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
package me.flibio.minigamecore.scoreboards;

import me.flibio.minigamecore.scoreboards.ScoreboardManager.ScoreboardType;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scoreboard.Scoreboard;

public class MinigameCoreScoreboard {

    private ScoreboardType scoreboardType;
    protected Scoreboard scoreboard;

    private String name;

    public MinigameCoreScoreboard(ScoreboardType type, String name) {
        this.scoreboardType = type;
        this.name = name;
    }

    /**
     * Gets the name of the scoreboard
     * 
     * @return The name of the scoreboard
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the type of the scoreboard
     * 
     * @return The type of the scoreboard
     */
    public ScoreboardType getType() {
        return this.scoreboardType;
    }

    /**
     * Displays the scoreboard to a player
     * 
     * @param player The player to display the scoreboard to
     */
    public void displayToPlayer(Player player) {
        player.setScoreboard(scoreboard);
    }
}
