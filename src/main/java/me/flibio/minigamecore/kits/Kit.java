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

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;

import java.util.concurrent.ConcurrentHashMap;

public class Kit {

    private String name;
    private ConcurrentHashMap<Integer, ItemStack> kitItems = new ConcurrentHashMap<Integer, ItemStack>();

    public Kit(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the kit.
     *
     * @return The name of the kit
     */
    public String getName() {
        return this.name;
    }

    /**
     * Adds an item to the kit in the first available position. If the kit
     * is full, the item will not be added.
     *
     * @param item The item to add to the kit
     * @return If the method was successful or not
     */
    public boolean addKitItem(ItemStack item) {
        if (this.kitItems.size() >= 36) {
            return false;
        }
        return false;
    }

    /**
     * Adds an item to the kit in the specified position. This will override
     * any item currently in that position. (Positions are not implemented yet)
     *
     * @param item The item to add to the kit
     * @param position The position to put the item in. Numbers 1-36
     * @return If the method was successful or not
     */
    public boolean addKitItem(ItemStack item, int position) {
        if (position > 36 || position < 1) {
            return false;
        }
        return false;
    }

    /**
     * Gives the player each item in the kit. Please note that inventory
     * positions are not currently implemented.
     *
     * @param player The player to give the kit to
     */
    public void giveToPlayer(Player player) {
        for (ItemStack item : this.kitItems.values()) {
            player.getInventory().set(item);
        }
    }

    /**
     * Gives the player each item in the kit, clearing their inventory first.
     * Please note that inventory positions are not currently implemented.
     *
     * @param player The player to give the kit to
     */
    public void forceGiveToPlayer(Player player) {
        player.getInventory().clear();
        for (ItemStack item : this.kitItems.values()) {
            player.getInventory().set(item);
        }
    }
}
