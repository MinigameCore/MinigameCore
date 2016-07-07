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
package io.github.flibio.minigamecore;

import io.github.flibio.minigamecore.arena.ArenaManager;
import io.github.flibio.minigamecore.economy.EconomyManager;
import io.github.flibio.minigamecore.kits.KitManager;
import io.github.flibio.minigamecore.scoreboards.ScoreboardManager;
import io.github.flibio.minigamecore.teams.TeamManager;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;

import java.util.Optional;

public class Minigame {

    private ArenaManager arenaManager;
    private EconomyManager economyManager;
    private KitManager kitManager;
    private ScoreboardManager scoreboardManager;
    private TeamManager teamManager;

    private String name;

    protected Minigame(String name, ArenaManager aManager, EconomyManager eManager, KitManager kManager, ScoreboardManager sManager,
            TeamManager tManager) {
        this.name = name;

        this.arenaManager = aManager;
        this.economyManager = eManager;
        this.kitManager = kManager;
        this.scoreboardManager = sManager;
        this.teamManager = tManager;
    }

    public static Optional<Minigame> create(String name, Object plugin) {
        Game game = Sponge.getGame();
        Optional<ArenaManager> arenaManager = ArenaManager.createInstance(plugin);
        EconomyManager economyManager = new EconomyManager(game, plugin);
        KitManager kitManager = new KitManager();
        ScoreboardManager scoreboardManager = new ScoreboardManager();
        TeamManager teamManager = new TeamManager();
        if (arenaManager.isPresent()) {
            return Optional.of(new Minigame(name, arenaManager.get(), economyManager, kitManager, scoreboardManager, teamManager));
        } else {
            return Optional.empty();
        }
    }

    /**
     * Get the name of the minigame.
     * 
     * @return The name of the minigame.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the arena manager.
     * 
     * @return The arena manager.
     */
    public ArenaManager getArenaManager() {
        return arenaManager;
    }

    /**
     * Gets the economy manager.
     * 
     * @return The economy manager.
     */
    public EconomyManager getEconomyManager() {
        return economyManager;
    }

    /**
     * Gets the kit manager.
     * 
     * @return The kit manager.
     */
    public KitManager getKitManager() {
        return kitManager;
    }

    /**
     * Gets the scoreboard manager.
     * 
     * @return The scoreboard manager.
     */
    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }

    /**
     * Gets the team manager.
     * 
     * @return The team manager.
     */
    public TeamManager getTeamManager() {
        return teamManager;
    }
}
