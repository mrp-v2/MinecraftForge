/*
 * Minecraft Forge
 * Copyright (c) 2016-2020.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation version 2.1
 * of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package net.minecraftforge.event;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.LogicalSide;

public class TickEvent extends Event
{
    public enum Type {
        WORLD, PLAYER, CLIENT, SERVER, RENDER;
    }

    public enum Phase {
        START, END;
    }
    public final Type type;
    public final LogicalSide side;
    public final Phase phase;
    public TickEvent(Type type, LogicalSide side, Phase phase)
    {
        this.type = type;
        this.side = side;
        this.phase = phase;
    }

    /**
     * This event originates from {@link net.minecraft.server.MinecraftServer#tick(java.util.function.BooleanSupplier) MinecraftServer.tick(BooleanSupplier)}.
     * It is a server event.
     */
    public static class ServerTickEvent extends TickEvent {
        public ServerTickEvent(Phase phase)
        {
            super(Type.SERVER, LogicalSide.SERVER, phase);
        }
    }

    /**
     * This event originates from {@link net.minecraft.client.Minecraft#runTick()}.
     * It is a client event.
     */
    public static class ClientTickEvent extends TickEvent {
        public ClientTickEvent(Phase phase)
        {
            super(Type.CLIENT, LogicalSide.CLIENT, phase);
        }
    }

    /**
     * This event originates from {@link net.minecraft.server.MinecraftServer#updateTimeLightAndEntities(java.util.function.BooleanSupplier) MinecraftServer.updateTimeLightAndEntities(BooleanSupplier)}.
     * It is a server event.
     * <br>
     * The event occurs during {@link ServerTickEvent ServerTickEvent}.
     */
    public static class WorldTickEvent extends TickEvent {
        public final World world;
        public WorldTickEvent(LogicalSide side, Phase phase, World world)
        {
            super(Type.WORLD, side, phase);
            this.world = world;
        }
    }

    /**
     * This event originates from {@link PlayerEntity#tick()}.
     * <br>
     * This event occurs during {@link WorldTickEvent WorldTickEvent} on a server, and {@link ClientTickEvent ClientTickEvent} on a client.
     */
    public static class PlayerTickEvent extends TickEvent {
        public final PlayerEntity player;

        public PlayerTickEvent(Phase phase, PlayerEntity player)
        {
            super(Type.PLAYER, player instanceof ServerPlayerEntity ? LogicalSide.SERVER : LogicalSide.CLIENT, phase);
            this.player = player;
        }
    }

    /**
     * This event originates from {@link net.minecraft.client.Minecraft#runGameLoop(boolean)}.
     * It is a client event.
     */
    public static class RenderTickEvent extends TickEvent {
        /**
         * TODO
         */
        public final float renderTickTime;
        public RenderTickEvent(Phase phase, float renderTickTime)
        {
            super(Type.RENDER, LogicalSide.CLIENT, phase);
            this.renderTickTime = renderTickTime;
        }
    }
}
