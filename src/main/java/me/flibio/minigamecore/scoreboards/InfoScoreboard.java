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

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InfoScoreboard extends MinigameCoreScoreboard {

    private ConcurrentHashMap<Integer, Text> lines = new ConcurrentHashMap<Integer, Text>();
    private Text displayName;
    private Objective obj;

    public InfoScoreboard(String name, Game game) {
        super(ScoreboardType.INFO, name);
        this.displayName = Text.of(name);

        this.scoreboard = game.getRegistry().createBuilder(Scoreboard.Builder.class).build();
        this.obj = game.getRegistry().createBuilder(Objective.Builder.class).name(name).criterion(Criteria.DUMMY).displayName(displayName).build();
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
     * Gets a line of text from the scoreboard.
     *
     * @param lineNumber The line number to get text from
     * @return The text if found
     */
    public Optional<Text> getLine(int lineNumber) {
        if (!this.lines.containsKey(lineNumber)) {
            return Optional.empty();
        } else {
            return Optional.of(this.lines.get(lineNumber));
        }
    }

    /**
     * Sets a line of text on the scoreboard.
     *
     * @param lineNumber The line number to change (Must be greater than 0)
     * @param text The text to set the line to
     * @return If the method was successful or not
     */
    public boolean setLine(int lineNumber, Text text) {
        if (lineNumber < 1) {
            return false;
        } else {
            this.lines.put(lineNumber, text);
            return true;
        }
    }

    /**
     * Displays the scoreboard to the player. Overrides any existing scoreboard.
     *
     * @param player The player to display the scoreboard to
     */
    @Override
    public void displayToPlayer(Player player) {
        this.obj.setDisplayName(this.displayName);
        for (Integer line : this.lines.keySet()) {
            this.obj.getOrCreateScore(this.lines.get(line)).setScore(line);
        }
        this.scoreboard.updateDisplaySlot(this.obj, DisplaySlots.SIDEBAR);
        player.setScoreboard(this.scoreboard);
    }
}
