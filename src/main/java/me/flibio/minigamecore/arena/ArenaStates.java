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

/**
 * Different states an arena can be in
 */
public final class ArenaStates {

    /**
     * Wating for the minimum amount of players to join.
     */
    public static final ArenaState LOBBY_WAITING = new ArenaState("LOBBY_WAITING");
    /**
     * Counting down until the game begins.
     */
    public static final ArenaState LOBBY_COUNTDOWN = new ArenaState("LOBBY_COUNTDOWN");
    /**
     * No longer minimum amount of players present, the countdown was cancelled
     */
    public static final ArenaState COUNTDOWN_CANCELLED = new ArenaState("COUNTDOWN_CANCELLED");
    /**
     * Players are in the arena, waiting for the game to begin.
     */
    public static final ArenaState GAME_COUNTDOWN = new ArenaState("GAME_COUNTDOWN");
    /**
     * The game is currently in progress.
     */
    public static final ArenaState GAME_PLAYING = new ArenaState("GAME_PLAYING");
    /**
     * The game has ended and players will return to the lobby.
     */
    public static final ArenaState GAME_OVER = new ArenaState("GAME_OVER");

    private ArenaStates() {
    }

}
