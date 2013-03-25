/*
 * Copyright (c) 2013. ToppleTheNun
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package de.funkyclan.mc.RepairRecipe;

import com.conventnunnery.plugins.MythicDrops.MythicDrops;
import de.funkyclan.mc.RepairRecipe.Listener.CraftingListener;
import de.funkyclan.mc.RepairRecipe.Recipe.ShapelessRepairRecipe;
import net.minecraft.server.v1_5_R2.Packet103SetSlot;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_5_R2.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_5_R2.inventory.CraftItemStack;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class RepairRecipe {

	private final MythicDrops plugin;
	private Set<ShapelessRepairRecipe> repairRecipes;

	public RepairRecipe(MythicDrops plugin) {
		this.plugin = plugin;
	}

	public MythicDrops getPlugin() {
		return plugin;
	}

	public Set<ShapelessRepairRecipe> getRepairRecipes() {
		return repairRecipes;
	}

	public void onEnable() {

		getPlugin().getServer().getPluginManager().registerEvents(new CraftingListener(this), getPlugin());


		repairRecipes = new HashSet<ShapelessRepairRecipe>();

		if (repairRecipes.size() == 0) {
			return;
		}

	}

	public ShapelessRepairRecipe getRepairRecipeFor(ItemStack itemStack) {
		return getRepairRecipeFor(itemStack.getType());
	}

	public ShapelessRepairRecipe getRepairRecipeFor(Material item) {
		for (ShapelessRepairRecipe recipe : repairRecipes) {
			if (recipe.getResult().getType().equals(item)) {
				return recipe;
			}
		}
		return null;
	}

	public void updateSlotInventory(HumanEntity player, ItemStack item, int index) {
		if (player instanceof CraftPlayer) {
			CraftPlayer craftPlayer = (CraftPlayer) player;
			if (craftPlayer.getHandle().activeContainer != null) {
				Packet103SetSlot packet = new Packet103SetSlot();
				packet.a = craftPlayer.getHandle().activeContainer.windowId;
				packet.b = index;
				packet.c = CraftItemStack.asNMSCopy(item);

				craftPlayer.getHandle().playerConnection.sendPacket(packet);
			}
		}
	}


}
