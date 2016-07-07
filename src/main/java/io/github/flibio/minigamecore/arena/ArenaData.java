/**
 * This file is part of MinigameCore, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2016 - 2016 ${owner}
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

import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;

public class ArenaData implements Serializable {

    private static final long serialVersionUID = 1L;
    private HashMap<String, Object> customVariables = new HashMap<>();
    private HashMap<String, SerializableLocation> locations = new HashMap<>();

    private final String name;
    private boolean triggerPlayerEvents = true;
    private ArrayList<ArenaState> preventBlockModify = new ArrayList<>();
    private ArrayList<ArenaState> preventPlayerDamage = new ArrayList<>();
    private ArrayList<ArenaState> preventHungerLoss = new ArrayList<>();

    /**
     * Stores data for the arena.
     * 
     * @param arenaName The name of the arena.
     */
    public ArenaData(String arenaName) {
        this.name = arenaName;
    }

    /**
     * Gets the name of the arena.
     * 
     * @return The name of the arena.
     */
    public String getName() {
        return name;
    }

    /**
     * Checks if MinigameCore will automatically trigger player join and player
     * quit methods when a player joins or quits the server.
     * 
     * @return If MinigameCore will automatically trigger player join and player
     *         quit methods when a player joins or quits the server.
     */
    public boolean isTriggerPlayerEvents() {
        return triggerPlayerEvents;
    }

    /**
     * Sets if MinigameCore will automatically trigger player join and player
     * quit methods when a player joins or quits the server.
     * 
     * @param triggerPlayerEvents If MinigameCore will automatically trigger
     *        player join and player quit methods when a player joins or quits
     *        the server.
     */
    public void setTriggerPlayerEvents(boolean triggerPlayerEvents) {
        this.triggerPlayerEvents = triggerPlayerEvents;
    }

    /**
     * Adds a state that block modification will be prevented on.
     * 
     * @param state State that block modification will be prevented on.
     */
    public void addPreventBlockModify(ArenaState state) {
        preventBlockModify.add(state);
    }

    /**
     * Adds states that block modification will be prevented on.
     * 
     * @param states States that block modification will be prevented on.
     */
    public void addPreventBlockModify(Collection<? extends ArenaState> states) {
        preventBlockModify.addAll(states);
    }

    /**
     * Gets all states that block modification will be prevented on.
     * 
     * @return All states that block modification will be prevented on.
     */
    public List<ArenaState> getPreventBlockModify() {
        return preventBlockModify;
    }

    /**
     * Adds a state that player damage will be prevented on.
     * 
     * @param state State that player damage will be prevented on.
     */
    public void addPreventPlayerDamage(ArenaState state) {
        preventPlayerDamage.add(state);
    }

    /**
     * Adds states that player damage will be prevented on.
     * 
     * @param states States that player damage will be prevented on.
     */
    public void addPreventPlayerDamage(Collection<? extends ArenaState> states) {
        preventPlayerDamage.addAll(states);
    }

    /**
     * Gets all states that player damage will be prevented on.
     * 
     * @return All states that player damage will be prevented on.
     */
    public List<ArenaState> getPreventPlayerDamage() {
        return preventPlayerDamage;
    }

    /**
     * Adds a state that hunger loss will be prevented on.
     * 
     * @param state State that hunger loss will be prevented on.
     */
    public void addPreventHungerLoss(ArenaState state) {
        preventHungerLoss.add(state);
    }

    /**
     * Adds states that hunger loss will be prevented on.
     * 
     * @param states States that hunger loss will be prevented on.
     */
    public void addPreventHungerLoss(Collection<? extends ArenaState> states) {
        preventHungerLoss.addAll(states);
    }

    /**
     * Gets all states that hunger loss will be prevented on.
     * 
     * @return All states that hunger loss will be prevented on.
     */
    public List<ArenaState> getPreventHungerLoss() {
        return preventHungerLoss;
    }

    // Custom Variables

    /**
     * Set the value of a key. The value must be serializable.
     * 
     * @param key The key to store with the value.
     * @param type The type of the value.
     * @param value The value to store with the key.
     * @return If the value was successfully set.
     */
    public <T> boolean setVariable(String key, Class<T> type, T value) {
        if (Serializable.class.isAssignableFrom(type)) {
            customVariables.put(key, value);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gets a variable using a key.
     * 
     * @param key The key of the variable.
     * @param type The type of the variable stored.
     * @return The variable, if it was found.
     */
    @SuppressWarnings("unchecked")
    public <T> Optional<T> getVariable(String key, Class<T> type) {
        if (customVariables.containsKey(key)) {
            Object raw = customVariables.get(key);
            if (type.equals(raw.getClass())) {
                return Optional.of((T) raw);
            } else {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }

    /**
     * Gets all of the custom variables stored with the arena.
     * 
     * @return A HashMap of all of the custom variables.
     */
    public HashMap<String, Object> getCustomVariables() {
        final HashMap<String, Object> toReturn = this.customVariables;
        return toReturn;
    }

    /**
     * Assigns a location to a key.
     * 
     * @param key The key the location will be assigned to.
     * @param location The location to assign.
     */
    public void setLocation(String key, Location<World> location) {
        locations.put(key, SerializableLocation.of(location));
    }

    /**
     * Gets a location using the specified key.
     * 
     * @param key The key of the location to get.
     * @return The location, if found.
     */
    public Optional<Location<World>> getLocation(String key) {
        if (locations.containsKey(key)) {
            return Optional.of(locations.get(key).createLocation());
        } else {
            return Optional.empty();
        }
    }

    /**
     * Gets all of the locations stored with the arena.
     * 
     * @return A HashMap of all of the locations.
     */
    public HashMap<String, Location<World>> getLocations() {
        HashMap<String, Location<World>> toReturn = new HashMap<>();
        for (Entry<String, SerializableLocation> entry : locations.entrySet()) {
            toReturn.put(entry.getKey(), entry.getValue().createLocation());
        }
        return toReturn;
    }

}
