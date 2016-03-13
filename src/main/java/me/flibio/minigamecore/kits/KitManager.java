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
package me.flibio.minigamecore.kits;

import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public class KitManager {

    private CopyOnWriteArrayList<Kit> kits = new CopyOnWriteArrayList<Kit>();

    /**
     * Manages all of the kits in a game.
     */
    public KitManager() {

    }

    /**
     * Registers a new kit with the kit manager.
     *
     * @param kit The kit to register
     * @return Boolean based on if the method was successful or not
     */
    public boolean registerKit(Kit kit) {
        //TODO
        return false;
    }

    /**
     * Removes a kit from the kit manager.
     *
     * @param name Name of the kit to remove
     * @return Boolean based on if the method was successful or not
     */
    public boolean removeKit(String name) {
        //TODO
        return false;
    }

    /**
     * Gets a kit from the kit manager.
     *
     * @param name The name of the kit to get
     * @return Optional of the kit
     */
    public Optional<Kit> getKit(String name) {
        for (Kit kit : this.kits) {
            if (kit.getName().equalsIgnoreCase(name)) {
                return Optional.of(kit);
            }
        }
        return Optional.empty();
    }

    /**
     * Checks if a kit exists.
     *
     * @param name The name of the kit to check for
     * @return Boolean based on if the kit was found or not
     */
    public boolean kitExists(String name) {
        for (Kit kit : this.kits) {
            if (kit.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }
}
