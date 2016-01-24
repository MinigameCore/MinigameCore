package me.flibio.minigamecore.arena;

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
	private ConcurrentHashMap<ArenaState,Runnable> runnables = new ConcurrentHashMap<ArenaState,Runnable>();
	
	private CopyOnWriteArrayList<Player> onlinePlayers = new CopyOnWriteArrayList<Player>();
	private ArenaState arenaState;
	
	private ArenaData arenaData;
	
	private Game game;

	/**
	 * An arena is an object that can handle spawn locations, lobbies, games, and more.
	 * @param arenaName
	 * 	The name of the arena
	 * @param game
	 * 	An instance of the game
	 * @param plugin
	 * 	An instance of the main class of your plugin
	 */
	public Arena(String arenaName, Game game, Object plugin) {
		this.arenaData = new ArenaData(arenaName);
		this.game = game;
		this.arenaState = ArenaStates.LOBBY_WAITING;
		
		game.getEventManager().registerListeners(plugin, this);
	}
	
	/**
	 * Adds an online player
	 * @param player
	 * 	The player to add
	 */
	public abstract void addOnlinePlayer(Player player);
	
	/**
	 * Removes an online player
	 * @param player
	 * 	The player to remove
	 */
	public abstract void removeOnlinePlayer(Player player);
	
	/**
	 * Gets all of the players in an arena
	 * @return
	 * 	All the players in the arena
	 */
	public CopyOnWriteArrayList<Player> getOnlinePlayers() {
		return onlinePlayers;
	}
	
	/**
	 * Calls an state change on the arena
	 * @param changeTo
	 * 	The state to change the arena to
	 */
	public void arenaStateChange(ArenaState changeTo) {
		if(!arenaStates.contains(changeTo)) {
			return;
		}
		arenaState = changeTo;
		//Post the arena state change event
		game.getEventManager().post(new ArenaStateChangeEvent(this));
		//Run a runnable if it is set
		if(arenaStateRunnableExists(changeTo)) {
			runnables.get(changeTo).run();
		}
	}
	
	//Other Arena Properties
	
	/**
	 * Gets the arena data
	 * @return
	 * 	The arena data
	 */
	public ArenaData getData() {
		return arenaData;
	}
	
	/**
	 * Sets the arena data
	 * @param data
	 * 	The arena data to set
	 */
	public void overrideData(ArenaData data) {
		arenaData = data;
	}
	
	/**
	 * Gets the state of the arena
	 * @return
	 * 	The state of the arena
	 */
	public ArenaState getArenaState() {
		return arenaState;
	}
	
	/**
	 * Adds a new arena state
	 * @param state
	 * 	The arena state to add
	 * @return
	 * 	If the method was successful or not
	 */
	public boolean addArenaState(ArenaState state) {
		//Check ifthe state exists
		if(arenaStateExists(state)) {
			return false;
		} else {
			arenaStates.add(state);
			return true;
		}
	}
	
	/**
	 * Removes an arena state
	 * @param state
	 * 	The arena state to remove
	 * @return
	 * 	If the method was successful or not
	 */
	public boolean removeArenaState(ArenaState state) {
		//Check ifthe state is a default state
		if(getDefaultArenaStates().contains(state)||!arenaStateExists(state)) {
			return false;
		} else {
			if(runnables.keySet().contains(state)) {
				runnables.remove(state);
			}
			arenaStates.remove(state);
			return true;
		}
	}
	
	/**
	 * Checks if an arena state exists
	 * @param arenaState
	 * 	The arena state to check for
	 * @return
	 * 	If the arena state exists
	 */
	public boolean arenaStateExists(ArenaState arenaState) {
		return arenaStates.contains(arenaState);
	}
	
	/**
	 * Gets a list of the default arena states
	 * @return
	 * 	A list of the default arena states
	 */
	public List<ArenaState> getDefaultArenaStates() {
		return Arrays.asList(ArenaStates.LOBBY_WAITING,ArenaStates.LOBBY_COUNTDOWN,ArenaStates.GAME_COUNTDOWN,
				ArenaStates.GAME_PLAYING,ArenaStates.GAME_OVER,ArenaStates.COUNTDOWN_CANCELLED);
	}
	
	/**
	 * Adds an arena state runnable
	 * @param state
	 * 	The state to add
	 * @param runnable
	 * 	The runnable to add
	 * @return
	 * 	If the method was successful or not
	 */
	public boolean addArenaStateRunnable(ArenaState state, Runnable runnable) {
		if(!arenaStateExists(state)||arenaStateRunnableExists(state)) {
			return false;
		}
		runnables.put(state, runnable);
		return true;
	}
	
	/**
	 * Removes an arena state runnable
	 * @param state
	 * 	The arena state to remove
	 * @return
	 * 	If the method was successful or not
	 */
	public boolean removeArenaStateRunnable(ArenaState state) {
		if(!arenaStateExists(state)||!arenaStateRunnableExists(state)) {
			return false;
		}
		runnables.remove(state);
		return true;
	}
	
	/**
	 * Checks if an arena state runnable exists
	 * @param state
	 * 	The state to check for
	 * @return
	 * 	If the arena state runnable exists
	 */
	public boolean arenaStateRunnableExists(ArenaState state) {
		return runnables.keySet().contains(state);
	}
	
	/**
	 * Gets an arena state runnable
	 * @param state
	 * 	The state to get the runnable of
	 * @return
	 * 	The arena state runnable
	 */
	public Optional<Runnable> getArenaStateRunnable(ArenaState state) {
		if(arenaStateRunnableExists(state)) {
			return Optional.of(runnables.get(state));
		} else {
			return Optional.empty();
		}
	}
	
	/**
	 * Deserializes XML formatted text. Example:
	 * <c n="red">Something went wrong!</c>
	 * @param text
	 * 	The text to deserialize
	 * @return
	 * 	The deserialzed text
	 */
	public Text deserialize(String text) {
		return TextSerializers.TEXT_XML.deserialize(text);
	}
	
	/**
	 * Deserializes XML formatted text. Example:
	 * <c n="green">%name% has joined the game!</c>
	 * @param text
	 * 	The text to deserialize
	 * @param old
	 * 	The string to replace before deserialization
	 * @param replacement
	 * 	What to replace the string with
	 * @return
	 * 	The deserialzed text
	 */
	public Text deserialize(String text, String old, String replacement) {
		text.replaceAll(old, replacement);
		return TextSerializers.TEXT_XML.deserialize(text);
	}
	
	/**
	 * Broadcasts the message to the entire server
	 * @param text
	 * 	The text to broadcast
	 */
	public void broadcast(Text text) {
		Sponge.getGame().getServer().getBroadcastChannel().send(text);
	}
	
	/**
	 * Plays a sound to all players in the game
	 * @param type
	 * 	The type of sound to play
	 * @param volume
	 * 	The volume of the sound
	 * @param pitch
	 * 	The pitch of the sound
	 */
	public void broadcastSound(SoundType type, int volume, int pitch) {
		for(Player player : onlinePlayers) {
			player.playSound(type, player.getLocation().getPosition(), volume, pitch);
		}
	}

	//Listeners
	
	@Listener
	public void onPlayerDisconnect(ClientConnectionEvent.Disconnect event) {
		if(arenaData.isTriggerPlayerEvents()) {
			Player player = event.getTargetEntity();
			removeOnlinePlayer(player);
			event.setChannel(MessageChannel.TO_NONE);
		}
	}
	
	@Listener
	public void onPlayerJoin(ClientConnectionEvent.Join event) {
		if(arenaData.isTriggerPlayerEvents()) {
			Player player = event.getTargetEntity();
			addOnlinePlayer(player);
			event.setChannel(MessageChannel.TO_NONE);
		}
	}
	
	@Listener
	public void onBlockModify(ChangeBlockEvent event) {
		Optional<Player> playerOptional = event.getCause().first(Player.class);
		if(!playerOptional.isPresent()) return;
		if(!arenaData.isModifyLobbyBlocks()) {
			if(arenaState.equals(ArenaStates.COUNTDOWN_CANCELLED)||arenaState.equals(ArenaStates.LOBBY_COUNTDOWN)||
					arenaState.equals(ArenaStates.LOBBY_WAITING)) {
				event.setCancelled(true);
			}
		}
	}
	
	@Listener
	public void onPlayerDamage(DamageEntityEvent event) {
		Optional<Player> playerOptional = event.getCause().first(Player.class);
		if(event.getTargetEntity() instanceof Player||playerOptional.isPresent()) {
			if(!arenaData.isAllowLobbyDamage()) {
				if(arenaState.equals(ArenaStates.COUNTDOWN_CANCELLED)||arenaState.equals(ArenaStates.LOBBY_COUNTDOWN)||
						arenaState.equals(ArenaStates.LOBBY_WAITING)) {
					event.setCancelled(true);
				}
			}
		}
	}
}
