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
import org.spongepowered.api.Game;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scoreboard.Scoreboard;
import org.spongepowered.api.scoreboard.critieria.Criteria;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlots;
import org.spongepowered.api.scoreboard.objective.Objective;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;

import java.util.concurrent.ConcurrentHashMap;

public class LeaderboardScoreboard extends MinigameCoreScoreboard {

    private Text displayName;
    private TextColor nameColor = TextColors.GREEN;
    private Objective obj;

    private ConcurrentHashMap<String, Integer> leaders = new ConcurrentHashMap<String, Integer>();

    public LeaderboardScoreboard(String name, Game game) {
        super(ScoreboardType.LEADERBOARD, name);
        this.displayName = Text.of(name);

        this.scoreboard = game.getRegistry().createBuilder(Scoreboard.Builder.class).build();
        this.obj = game.getRegistry().createBuilder(Objective.Builder.class).name(name).criterion(Criteria.DUMMY).displayName(displayName).build();
    }

    /**
     * Adds a leader to the leaderboard.
     *
     * @param player The name of the player to add
     * @param score The score of the player
     * @return If the method was successful or not
     */
    public boolean addLeader(String player, int score) {
        if (this.leaders.containsKey(player)) {
            return false;
        } else {
            this.leaders.put(player, score);
            return true;
        }
    }

    /**
     * Updates a leader's score on the leaderboard.
     *
     * @param player The name of the player to update
     * @param score The score of the player
     * @return If the method was successful or not
     */
    public boolean updateScore(String player, int score) {
        if (!this.leaders.containsKey(player)) {
            return false;
        } else {
            this.leaders.replace(player, score);
            return true;
        }
    }

    /**
     * Removes a leader from the leaderboard.
     *
     * @param player The name of the player to remove
     * @return If the method was successful or not
     */
    public boolean removeLeader(String player) {
        if (!this.leaders.containsKey(player)) {
            return false;
        } else {
            this.leaders.remove(player);
            return true;
        }
    }

    /**
     * Checks if a leader exists.
     *
     * @param player The name of the player to check for
     * @return If the leader was found or not
     */
    public boolean leaderExists(String player) {
        return this.leaders.containsKey(player);
    }

    /**
     * Gets the display name of the scoreboard.
     *
     * @return The display name of the scoreboard
     */
    public Text getDisplayName() {
        return this.displayName;
    }

    /**
     * Sets the display name of the scoreboard.
     *
     * @param text What to set the display name to
     */
    public void setDisplayName(Text text) {
        this.displayName = text;
    }

    /**
     * Gets the color of the player's names.
     *
     * @return The color of the player's names
     */
    public TextColor getNameColor() {
        return this.nameColor;
    }

    /**
     * Sets the color of the player's names.
     *
     * @param nameColor The color of the player's names
     */
    public void setNameColor(TextColor nameColor) {
        this.nameColor = nameColor;
    }

    /**
     * Displays the scoreboard to the player. Overrides any existing scoreboard.
     *
     * @param player The player to display the scoreboard to
     */
    @Override
    public void displayToPlayer(Player player) {
        this.obj.setDisplayName(this.displayName);
        for (String leader : this.leaders.keySet()) {
            this.obj.getOrCreateScore(Text.of(this.nameColor, leader)).setScore(this.leaders.get(leader));
        }
        this.scoreboard.updateDisplaySlot(this.obj, DisplaySlots.SIDEBAR);
        player.setScoreboard(this.scoreboard);
    }
}
