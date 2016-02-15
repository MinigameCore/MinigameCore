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

import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public class ArenaManager {

    private CopyOnWriteArrayList<Arena> arenas = new CopyOnWriteArrayList<Arena>();

    /**
     * Manages the arenas for a game.
     */
    public ArenaManager() {

    }

    /**
     * Adds an arena to the minigame.
     *
     * @param arena The arena to add
     * @return If the arena was successfully added or not
     */
    public boolean addArena(Arena arena) {
        if (arenaExists(arena.getData().getName())) {
            return false;
        }
        this.arenas.add(arena);
        return true;
    }

    /**
     * Removes an arena.
     *
     * @param name The name of the arena to remove
     * @return If the arena was successfully removed or not
     */
    public boolean removeArena(String name) {
        for (Arena arena : this.arenas) {
            if (arena.getData().getName().equalsIgnoreCase(name)) {
                this.arenas.remove(arena);
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if an arena exists.
     *
     * @param name The arena to check for
     * @return Boolean based on if the arena was found or not
     */
    public Optional<Arena> getArena(String name) {
        for (Arena arena : this.arenas) {
            if (arena.getData().getName().equalsIgnoreCase(name)) {
                return Optional.of(arena);
            }
        }
        return Optional.empty();
    }

    /**
     * Checks if an arena exists.
     *
     * @param name The arena to check for
     * @return Boolean based on if the arena was found or not
     */
    public boolean arenaExists(String name) {
        for (Arena arena : this.arenas) {
            if (arena.getData().getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }
}
