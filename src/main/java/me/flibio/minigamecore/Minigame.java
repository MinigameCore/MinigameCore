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
package me.flibio.minigamecore;

import me.flibio.minigamecore.arena.ArenaManager;
import me.flibio.minigamecore.economy.EconomyManager;
import me.flibio.minigamecore.file.FileManager;
import me.flibio.minigamecore.kits.KitManager;
import me.flibio.minigamecore.scoreboards.ScoreboardManager;
import me.flibio.minigamecore.teams.TeamManager;
import org.slf4j.Logger;
import org.spongepowered.api.Game;

public class Minigame {

    // Managers
    private ArenaManager arenaManager;
    private EconomyManager economyManager;
    private FileManager fileManager;
    private KitManager kitManager;
    private ScoreboardManager scoreboardManager;
    private TeamManager teamManager;

    private String name;

    private Game game;
    private Logger logger;

    /**
     * Stores information about the game.
     *
     * @param name The name of the game
     * @param game Instance of the game object
     * @param logger Instance of the logger object
     */
    public Minigame(String name, Game game, Logger logger) {
        this.name = name;
        this.game = game;
        this.logger = logger;

        // Initialize Managers
        this.arenaManager = new ArenaManager();
        this.economyManager = new EconomyManager(this.game);
        this.fileManager = new FileManager(this.logger, this.game, this.name);
        this.kitManager = new KitManager();
        this.scoreboardManager = new ScoreboardManager();
        this.teamManager = new TeamManager();
    }

    /**
     * Get the name of the minigame.
     *
     * @return The name of the minigame
     */
    public String getName() {
        return this.name;
    }

    // All manager getters

    /**
     * Gets the arena manager for the game.
     *
     * @return The arena manager
     */
    public ArenaManager getArenaManager() {
        return this.arenaManager;
    }

    /**
     * Gets the economy manager for the game.
     *
     * @return The economy manager
     */
    public EconomyManager getEconomyManager() {
        return this.economyManager;
    }

    /**
     * Gets the file manager for the game.
     *
     * @return The file manager
     */
    public FileManager getFileManager() {
        return this.fileManager;
    }

    /**
     * Gets the kit manager for the game.
     *
     * @return The kit manager
     */
    public KitManager getKitManager() {
        return this.kitManager;
    }

    /**
     * Gets the scoreboard manager for the game.
     *
     * @return The scoreboard manager
     */
    public ScoreboardManager getScoreboardManager() {
        return this.scoreboardManager;
    }

    /**
     * Gets the team manager for the game.
     *
     * @return The team manager
     */
    public TeamManager getTeamManager() {
        return this.teamManager;
    }
}
