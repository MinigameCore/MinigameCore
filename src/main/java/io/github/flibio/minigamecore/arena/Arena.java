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

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import io.github.minigamecore.api.arena.ArenaStage;
import lombok.Getter;
import lombok.Setter;
import org.spongepowered.api.Game;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.effect.sound.SoundType;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.data.ChangeDataHolderEvent;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.service.user.UserStorageService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public abstract class Arena implements io.github.minigamecore.api.arena.Arena {

    @Getter
    /**
     * The {@link ArenaStage}s.
     *
     * @returns The arena stages
     */
    private List<ArenaStage> arenaStages = new ArrayList<>();

    private int currentStagePos = 0;

    @Getter
    /**
     * Gets all of the players in an arena.
     *
     * @return All the players in the arena
     */
    protected ArrayList<UUID> onlinePlayers = new ArrayList<>();

    @Getter
    /**
     * The current {@link ArenaStage}.
     *
     * @returns The current arena stage
     */
    private ArenaStage currentStage;

    @Getter
    @Setter
    /**
     * The {@link ArenaData}.
     *
     * @params arenaData The overriding {@link ArenaData}
     * @returns The arena data
     */
    private ArenaData arenaData;

    @Getter
    /**
     * The {@link Game}.
     *
     * @returns The game
     */
    private Game game;

    @Getter
    /**
     * The {@link PluginContainer}.
     *
     * @returns The plugin
     */
    private PluginContainer plugin;

    /**
     * Creates a new Arena.
     *
     * @param arenaName The name of the arena
     * @param game An instance of the game
     * @param plugin An instance of the main class of the plugin
     * @param stages All arena stages, in order of execution
     */
    public Arena(String arenaName, Game game, PluginContainer plugin, List<ArenaStage> stages) {
        this.arenaData = new ArenaData(arenaName);
        this.game = game;
        this.plugin = plugin;
        this.arenaStages = stages;

        game.getEventManager().registerListeners(plugin, this);
    }

    // Other Arena Properties

    /**
     * Gets the {@link ArenaData}.
     *
     * @return The {@link ArenaData}.
     */
    public ArenaData getData() {
        return arenaData;
    }

    /**
     * Overrides the current {@link ArenaData}.
     *
     * @param data The {@link ArenaData} that will override the current
     * {@link ArenaData}.
     */
    public void overrideData(ArenaData data) {
        arenaData = data;
    }

    /**
     * Gets all of the {@link ArenaStage}s registered with this arena.
     *
     * @return All of the {@link ArenaStage}s registered with this arena.
     */
    public List<ArenaStage> getArenaStages() {
        return arenaStages;
    }

    /**
     * Sends a message to each player.
     *
     * @param text The text to send.
     */
    public void broadcast(Text text) {
        for (Player player : resolvePlayers(onlinePlayers)) {
            player.sendMessage(text);
        }
    }

    /**
     * Plays a sound to all players in the game.
     *
     * @param type The type of sound to play.
     * @param volume The volume of the sound.
     * @param pitch The pitch of the sound.
     */
    public void broadcastSound(SoundType type, int volume, int pitch) {
        for (Player player : resolvePlayers(onlinePlayers)) {
            player.playSound(type, player.getLocation().getPosition(), volume, pitch);
        }
    }

    private final LoadingCache<UUID, Player> playerCache = CacheBuilder.newBuilder().expireAfterAccess(10, TimeUnit.MINUTES).build(
            new CacheLoader<UUID, Player>() {

                Optional<UserStorageService> userStorageServiceOptional = game.getServiceManager().provide(UserStorageService.class);

                @Override public Player load(UUID key) throws Exception {
                    if (userStorageServiceOptional.isPresent()) {
                        UserStorageService storageService = userStorageServiceOptional.get();
                        if (storageService.get(key).isPresent()) {
                            User user = storageService.get(key).get();
                            if (user.getPlayer().isPresent()) {
                                return user.getPlayer().get();
                            } else {
                                throw new Exception("Player was not present. " + key + " has never played on this server before.");
                            }
                        } else {
                            throw new Exception(key + " is not a player.");
                        }
                    } else {
                        throw new Exception("UserStorageService is unavailable.");
                    }
                }
            });


    /**
     * Resolves a list of UUID objects to players. Be sure to check that all players are there, as not all players may be present.
     *
     * @param uuids The UUID list to resolve
     * @return The list of players
     */
    public List<Player> resolvePlayers(List<UUID> uuids) {
        List<Player> players = new ArrayList<>();
        for (UUID id : uuids) {
            try {
                players.add(playerCache.get(id));
            } catch (ExecutionException e) {
                plugin.getLogger().debug("A player by the UUID " + id.toString() + " could not be found.", e);
            }
        }
        return players;
    }

    // Listeners

    @Listener
    public void onPlayerDisconnect(ClientConnectionEvent.Disconnect event) {
        if (arenaData.isTriggerPlayerEvents()) {
            Player player = event.getTargetEntity();
            removePlayer(player);
            event.setChannel(MessageChannel.TO_NONE);
        }
    }

    @Listener
    public void onPlayerJoin(ClientConnectionEvent.Join event) {
        if (arenaData.isTriggerPlayerEvents()) {
            Player player = event.getTargetEntity();
            addPlayer(player);
            event.setChannel(MessageChannel.TO_NONE);
        }
    }

    @Listener
    public void onBlockModify(ChangeBlockEvent event, @First Player player) {
        if (event.getCause().root() instanceof Player) {
            if (onlinePlayers.contains(player.getUniqueId()) && arenaData.getPreventBlockModify().contains(getCurrentStage().getId())) {
                event.setCancelled(true);
            }
        }
    }

    @Listener
    public void onPlayerDamage(DamageEntityEvent event) {
        Entity entity = event.getTargetEntity();
        if (entity instanceof Player) {
            if (onlinePlayers.contains(((Player) entity).getUniqueId()) && arenaData.getPreventPlayerDamage().contains(getCurrentStage().getId())) {
                event.setCancelled(true);
            }
        }
    }

    @Listener
    public void onHungerChange(ChangeDataHolderEvent.ValueChange event) {
        if (arenaData.getPreventHungerLoss().contains(getCurrentStage().getId())) {
            event.getEndResult().getReplacedData().forEach(iv -> {
                if (iv.getKey().equals(Keys.FOOD_LEVEL)) {
                    event.setCancelled(true);
                }
            });
        }
    }

    @Override public List<UUID> getPlayers() {
        return getPlayers();
    }

    @Override public void next() {
        if (getArenaStages().get(++currentStagePos) != null) {
            currentStage = getArenaStages().get(currentStagePos);
            currentStage.run();
        } else {
            // EVERYTHING'S OVER, DELETE SYSTEM 32
        }
    }


}
