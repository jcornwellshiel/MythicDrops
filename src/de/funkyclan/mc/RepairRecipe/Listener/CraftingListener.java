/*
 * Copyright (c) 2013. ToppleTheNun
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package de.funkyclan.mc.RepairRecipe.Listener;

import de.funkyclan.mc.RepairRecipe.Recipe.ShapelessRepairRecipe;
import de.funkyclan.mc.RepairRecipe.RepairRecipe;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Listener for the Workbench and CraftingMatrix in Inventory
 */
public class CraftingListener implements Listener {

	private RepairRecipe plugin;

	public CraftingListener(RepairRecipe repairRecipe) {
		plugin = repairRecipe;
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onCraftItem(CraftItemEvent event) {
		if (!(event.getWhoClicked() instanceof Player) || event.isCancelled()) {
			return;
		}
		Player player = (Player) event.getWhoClicked();

		ShapelessRepairRecipe recipe = plugin.getRepairRecipeFor(event.getRecipe().getResult());

		if (recipe != null && recipe.checkIngredients(event.getInventory().getMatrix())) {
			List<HumanEntity> players = new ArrayList<HumanEntity>();
			players.add(player);

			ItemStack repairedItem = recipe.repairItem(event.getInventory(), true, players);
			if (repairedItem == null) {
				event.setResult(Event.Result.DENY);
				return;
			}

			event.setCurrentItem(repairedItem);
			player.playSound(player.getLocation(), Sound.ANVIL_USE, 1f, 1f);
		}


	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPrepareItemCraft(PrepareItemCraftEvent event) {
		ShapelessRepairRecipe recipe = plugin.getRepairRecipeFor(event.getRecipe().getResult());

		if (recipe != null && recipe.checkIngredients(event.getInventory().getMatrix())) {
			ItemStack repairedItem = recipe.repairItem(event.getInventory(), false, event.getViewers());
			event.getInventory().setResult(repairedItem);
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onInventoryEvent(InventoryClickEvent event) {
		if (event.isCancelled()) return;
		if (event.getRawSlot() > 0 && event.getRawSlot() < event.getView().getTopInventory().getSize() &&
				event.getInventory().getItem(0) != null) {
			ShapelessRepairRecipe recipe = plugin.getRepairRecipeFor(event.getInventory().getItem(0));
			if (recipe != null && (event.getInventory().getType().equals(InventoryType.WORKBENCH) ||
					event.getInventory().getType().equals(InventoryType.CRAFTING))) {
				CraftingInventory inventory = (CraftingInventory) event.getInventory();

				// clone matrix otherwise we dupe items
				ItemStack[] matrix = new ItemStack[inventory.getMatrix().length];
				int i = 0;
				for (ItemStack itemStack : inventory.getMatrix()) {
					if (itemStack != null) {
						matrix[i] = itemStack.clone();
					}
					i++;
				}

				ItemStack item = matrix[event.getRawSlot() - 1];
				if (item.getType().equals(Material.AIR)) {
					item = event.getCursor().clone();
				}
				if (event.isRightClick()) {
					item.setAmount(item.getAmount() + 1);
				} else if (event.isLeftClick()) {
					item.setAmount(item.getAmount() + event.getCursor().getAmount());
				} else if (event.isShiftClick()) {
					item.setAmount(0);
				}

				if (recipe.checkIngredients(matrix)) {
					ItemStack[] tempMatrix = inventory.getMatrix();
					inventory.setMatrix(matrix);
					ItemStack repairedItem = recipe.repairItem(inventory, false, event.getViewers());
					inventory.setResult(repairedItem);
					inventory.setMatrix(tempMatrix);

					for (HumanEntity entity : event.getViewers()) {
						plugin.updateSlotInventory(entity, repairedItem, 0);
					}
				}
			}
		}

	}


}
