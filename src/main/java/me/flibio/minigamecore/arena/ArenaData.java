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
package me.flibio.minigamecore.arena;

import com.google.common.collect.ImmutableMap;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ArenaData {

    private HashMap<String, Object> customVariables = new HashMap<String, Object>();
    private HashMap<String, Location<World>> customLocations = new HashMap<String, Location<World>>();

    private String name = "";
    private boolean triggerPlayerEvents = true;
    private boolean modifyLobbyBlocks = false;
    private boolean allowLobbyDamage = false;

    /**
     * Stores configurable options for the arena.
     *
     * @param arenaName The name of the arena
     */
    public ArenaData(String arenaName) {
        this.name = arenaName;
    }

    /**
     * Gets the name of the arena.
     *
     * @return The name of the arena
     */
    public String getName() {
        return this.name;
    }

    /**
     * Checks if MinigameCore will automatically trigger player join
     * and player quit methods.
     *
     * @return If MinigameCore will automatically trigger player join
     * and player quit methods
     */
    public boolean isTriggerPlayerEvents() {
        return this.triggerPlayerEvents;
    }

    /**
     * Sets if MinigameCore will automatically trigger player join
     * and player quit methods.
     *
     * @param triggerPlayerEvents If MinigameCore will automatically trigger player join
     * and player quit methods
     */
    public void setTriggerPlayerEvents(boolean triggerPlayerEvents) {
        this.triggerPlayerEvents = triggerPlayerEvents;
    }

    /**
     * Checks if MinigameCore will block players from modifying blocks in the
     * lobby.
     *
     * @return If MinigameCore will block players from modifying blocks in the lobby
     */
    public boolean isModifyLobbyBlocks() {
        return this.modifyLobbyBlocks;
    }

    /**
     * Sets if MinigameCore will block players from modifying blocks in the.
     * lobby.
     *
     * @param modifyLobbyBlocks If MinigameCore will block players from modifying blocks in the lobby
     */
    public void setModifyLobbyBlocks(boolean modifyLobbyBlocks) {
        this.modifyLobbyBlocks = modifyLobbyBlocks;
    }

    /**
     * Checks if MinigameCore will allow players to take damage in the lobby.
     *
     * @return If MinigameCore will allow players to take damage in the lobby
     */
    public boolean isAllowLobbyDamage() {
        return this.allowLobbyDamage;
    }

    /**
     * Sets if MinigameCore will allow players to take damage in the lobby.
     *
     * @param allowLobbyDamage If MinigameCore will allow players to take damage in the lobby
     */
    public void setAllowLobbyDamage(boolean allowLobbyDamage) {
        this.allowLobbyDamage = allowLobbyDamage;
    }

    //Custom Variables

    /**
     * Sets a custom variable. Saved with the arena data.
     *
     * @param key The key used to retrieve the value
     * @param value The value to be saved
     */
    public void setVariable(String key, String value) {
        this.customVariables.put(key, value);
    }

    /**
     * Sets a custom variable. Saved with the arena data.
     *
     * @param key The key used to retrieve the value
     * @param value The value to be saved
     */
    public void setVariable(String key, int value) {
        this.customVariables.put(key, value);
    }

    /**
     * Sets a custom variable. Saved with the arena data.
     *
     * @param key The key used to retrieve the value
     * @param value The value to be saved
     */
    public void setVariable(String key, boolean value) {
        this.customVariables.put(key, value);
    }

    /**
     * Sets a custom variable. Saved with the arena data.
     *
     * @param key The key used to retrieve the value
     * @param value The value to be saved
     */
    public void setVariable(String key, double value) {
        this.customVariables.put(key, value);
    }

    /**
     * Sets a custom variable. Saved with the arena data.
     *
     * @param key The key used to retrieve the value
     * @param value The value to be saved
     */
    public void setVariable(String key, float value) {
        this.customVariables.put(key, value);
    }

    /**
     * Retrieves a value using the specified key.
     *
     * @param key The key to use to retrieve the value
     * @return The value, if found
     */
    public Optional<Object> getVariable(String key) {
        if (this.customVariables.containsKey(key)) {
            return Optional.of(this.customVariables.get(key));
        } else {
            return Optional.empty();
        }
    }

    /**
     * Gets all of the custom variables stored with the arena.
     *
     * @return A HashMap of all of the custom variables
     */
    public Map<String, Object> getCustomVariables() {
        return ImmutableMap.copyOf(this.customVariables);
    }

    /**
     * Sets a custom location. Saved with the arena data.
     *
     * @param key The key used to retrieve the location
     * @param location The location to be saved
     */
    public void setLocation(String key, Location<World> location) {
        this.customLocations.put(key, location);
    }

    /**
     * Gets a location using the specified key.
     *
     * @param key The key of the location to get
     * @return The location, if found
     */
    public Optional<Location<World>> getLocation(String key) {
        if (this.customLocations.containsKey(key)) {
            return Optional.of(this.customLocations.get(key));
        } else {
            return Optional.empty();
        }
    }

    /**
     * Gets all of the custom locations stored with the arena.
     *
     * @return A HashMap of all of the custom locations
     */
    public Map<String, Location<World>> getCustomLocations() {
        return ImmutableMap.copyOf(this.customLocations);
    }

}
