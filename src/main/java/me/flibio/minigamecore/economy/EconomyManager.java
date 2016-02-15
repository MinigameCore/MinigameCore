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
package me.flibio.minigamecore.economy;

import me.flibio.minigamecore.MinigameCore;
import org.spongepowered.api.Game;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.service.ChangeServiceProviderEvent;
import org.spongepowered.api.service.economy.Currency;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.service.economy.account.UniqueAccount;
import org.spongepowered.api.service.economy.transaction.ResultType;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public class EconomyManager {

    private boolean foundProvider = true;

    private EconomyService economy;

    private Cause cause = Cause.of("MinigameCore");
    private Currency currency;

    /**
     * Provides easy-to-use economy integration.
     *
     * @param game The game object
     */
    public EconomyManager(Game game) {
        game.getEventManager().registerListeners(MinigameCore.access, this);
    }

    @Listener
    public void onChangeServiceProvider(ChangeServiceProviderEvent event) {
        if (event.getService().equals(EconomyService.class) && !this.foundProvider) {
            Object raw = event.getNewProviderRegistration().getProvider();
            if (raw instanceof EconomyService) {
                this.foundProvider = true;
                this.economy = (EconomyService) raw;
                this.currency = this.economy.getDefaultCurrency();
            } else {
                this.foundProvider = false;
            }
        }
    }

    /**
     * Sets the balance of a player.
     *
     * @param uuid UUID of the player whose balance to change
     * @param amount The amount to set the player's balance to
     * @return Boolean based on if  the method was successful or not
     */
    public boolean setBalance(UUID uuid, BigDecimal amount) {
        if (!this.foundProvider) {
            return false;
        }
        Optional<UniqueAccount> uOpt = this.economy.getAccount(uuid);
        if (!uOpt.isPresent()) {
            return false;
        }
        UniqueAccount account = uOpt.get();
        return account.setBalance(this.currency, amount, this.cause).getResult().equals(ResultType.SUCCESS);
    }

    /**
     * Gets the balance of a player.
     *
     * @param uuid UUID of the player to get the balance of
     * @return The balance of the player
     */
    public Optional<BigDecimal> getBalance(UUID uuid) {
        if (!this.foundProvider) {
            return Optional.empty();
        }
        Optional<UniqueAccount> uOpt = this.economy.getAccount(uuid);
        if (!uOpt.isPresent()) {
            return Optional.empty();
        }
        UniqueAccount account = uOpt.get();
        return Optional.of(account.getBalance(this.currency));
    }

    /**
     * Adds currency to a players balance.
     *
     * @param uuid UUID of the player whose balance to change
     * @param amount Amount of currency to add to the player
     * @return Boolean based on if  the method was successful or not
     */
    public boolean addCurrency(UUID uuid, BigDecimal amount) {
        if (!this.foundProvider) {
            return false;
        }
        Optional<UniqueAccount> uOpt = this.economy.getAccount(uuid);
        if (!uOpt.isPresent()) {
            return false;
        }
        UniqueAccount account = uOpt.get();
        return account.deposit(this.currency, amount, this.cause).getResult().equals(ResultType.SUCCESS);
    }

    /**
     * Removes currency from a players balance.
     *
     * @param uuid UUID of the player whose balance to change
     * @param amount Amount of currency to remove from the player
     * @return Boolean based on if  the method was successful or not
     */
    public boolean removeCurrency(UUID uuid, BigDecimal amount) {
        if (!this.foundProvider) {
            return false;
        }
        Optional<UniqueAccount> uOpt = this.economy.getAccount(uuid);
        if (!uOpt.isPresent()) {
            return false;
        }
        UniqueAccount account = uOpt.get();
        return account.withdraw(this.currency, amount, this.cause).getResult().equals(ResultType.SUCCESS);
    }

}
