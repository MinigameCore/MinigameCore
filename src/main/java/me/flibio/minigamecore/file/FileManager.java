/*
 * This file is part of MinigameCore, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2015 - 2016 Flibio
 * Copyright (c) Contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package me.flibio.minigamecore.file;

import me.flibio.minigamecore.arena.Arena;
import me.flibio.minigamecore.arena.ArenaData;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class FileManager {

    private ConcurrentHashMap<String, ConfigurationNode> files = new ConcurrentHashMap<String, ConfigurationNode>();

    private Logger logger;
    private Game game;
    private String name;

    /**
     * Provides easy-to-use file configuration and data storage.
     *
     * @param logger An instance of the logger
     * @param name The name of the game
     */
    public FileManager(Logger logger, Game game, String name) {
        this.logger = logger;
        this.game = game;
        this.name = name;
    }

    /**
     * Generates a folder with a specified name.
     *
     * @param name The name of the folder to generate
     * @return If the method was successful or not
     */
    private boolean generateFolder(String name) {
        File folder = new File("config/" + name);
        try {
            if (!folder.exists()) {
                if (folder.mkdir()) {
                    this.logger.info("Successfully generated folder " + name);
                    return true;
                } else {
                    this.logger.error("Error generating folder " + name);
                    return false;
                }
            } else {
                return true;
            }
        } catch (Exception e) {
            this.logger.error("Error generating folder: " + e.getMessage());
            return false;
        }
    }

    /**
     * Initializes a file into the file manager. This must be done each server start.
     *
     * @param fileName The name of the file to initialize
     * @return If the method was successful or not
     */
    public boolean initializeFile(String fileName) {
        if (fileName.contains(".conf")) {
            fileName = fileName.replace(".conf", "").trim();
        }
        if (generateFolder(this.name)) {
            if (!this.files.containsKey(fileName)) {
                // File isn't registered
                File file = new File("config/" + this.name + "/" + fileName + ".conf");
                if (!file.exists()) {
                    // File doesn't exist
                    try {
                        file.createNewFile();
                        ConfigurationLoader<?> manager = HoconConfigurationLoader.builder()
                                .setFile(new File("config/" + this.name + "/" + fileName + ".conf")).build();
                        ConfigurationNode root;
                        root = manager.load();
                        this.files.put(fileName, root);
                        return true;
                    } catch (IOException e) {
                        this.logger.error("Error initializing file " + fileName + ": " + e.getMessage());
                        return false;
                    }
                } else {
                    // File already exists
                    ConfigurationLoader<?> manager = HoconConfigurationLoader.builder()
                            .setFile(new File("config/" + this.name + "/" + fileName + ".conf")).build();
                    ConfigurationNode root;
                    // Try to load the file
                    try {
                        root = manager.load();
                        this.files.put(fileName, root);
                        return true;
                    } catch (IOException e) {
                        this.logger.error("Error initializing file " + fileName + ": " + e.getMessage());
                        return false;
                    }
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Adds a default option to the specified file. Allows for "." seperators
     * in the key path. If the specified path has no value set, method will
     * set it to the default value specified.
     *
     * @param fileName The name of the file
     * @param key The path of the default option
     * @param value The default value of the option
     * @return If the method was successful or not
     */
    public boolean defaultOption(String fileName, String key, Object value) {
        if (fileName.contains(".conf")) {
            fileName = fileName.replace(".conf", "").trim();
        }
        Optional<ConfigurationNode> fileOptional = getFile(fileName);
        if (fileOptional.isPresent()) {
            ConfigurationNode file = fileOptional.get();
            if (file.getNode((Object[]) key.split("\\.")).getValue() == null) {
                file.getNode((Object[]) key.split("\\.")).setValue(value);
                saveFile(fileName, file);
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Saves a file. Writes to disk and updates the list of files.
     *
     * @param fileName The name of the file to save
     * @param fileData The file data to be saved
     * @return If the method was successful or not
     */
    public boolean saveFile(String fileName, ConfigurationNode fileData) {
        if (fileName.contains(".conf")) {
            fileName = fileName.replace(".conf", "").trim();
        }
        File file = new File("config/" + this.name + "/" + fileName + ".conf");
        if (file.exists()) {
            ConfigurationLoader<?> manager = HoconConfigurationLoader.builder().setFile(file).build();
            try {
                manager.save(fileData);
                return true;
            } catch (IOException e) {
                this.logger.error("Error saving " + fileName + ": " + e.getMessage());
                return false;
            }
        } else {
            // File doesn't exist
            return false;
        }
    }

    /**
     * Gets an initialized file.
     *
     * @param name The name of the file to get
     * @return The ConfigurationNode of the file
     */
    public Optional<ConfigurationNode> getFile(String name) {
        if (name.contains(".conf")) {
            name = name.replace(".conf", "").trim();
        }
        if (this.files.containsKey(name)) {
            return Optional.of(this.files.get(name));
        } else {
            return Optional.empty();
        }
    }

    /**
     * Saves an arena to the file.
     *
     * @param arena The arena to save
     * @param fileName The location to save the arena
     * @return If arena was saved successfully or not
     */
    public boolean saveArena(Arena arena, String fileName) {
        initializeFile(fileName);
        Optional<ConfigurationNode> fopt = getFile(fileName);
        if (fopt.isPresent()) {
            ConfigurationNode file = fopt.get();
            ArenaData data = arena.getData();
            String arenaName = data.getName();
            ConfigurationNode arenaNode = file.getNode(arenaName);
            file.setValue(null);
            arenaNode.getNode("triggerPlEvents").setValue(data.isTriggerPlayerEvents());
            arenaNode.getNode("allowLobbyDmg").setValue(data.isAllowLobbyDamage());
            arenaNode.getNode("modifyLobbyBlocks").setValue(data.isModifyLobbyBlocks());
            for (String key : data.getCustomLocations().keySet()) {
                saveLoc(arenaNode.getNode("customLoc").getNode(key), data.getCustomLocations().get(key));
            }
            for (String key : data.getCustomVariables().keySet()) {
                arenaNode.getNode("custom").getNode(key).setValue(data.getCustomVariables().get(key));
            }
            return saveFile(fileName, file);
        } else {
            return false;
        }
    }

    /**
     * Loads all arenas from a specified file.
     *
     * @param fileName The file to load arenas from
     * @return All arenas found in the file
     */
    public ArrayList<ArenaData> loadArenas(String fileName) {
        initializeFile(fileName);
        Optional<ConfigurationNode> fopt = getFile(fileName);
        ArrayList<ArenaData> arenaDatas = new ArrayList<ArenaData>();
        if (fopt.isPresent()) {
            ConfigurationNode file = fopt.get();
            for (String child : getChildren(file)) {
                ConfigurationNode arenaNode = file.getNode(child);
                ArenaData arenaData = new ArenaData(child);
                // Load all the saved value stored with the arena
                try {
                    arenaData.setTriggerPlayerEvents(arenaNode.getNode("triggerPlEvents").getBoolean());
                    arenaData.setAllowLobbyDamage(arenaNode.getNode("allowLobbyDmg").getBoolean());
                    arenaData.setModifyLobbyBlocks(arenaNode.getNode("modifyLobbyBlocks").getBoolean());
                    // Check for custom variables
                    if (arenaNode.getNode("custom") != null) {
                        for (String customVar : getChildren(arenaNode.getNode("custom"))) {
                            ConfigurationNode customNode = arenaNode.getNode("custom");
                            Object rawVal = customNode.getNode(customVar).getValue();
                            if (rawVal instanceof String) {
                                arenaData.setVariable(customVar, (String) rawVal);
                            } else if (rawVal instanceof Integer) {
                                arenaData.setVariable(customVar, (Integer) rawVal);
                            } else if (rawVal instanceof Boolean) {
                                arenaData.setVariable(customVar, (Boolean) rawVal);
                            } else if (rawVal instanceof Double) {
                                arenaData.setVariable(customVar, (Double) rawVal);
                            } else if (rawVal instanceof Float) {
                                arenaData.setVariable(customVar, (Float) rawVal);
                            }
                        }
                    }
                    // Check for custom locations
                    if (arenaNode.getNode("customLoc") != null) {
                        for (String locName : getChildren(arenaNode.getNode("customLoc"))) {
                            Optional<Location<World>> oLoc = loadLoc(arenaNode.getNode("customLoc").getNode(locName));
                            if (oLoc.isPresent()) {
                                arenaData.setLocation(locName, oLoc.get());
                            }
                        }
                    }
                    arenaDatas.add(arenaData);
                } catch (Exception e) {
                    this.logger.error("Corrupt arena data for arena " + child + "!");
                }
            }
        }
        return arenaDatas;
    }

    private void saveLoc(ConfigurationNode node, Location<World> loc) {
        node.getNode("X").setValue(loc.getBlockX());
        node.getNode("Y").setValue(loc.getBlockY());
        node.getNode("Z").setValue(loc.getBlockZ());
        node.getNode("w").setValue(loc.getExtent().getName());
    }

    private Optional<Location<World>> loadLoc(ConfigurationNode node) {
        ConfigurationNode x = node.getNode("X");
        ConfigurationNode y = node.getNode("Y");
        ConfigurationNode z = node.getNode("Z");
        ConfigurationNode world = node.getNode("w");
        if (x == null || y == null || z == null || world == null || !this.game.getServer().getWorld(world.getString()).isPresent()) {
            return Optional.empty();
        }
        try {
            return Optional.of(new Location<World>(this.game.getServer().getWorld(world.getString()).get(),
                    x.getInt(), y.getInt(), z.getInt()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private ArrayList<String> getChildren(ConfigurationNode node) {
        ArrayList<String> children = new ArrayList<String>();
        for (Object raw : node.getChildrenMap().keySet()) {
            if (raw instanceof String) {
                children.add((String) raw);
            }
        }
        return children;
    }
}
