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
package io.github.minigamecore.economy;

import org.spongepowered.api.Game;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.event.service.ChangeServiceProviderEvent;
import org.spongepowered.api.service.economy.Currency;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.service.economy.account.UniqueAccount;
import org.spongepowered.api.service.economy.transaction.ResultType;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public class EconomyManager {

    private boolean foundProvider = false;

    private EconomyService economy;

    private Cause cause;
    private Currency currency;

    /**
     * Provides economy integration for minigames.
     * 
     * @param game The game object.
     * @param plugin The plugin object.
     */
    public EconomyManager(Game game, Object plugin) {
        cause = Cause.of(NamedCause.of("MinigameCore", plugin));
        game.getEventManager().registerListeners(plugin, this);
    }

    @Listener
    public void onChangeServiceProvider(ChangeServiceProviderEvent event) {
        if (event.getService().equals(EconomyService.class) && !foundProvider) {
            Object raw = event.getNewProviderRegistration().getProvider();
            if (raw instanceof EconomyService) {
                foundProvider = true;
                economy = (EconomyService) raw;
                currency = economy.getDefaultCurrency();
            }
        }
    }

    /**
     * Checks if MinigameCore found an economy plugin.
     * 
     * @return If MinigameCore found an economy plugin.
     */
    public boolean foundEconomy() {
        return foundProvider;
    }

    /**
     * Sets the balance of a player.
     * 
     * @param uuid UUID of the player whose balance to change.
     * @param amount The amount to set the player's balance to.
     * @return Boolean based on if the method was successful or not.
     */
    public boolean setBalance(UUID uuid, BigDecimal amount) {
        if (!foundProvider)
            return false;
        currency = economy.getDefaultCurrency();
        Optional<UniqueAccount> uOpt = economy.getOrCreateAccount(uuid);
        if (!uOpt.isPresent())
            return false;
        UniqueAccount account = uOpt.get();
        if (account.setBalance(currency, amount, cause).getResult().equals(ResultType.SUCCESS)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gets the balance of a player.
     * 
     * @param uuid UUID of the player to get the balance of.
     * @return The balance of the player.
     */
    public Optional<BigDecimal> getBalance(UUID uuid) {
        if (!foundProvider)
            return Optional.empty();
        currency = economy.getDefaultCurrency();
        Optional<UniqueAccount> uOpt = economy.getOrCreateAccount(uuid);
        if (!uOpt.isPresent())
            return Optional.empty();
        UniqueAccount account = uOpt.get();
        return Optional.of(account.getBalance(currency));
    }

    /**
     * Adds currency to a players balance.
     * 
     * @param uuid UUID of the player whose balance to change.
     * @param amount Amount of currency to add to the player.
     * @return Boolean based on if the method was successful or not.
     */
    public boolean addCurrency(UUID uuid, BigDecimal amount) {
        if (!foundProvider)
            return false;
        currency = economy.getDefaultCurrency();
        Optional<UniqueAccount> uOpt = economy.getOrCreateAccount(uuid);
        if (!uOpt.isPresent())
            return false;
        UniqueAccount account = uOpt.get();
        if (account.deposit(currency, amount, cause).getResult().equals(ResultType.SUCCESS)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Removes currency from a players balance.
     * 
     * @param uuid UUID of the player whose balance to change.
     * @param amount Amount of currency to remove from the player.
     * @return Boolean based on if the method was successful or not.
     */
    public boolean removeCurrency(UUID uuid, BigDecimal amount) {
        if (!foundProvider)
            return false;
        currency = economy.getDefaultCurrency();
        Optional<UniqueAccount> uOpt = economy.getOrCreateAccount(uuid);
        if (!uOpt.isPresent())
            return false;
        UniqueAccount account = uOpt.get();
        if (account.withdraw(currency, amount, cause).getResult().equals(ResultType.SUCCESS)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gets the currency the server is using.
     * 
     * @return The currency the server is using.
     */
    public Optional<Currency> getCurrency() {
        if (!foundProvider)
            return Optional.empty();
        return Optional.of(economy.getDefaultCurrency());
    }

}
