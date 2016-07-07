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

import java.util.Arrays;
import java.util.List;

/**
 * Contains all of the default arena states.
 */
public class ArenaStates {

    /**
     * Wating for the minimum amount of players to join.
     */
    public static final ArenaState LOBBY_WAITING = ArenaState.of("LOBBY_WAITING");

    /**
     * Counting down until the game begins.
     */
    public static final ArenaState LOBBY_COUNTDOWN = ArenaState.of("LOBBY_COUNTDOWN");

    /**
     * Players are in the arena, waiting for the game to begin.
     */
    public static final ArenaState GAME_COUNTDOWN = ArenaState.of("GAME_COUNTDOWN");

    /**
     * The game is currently in progress.
     */
    public static final ArenaState GAME_PLAYING = ArenaState.of("GAME_PLAYING");

    /**
     * The game has ended and players will return to the lobby.
     */
    public static final ArenaState GAME_OVER = ArenaState.of("GAME_OVER");

    /**
     * All of the default arena states.
     */
    public static final List<ArenaState> ALL = Arrays.asList(LOBBY_WAITING, LOBBY_COUNTDOWN, GAME_COUNTDOWN, GAME_PLAYING, GAME_OVER);

}
