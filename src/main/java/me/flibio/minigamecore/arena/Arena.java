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

import com.google.common.collect.ImmutableList;
import me.flibio.minigamecore.events.ArenaStateChangeEvent;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.effect.sound.SoundType;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Arena {

    private CopyOnWriteArrayList<ArenaState> arenaStates = new CopyOnWriteArrayList<ArenaState>(getDefaultArenaStates());
    private ConcurrentHashMap<ArenaState, Runnable> runnables = new ConcurrentHashMap<ArenaState, Runnable>();

    private CopyOnWriteArrayList<Player> onlinePlayers = new CopyOnWriteArrayList<Player>();
    private ArenaState arenaState;

    private ArenaData arenaData;

    private Game game;

    /**
     * An arena is an object that can handle spawn locations, lobbies, games, and more.
     *
     * @param arenaName The name of the arena
     * @param game An instance of the game
     * @param plugin An instance of the main class of your plugin
     */
    public Arena(String arenaName, Game game, Object plugin) {
        this.arenaData = new ArenaData(arenaName);
        this.game = game;
        this.arenaState = ArenaStates.LOBBY_WAITING;

        game.getEventManager().registerListeners(plugin, this);
    }

    /**
     * Adds an online player.
     *
     * @param player The player to add
     */
    public abstract void addOnlinePlayer(Player player);

    /**
     * Removes an online player.
     *
     * @param player The player to remove
     */
    public abstract void removeOnlinePlayer(Player player);

    /**
     * Gets all of the players in an arena.
     *
     * @return All the players in the arena
     */
    public List<Player> getOnlinePlayers() {
        return ImmutableList.copyOf(this.onlinePlayers);
    }

    /**
     * Calls an state change on the arena.
     *
     * @param changeTo The state to change the arena to
     */
    public void arenaStateChange(ArenaState changeTo) {
        if (!this.arenaStates.contains(changeTo)) {
            return;
        }
        this.arenaState = changeTo;
        //Post the arena state change event
        this.game.getEventManager().post(new ArenaStateChangeEvent(this));
        //Run a runnable if it is set
        if (arenaStateRunnableExists(changeTo)) {
            this.runnables.get(changeTo).run();
        }
    }

    // Other Arena Properties

    /**
     * Gets the arena data.
     *
     * @return The arena data
     */
    public ArenaData getData() {
        return this.arenaData;
    }

    /**
     * Sets the arena data.
     *
     * @param data The arena data to set
     */
    public void overrideData(ArenaData data) {
        this.arenaData = data;
    }

    /**
     * Gets the state of the arena.
     *
     * @return The state of the arena
     */
    public ArenaState getArenaState() {
        return this.arenaState;
    }

    /**
     * Adds a new arena state.
     *
     * @param state The arena state to add
     * @return If the method was successful or not
     */
    public boolean addArenaState(ArenaState state) {
        // Check if the state exists
        if (arenaStateExists(state)) {
            return false;
        } else {
            this.arenaStates.add(state);
            return true;
        }
    }

    /**
     * Removes an arena state.
     *
     * @param state The arena state to remove
     * @return If the method was successful or not
     */
    public boolean removeArenaState(ArenaState state) {
        // Check if the state is a default state
        if (getDefaultArenaStates().contains(state) || !arenaStateExists(state)) {
            return false;
        } else {
            if (this.runnables.keySet().contains(state)) {
                this.runnables.remove(state);
            }
            this.arenaStates.remove(state);
            return true;
        }
    }

    /**
     * Checks if an arena state exists.
     *
     * @param arenaState The arena state to check for
     * @return If the arena state exists
     */
    public boolean arenaStateExists(ArenaState arenaState) {
        return this.arenaStates.contains(arenaState);
    }

    /**
     * Gets a list of the default arena states.
     *
     * @return A list of the default arena states
     */
    public List<ArenaState> getDefaultArenaStates() {
        return Arrays.asList(ArenaStates.LOBBY_WAITING, ArenaStates.LOBBY_COUNTDOWN, ArenaStates.GAME_COUNTDOWN,
                ArenaStates.GAME_PLAYING, ArenaStates.GAME_OVER, ArenaStates.COUNTDOWN_CANCELLED);
    }

    /**
     * Adds an arena state runnable.
     *
     * @param state The state to add
     * @param runnable The runnable to add
     * @return If the method was successful or not
     */
    public boolean addArenaStateRunnable(ArenaState state, Runnable runnable) {
        if (!arenaStateExists(state) || arenaStateRunnableExists(state)) {
            return false;
        }
        this.runnables.put(state, runnable);
        return true;
    }

    /**
     * Removes an arena state runnable.
     *
     * @param state The arena state to remove
     * @return If the method was successful or not
     */
    public boolean removeArenaStateRunnable(ArenaState state) {
        if (!arenaStateExists(state) || !arenaStateRunnableExists(state)) {
            return false;
        }
        this.runnables.remove(state);
        return true;
    }

    /**
     * Checks if an arena state runnable exists.
     *
     * @param state The state to check for
     * @return If the arena state runnable exists
     */
    public boolean arenaStateRunnableExists(ArenaState state) {
        return this.runnables.keySet().contains(state);
    }

    /**
     * Gets an arena state runnable.
     *
     * @param state The state to get the runnable of
     * @return The arena state runnable
     */
    public Optional<Runnable> getArenaStateRunnable(ArenaState state) {
        if (arenaStateRunnableExists(state)) {
            return Optional.of(this.runnables.get(state));
        } else {
            return Optional.empty();
        }
    }

    /**
     * Deserializes XML formatted text. Example:
     * {@code <c n="red">Something went wrong!</c>}
     *
     * @param text The text to deserialize
     * @return The deserialzed text
     */
    public Text deserialize(String text) {
        return TextSerializers.TEXT_XML.deserialize(text);
    }

    /**
     * Deserializes XML formatted text. Example:
     * {@code <c n="green">%name% has joined the game!</c>}
     *
     * @param text The text to deserialize
     * @param old The string to replace before deserialization
     * @param replacement What to replace the string with
     * @return The deserialzed text
     */
    public Text deserialize(String text, String old, String replacement) {
        return TextSerializers.TEXT_XML.deserialize(text.replaceAll(old, replacement));
    }

    /**
     * Broadcasts the message to the entire server.
     *
     * @param text The text to broadcast
     */
    public void broadcast(Text text) {
        Sponge.getGame().getServer().getBroadcastChannel().send(text);
    }

    /**
     * Plays a sound to all players in the game.
     *
     * @param type The type of sound to play
     * @param volume The volume of the sound
     * @param pitch The pitch of the sound
     */
    public void broadcastSound(SoundType type, int volume, int pitch) {
        for (Player player : this.onlinePlayers) {
            player.playSound(type, player.getLocation().getPosition(), volume, pitch);
        }
    }

    // Listeners

    @Listener
    public void onPlayerDisconnect(ClientConnectionEvent.Disconnect event) {
        if (this.arenaData.isTriggerPlayerEvents()) {
            Player player = event.getTargetEntity();
            removeOnlinePlayer(player);
            event.setChannel(MessageChannel.TO_NONE);
        }
    }

    @Listener
    public void onPlayerJoin(ClientConnectionEvent.Join event) {
        if (this.arenaData.isTriggerPlayerEvents()) {
            Player player = event.getTargetEntity();
            addOnlinePlayer(player);
            event.setChannel(MessageChannel.TO_NONE);
        }
    }

    @Listener
    public void onBlockModify(ChangeBlockEvent event) {
        Optional<Player> playerOptional = event.getCause().first(Player.class);
        if (!playerOptional.isPresent()) {
            return;
        }
        if (!this.arenaData.isModifyLobbyBlocks()) {
            if (this.arenaState.equals(ArenaStates.COUNTDOWN_CANCELLED) || this.arenaState.equals(ArenaStates.LOBBY_COUNTDOWN) ||
                    this.arenaState.equals(ArenaStates.LOBBY_WAITING)) {
                event.setCancelled(true);
            }
        }
    }

    @Listener
    public void onPlayerDamage(DamageEntityEvent event) {
        Optional<Player> playerOptional = event.getCause().first(Player.class);
        if (event.getTargetEntity() instanceof Player || playerOptional.isPresent()) {
            if (!this.arenaData.isAllowLobbyDamage()) {
                if (this.arenaState.equals(ArenaStates.COUNTDOWN_CANCELLED) || this.arenaState.equals(ArenaStates.LOBBY_COUNTDOWN) ||
                        this.arenaState.equals(ArenaStates.LOBBY_WAITING)) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
