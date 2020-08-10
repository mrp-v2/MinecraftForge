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

import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

/**
 * 
 * AnvilUpdateEvent is fired when a player places items in both the left and right slots of a anvil.
 * If the event is canceled, vanilla behavior will not run, and the output will be set to {@link ItemStack#EMPTY}.
 * If the event is not canceled, and the output is not {@link ItemStack#EMPTY}, it will set the output and cost, and not run vanilla behavior.
 * if the output is {@link ItemStack#EMPTY}, and the event is not canceled, vanilla behavior will execute.
 */
@Cancelable
public class AnvilUpdateEvent extends Event
{
    @Nonnull
    private final ItemStack left;
    @Nonnull
    private final ItemStack right;
    private final String name;
    @Nonnull
    private ItemStack output;
    private int cost;
    private int materialCost;

    public AnvilUpdateEvent(@Nonnull ItemStack left, @Nonnull ItemStack right, String name, int cost)
    {
        this.left = left;
        this.right = right;
        this.output = ItemStack.EMPTY;
        this.name = name;
        this.setCost(cost);
        this.setMaterialCost(0);
    }

    /**
     * Gets the left side of the anvil input.
     * */
    @Nonnull
    public ItemStack getLeft() { return left; }
    /**
     * Gets the right side of the anvil input.
     * */
    @Nonnull
    public ItemStack getRight() { return right; }
    /**
     * Gets the name of the output item.
     * */
    public String getName() { return name; }
    /**
     * Gets the output of the anvil.
     * {@link ItemStack#EMPTY} indicates vanilla behavior should be used.
     * */
    @Nonnull
    public ItemStack getOutput() { return output; }

    /**
     * Sets the output of the anvil.
     * To modify the experience cost, use {@link AnvilUpdateEvent#setCost(int)}.
     *      
     * @param output The output of the anvil.
     */
    public void setOutput(@Nonnull ItemStack output) { this.output = output; }
    /**
     * Gets the cost of the operation.
     * This is only applied if {@link AnvilUpdateEvent#setOutput(ItemStack)} is also used.
     * */
    public int getCost() { return cost; }
    /**
     * Sets the cost of the operation.
     * This is only applied if {@link AnvilUpdateEvent#setOutput(ItemStack)} is also used.
     * 
     * @param cost The cost of the operation.
     * */
    public void setCost(int cost) { this.cost = cost; }
    /**
     * Gets the number of items in the right input slot of the anvil that will be consumed by the operation.
     * A value of 0 or less means the entire stack will be consumed.
     * */
    public int getMaterialCost() { return materialCost; }
    /**
     * Sets the number of items in the right input slot of the anvil that will be consumed by the operation.
     * A value of 0 or less means the entire stack will be consumed.
     * 
     * @param materialCost The number of items to consume from the right input slot.
     * */
    public void setMaterialCost(int materialCost) { this.materialCost = materialCost; }
}
