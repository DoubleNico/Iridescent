/**
 * PacketWrapper - ProtocolLib wrappers for Minecraft packets
 * Copyright (C) dmulloy2 <http://dmulloy2.net>
 * Copyright (C) Kristian S. Strangeland
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.klyser8.iridescent.wrappers;

import java.lang.reflect.Field;
import java.util.UUID;

import com.comphenix.protocol.events.InternalStructure;
import com.comphenix.protocol.reflect.StructureModifier;
import net.minecraft.network.protocol.game.PacketPlayOutBoss;
import net.minecraft.network.protocol.game.PacketPlayOutScoreboardTeam;
import org.bukkit.boss.BarColor;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;

public class WrapperPlayServerBoss extends AbstractPacket {

	public static final PacketType TYPE = PacketType.Play.Server.BOSS;

	public WrapperPlayServerBoss() {
		super(new PacketContainer(TYPE), TYPE);
		handle.getModifier().writeDefaults();
	}

	public WrapperPlayServerBoss(PacketContainer packet) {
		super(packet, TYPE);
	}

	public WrappedChatComponent getTitle() {
		return handle.getChatComponents().read(0);
	}

	public void setTitle(WrappedChatComponent value) {
		handle.getChatComponents().write(0, value);
	}
}