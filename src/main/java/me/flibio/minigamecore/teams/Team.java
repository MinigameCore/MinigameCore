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
package me.flibio.minigamecore.teams;

import org.spongepowered.api.entity.living.player.Player;

import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public class Team {

    private String name;
    private CopyOnWriteArrayList<Player> players = new CopyOnWriteArrayList<Player>();

    public Team(String name) {
        this.name = name;
    }

    /**
     * Gets the team's name.
     *
     * @return The name of the team
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets a player from the team players.
     *
     * @param name The name of the player to search for
     * @return The player
     */
    public Optional<Player> getPlayer(String name) {
        for (Player player : this.players) {
            if (player.getName().equalsIgnoreCase(name)) {
                return Optional.of(player);
            }
        }
        return Optional.empty();
    }

    /**
     * Checks if a player is on the team.
     *
     * @param name The name of the player to check for
     * @return If the player is on the team or not
     */
    public boolean playerExists(String name) {
        for (Player player : this.players) {
            if (player.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a player to the team.
     *
     * @param player The player to add to the team
     */
    public void addPlayer(Player player) {
        this.players.add(player);
    }

    /**
     * Removes a player from the team.
     *
     * @param name The name of the player to remove from the team
     */
    public void removePlayer(String name) {
        for (Player player : this.players) {
            if (player.getName().equalsIgnoreCase(name)) {
                this.players.remove(player);
            }
        }
    }
}
