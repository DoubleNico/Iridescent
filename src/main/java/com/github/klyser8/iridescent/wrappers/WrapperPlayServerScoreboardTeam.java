/*
	Original author: https://github.com/H4kt
	Thanks to him for this amazing code
 */
package com.github.klyser8.iridescent.wrappers;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.InternalStructure;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.reflect.IntEnum;
import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.WrappedChatComponent;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class WrapperPlayServerScoreboardTeam extends AbstractPacket {
	public static final PacketType TYPE =
			PacketType.Play.Server.SCOREBOARD_TEAM;
	private InternalStructure internalPacket;


	public WrapperPlayServerScoreboardTeam() {
		super(new PacketContainer(TYPE), TYPE);
		handle.getModifier().writeDefaults();
		Optional<InternalStructure> optInternalPacket = this.getHandle().getOptionalStructures().read(0);
		internalPacket = optInternalPacket.get();
	}

	public WrapperPlayServerScoreboardTeam(PacketContainer packet) {
		super(packet, TYPE);
		Optional<InternalStructure> optInternalPacket = this.getHandle().getOptionalStructures().read(0);
		optInternalPacket.ifPresent(internalStructure -> internalPacket = internalStructure);
	}

	/**
	 * Enum containing all known modes.
	 *
	 * @author dmulloy2
	 */
	public static class Mode extends IntEnum {
		public static final int TEAM_CREATED = 0;
		public static final int TEAM_REMOVED = 1;
		public static final int TEAM_UPDATED = 2;
		public static final int PLAYERS_ADDED = 3;
		public static final int PLAYERS_REMOVED = 4;

		private static final Mode INSTANCE = new Mode();

		public static Mode getInstance() {
			return INSTANCE;
		}
	}

	/**
	 * Retrieve Team Name.
	 * <p>
	 * Notes: a unique name for the team. (Shared with scoreboard).
	 *
	 * @return The current Team Name
	 */
	public String getName() {
		return handle.getStrings().read(0);
	}

	/**
	 * Set Team Name.
	 *
	 * @param value - new value.
	 */
	public void setName(String value) {
		handle.getStrings().write(0, value);
	}

	/**
	 * Retrieve Team Display Name.
	 * <p>
	 * Notes: only if Mode = 0 or 2.
	 *
	 * @return The current Team Display Name
	 */
	public WrappedChatComponent getDisplayName() {
		if(!(internalPacket == null))
			return internalPacket.getChatComponents().readSafely(0);
		else
			return null;
	}

	/**
	 * Set Team Display Name.
	 *
	 * @param value - new value.
	 */
	public void setDisplayName(WrappedChatComponent value) {
		internalPacket.getChatComponents().writeSafely(0, value);
	}

	/**
	 * Retrieve Team Prefix.
	 * <p>
	 * Notes: only if Mode = 0 or 2. Displayed before the players' name that are
	 * part of this team.
	 *
	 * @return The current Team Prefix
	 */
	public WrappedChatComponent getPrefix() {
		return internalPacket.getChatComponents().readSafely(1);
	}

	/**
	 * Set Team Prefix.
	 *
	 * @param value - new value.
	 */
	public void setPrefix(WrappedChatComponent value) {
		internalPacket.getChatComponents().writeSafely(1, value);
	}

	/**
	 * Retrieve Team Suffix.
	 * <p>
	 * Notes: only if Mode = 0 or 2. Displayed after the players' name that are
	 * part of this team.
	 *
	 * @return The current Team Suffix
	 */
	public WrappedChatComponent getSuffix() {
		return internalPacket.getChatComponents().readSafely(2);
	}

	/**
	 * Set Team Suffix.
	 *
	 * @param value - new value.
	 */
	public void setSuffix(WrappedChatComponent value) {
		internalPacket.getChatComponents().writeSafely(2, value);
	}

	/**
	 * Retrieve Name Tag Visibility.
	 * <p>
	 * Notes: only if Mode = 0 or 2. always, hideForOtherTeams, hideForOwnTeam,
	 * never.
	 *
	 * @return The current Name Tag Visibility
	 */
	public String getNameTagVisibility() {
		return internalPacket.getStrings().readSafely(0);
	}

	/**
	 * Set Name Tag Visibility.
	 *
	 * @param value - new value.
	 */
	public void setNameTagVisibility(String value) {
		internalPacket.getStrings().write(0, value);
	}

	/**
	 * Retrieve Color.
	 * <p>
	 * Notes: only if Mode = 0 or 2. Same as Chat colors.
	 *
	 * @return The current Color
	 */
	public ChatColor getColor() {
		return internalPacket.getEnumModifier(ChatColor.class, MinecraftReflection.getMinecraftClass("EnumChatFormat")).read(0);
	}

	/**
	 * Set Color.
	 *
	 * @param value - new value.
	 */
	public void setColor(ChatColor value) {
		internalPacket.getEnumModifier(ChatColor.class, MinecraftReflection.getMinecraftClass("EnumChatFormat")).write(0, value);
	}

	/**
	 * Get the collision rule.
	 * Notes: only if Mode = 0 or 2. always, pushOtherTeams, pushOwnTeam, never.
	 * @return The current collision rule
	 */
	public String getCollisionRule() {
		return internalPacket.getStrings().read(1);
	}

	/**
	 * Sets the collision rule.
	 * @param value - new value.
	 */
	public void setCollisionRule(String value) {
		internalPacket.getStrings().write(1, value);
	}

	/**
	 * Retrieve Players.
	 * <p>
	 * Notes: only if Mode = 0 or 3 or 4. Players to be added/remove from the
	 * team. Max 40 characters so may be uuid's later
	 *
	 * @return The current Players
	 */
	@SuppressWarnings("unchecked")
	public List<String> getPlayers() {
		return (List<String>) handle.getSpecificModifier(Collection.class)
				.read(0);
	}

	/**
	 * Set Players.
	 *
	 * @param value - new value.
	 */
	public void setPlayers(List<String> value) {
		handle.getSpecificModifier(Collection.class).write(0, value);
	}

	/**
	 * Retrieve Mode.
	 * <p>
	 * Notes: if 0 then the team is created. If 1 then the team is removed. If 2
	 * the team team information is updated. If 3 then new players are added to
	 * the team. If 4 then players are removed from the team.
	 *
	 * @return The current Mode
	 */
	public int getMode() {
		return handle.getIntegers().read(0);
	}

	/**
	 * Set Mode.
	 *
	 * @param value - new value.
	 */
	public void setMode(int value) {
		handle.getIntegers().write(0, value);
	}

	/**
	 * Retrieve pack option data. Pack data is calculated as follows:
	 *
	 * <pre>
	 * <code>
	 * int data = 0;
	 * if (team.allowFriendlyFire()) {
	 *     data |= 1;
	 * }
	 * if (team.canSeeFriendlyInvisibles()) {
	 *     data |= 2;
	 * }
	 * </code>
	 * </pre>
	 *
	 * @return The current pack option data
	 */
	public int getPackOptionData() {
		return internalPacket.getIntegers().read(1);
	}

	/**
	 * Set pack option data.
	 *
	 * @param value - new value
	 * @see #getPackOptionData()
	 */
	public void setPackOptionData(int value) {
		internalPacket.getIntegers().write(1, value);
	}
}