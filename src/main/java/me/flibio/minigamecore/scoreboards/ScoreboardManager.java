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

import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public class ScoreboardManager {

    private CopyOnWriteArrayList<MinigameCoreScoreboard> scoreboards = new CopyOnWriteArrayList<MinigameCoreScoreboard>();

    public ScoreboardManager() {

    }

    /**
     * Gets a scoreboard.
     *
     * @param name Name of the scoreboard to get
     * @return The scoreboard
     */
    public Optional<MinigameCoreScoreboard> getScoreboard(String name) {
        for (MinigameCoreScoreboard scoreboard : this.scoreboards) {
            if (scoreboard.getName().toLowerCase().equalsIgnoreCase(name)) {
                Optional.of(scoreboard);
            }
        }
        return Optional.empty();
    }

    /**
     * Checks if a scoreboard exists.
     *
     * @param name Name of the scoreboard to check for
     * @return Boolean based on if the scoreboard exists or not
     */
    public boolean scoreboardExists(String name) {
        for (MinigameCoreScoreboard scoreboard : this.scoreboards) {
            if (scoreboard.getName().toLowerCase().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public enum ScoreboardType {INFO, ABOVE_NAME, LEADERBOARD}
}
