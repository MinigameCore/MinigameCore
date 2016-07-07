/**
 * This file is part of MinigameCore, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2016 - 2016 MinigameCore
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

import java.io.Serializable;

public class ArenaState implements Serializable {

    private static final long serialVersionUID = 1L;
    private String stateName;

    /**
     * An ArenaState is used to identify which state the arena is in.
     * 
     * @param stateName The name of the arena state.
     */
    protected ArenaState(String stateName) {
        this.stateName = stateName;
    }

    /**
     * Creates a new arena state using the given name.
     * 
     * @param stateName The name of the arena state.
     * @return The created arena state.
     */
    public static ArenaState of(String stateName) {
        return new ArenaState(stateName);
    }

    /**
     * Gets the name of the state.
     * 
     * @return The state name.
     */
    public String getName() {
        return this.stateName;
    }
}
