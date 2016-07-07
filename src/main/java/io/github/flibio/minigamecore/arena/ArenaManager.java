/**
 * This file is part of MinigameCore, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2016 - 2016 MinigameCore
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
package io.github.flibio.minigamecore.arena;

import com.google.common.io.Files;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.plugin.Plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ArenaManager {

    private ArrayList<Arena> arenas = new ArrayList<>();

    private Logger logger;
    private String folderName;

    /**
     * Manages the arenas for a minigame.
     */
    public ArenaManager(Logger logger, String folderName) {
        this.logger = logger;
        this.folderName = folderName;
    }

    /**
     * Creates a new {@link ArenaManager} instance.
     * 
     * @param plugin An instance of the main plugin class.
     * @return The new {@link ArenaManager} instance.
     */
    public static Optional<ArenaManager> createInstance(Object plugin) {
        if (plugin.getClass().isAnnotationPresent(Plugin.class)) {
            Plugin annotation = plugin.getClass().getAnnotation(Plugin.class);
            Logger logger = Sponge.getGame().getPluginManager().getPlugin(annotation.id()).get().getLogger();
            return Optional.of(new ArenaManager(logger, annotation.name().toLowerCase().replaceAll(" ", "")));
        } else {
            return Optional.empty();
        }
    }

    /**
     * Adds an arena to the minigame.
     * 
     * @param arena The arena to add.
     * @return If the arena was successfully added or not.
     */
    public boolean addArena(Arena arena) {
        if (arenaExists(arena.getData().getName())) {
            return false;
        }
        arenas.add(arena);
        return true;
    }

    /**
     * Removes an arena.
     * 
     * @param name The name of the arena to remove.
     * @return If the arena was successfully removed or not.
     */
    public boolean removeArena(String name) {
        for (Arena arena : arenas) {
            if (arena.getData().getName().equalsIgnoreCase(name)) {
                arenas.remove(arena);
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if an arena exists.
     * 
     * @param name The arena to check for.
     * @return Boolean based on if the arena was found or not.
     */
    public Optional<Arena> getArena(String name) {
        for (Arena arena : arenas) {
            if (arena.getData().getName().equalsIgnoreCase(name)) {
                return Optional.of(arena);
            }
        }
        return Optional.empty();
    }

    /**
     * Checks if an arena exists.
     * 
     * @param name The arena to check for.
     * @return Boolean based on if the arena was found or not.
     */
    public boolean arenaExists(String name) {
        for (Arena arena : arenas) {
            if (arena.getData().getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets all arenas registered with the minigame.
     * 
     * @return All arenas registered with the minigame.
     */
    public List<Arena> getArenas() {
        return arenas;
    }

    /**
     * Saves {@link ArenaData} to the arena directory.
     * 
     * @param data The {@link ArenaData} to save.
     * @return If the save was successful.
     */
    public boolean saveArenaData(ArenaData data) {
        try {
            new File("config/" + folderName + "/arenas").mkdirs();
            File file = new File("config/" + folderName + "/arenas/" + data.getName() + ".ser");
            logger.debug("Attempting to save ArenaData to " + file.getPath());
            FileOutputStream fout = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(data);
            oos.close();
            return true;
        } catch (Exception e) {
            logger.error("Could not save ArenaData of " + data.getName());
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            logger.debug(sw.toString());
            return false;
        }
    }

    /**
     * Loads all {@link ArenaData} found in the arena directory.
     * 
     * @return All {@link ArenaData} found in the arena directory.
     */
    public Set<ArenaData> loadArenaData() {
        HashSet<ArenaData> arenaDatas = new HashSet<>();
        try {
            File folder = new File("config/" + folderName + "/arenas");
            folder.mkdirs();
            logger.debug("Attempting to load all ArenaData from " + folder.getPath());
            for (File file : folder.listFiles()) {
                if (file.isFile()) {
                    String ext = Files.getFileExtension(file.getName());
                    if (ext.equalsIgnoreCase("ser")) {
                        FileInputStream fin = new FileInputStream(file);
                        ObjectInputStream ois = new ObjectInputStream(fin);
                        Object raw = ois.readObject();
                        ois.close();
                        if (raw instanceof ArenaData) {
                            arenaDatas.add((ArenaData) raw);
                            logger.debug("Valid ArenaData loaded from " + file.getPath());
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("An error occurred while loading ArenaData!");
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            logger.debug(sw.toString());
        }
        return arenaDatas;
    }

}
