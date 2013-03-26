package de.funkyclan.mc.RepairRecipe.Recipe;

import de.funkyclan.mc.RepairRecipe.RepairRecipe;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class ShapelessRepairRecipe extends ShapelessRecipe {

	private Material item;
	private Material ingot;
	private int ingotCost;
	private RepairRecipe plugin;

	public ShapelessRepairRecipe(Material item, Material ingot, int ingotCost, RepairRecipe plugin) {
		super(new ItemStack(item));

		this.item = item;
		addIngredient(1, item, -1);

		this.ingot = ingot;
		addIngredient(1, ingot, -1);

		this.ingotCost = ingotCost;

		this.plugin = plugin;
	}

	public boolean checkIngredients(ItemStack[] matrix) {
		List<ItemStack> list = getIngredientList();
		int usedItems = 0;
		int matrixItems = 0;
		for (ItemStack item : matrix) {
			if (item != null && item.getTypeId() != Material.AIR.getId()) {
				for (ItemStack recipeItem : list) {
					if (recipeItem.getTypeId() == item.getTypeId()) {
						usedItems++;
						list.remove(recipeItem);
						break;
					}
				}
				matrixItems++;
			}
		}

		if (usedItems == matrixItems && 0 == list.size()) {
			return true;
		} else {
			return false;
		}
	}

	public ItemStack repairItem(CraftingInventory inventory, boolean setInventory, List<HumanEntity> players) {
		ItemStack[] matrix = inventory.getMatrix();
		ItemStack repairedItem = null;
		ItemStack ingot = null;
		int ingotIndex = 0;
		int index = 1;
		for (ItemStack matrixItem : matrix) {
			if (matrixItem != null) {
				if (matrixItem.getTypeId() == this.item.getId()) {
					repairedItem = matrixItem.clone();
				}
				if (matrixItem.getTypeId() == this.ingot.getId()) {
					ingot = matrixItem;
					ingotIndex = index;
				}
			}
			index++;
		}

		if (repairedItem == null) {
			repairedItem = inventory.getResult().clone();
		}
		if (repairedItem.getDurability() <= 0) {
			return null;
		}
		if (ingot != null) {
			Map<Enchantment, Integer> enchantments = repairedItem.getEnchantments();
			int chance = plugin.getPlugin().getPluginSettings().getRepairKeepEnchantmentsChance();
			if (chance < 100) {
				Random dieGod = new Random();
				for (Enchantment ench : enchantments.keySet()) {
					if (chance == 0) {
						repairedItem.removeEnchantment(ench);
					} else {
						if (dieGod.nextInt(100) > chance) {
							repairedItem.removeEnchantment(ench);
						} else if (dieGod.nextInt(100) > chance) {
							int level = repairedItem.getEnchantmentLevel(ench);
							repairedItem.removeEnchantment(ench);
							if (level - 1 > 0)
								repairedItem.addEnchantment(ench, dieGod.nextInt(level - 1) + 1);
						}
					}
				}
			}
			double enchantLevel = 0;

			if (enchantments.size() > 0) {
				boolean highestEnchant = plugin.getPlugin().getPluginSettings().getRepairUseHighestEnchant();
				if (highestEnchant) {
					enchantLevel = Integer.MIN_VALUE;
				}
				for (Enchantment ench : enchantments.keySet()) {
					if (highestEnchant) {
						enchantLevel = Math.max(enchantLevel, repairedItem.getEnchantmentLevel(ench));
					} else {
						enchantLevel += repairedItem.getEnchantmentLevel(ench);
					}
				}
			}

			double baseRepairCost =
					(double) repairedItem.getDurability() / ((item.getMaxDurability() / this.ingotCost));

			baseRepairCost += baseRepairCost * enchantLevel;

			double discount = plugin.getPlugin().getPluginSettings().getRepairDiscount();
			baseRepairCost = baseRepairCost * discount;

			int ingotCost = new Double(Math.ceil(baseRepairCost)).intValue();
			short durability = 0;


			if (ingot.getAmount() < ingotCost) {
				ingotCost = ingot.getAmount();
				durability = (short) (repairedItem.getDurability() -
						new Double(Math.ceil(ingotCost * repairedItem.getDurability() / baseRepairCost)).shortValue());
			} else if (ingotCost > 0 && plugin.getPlugin().getPluginSettings().isRepairAllowOverRepair()) {
				durability = (short) (repairedItem.getDurability() -
						new Double(Math.ceil(ingotCost * repairedItem.getDurability() / baseRepairCost)).shortValue());
			}
			if (durability < -item.getMaxDurability()) {
				durability = (short) (-1 * item.getMaxDurability());
			}
			repairedItem.setDurability(durability);

			if (setInventory) {
				// one ingot is removed automatically by recipe cost
				ingotCost = ingotCost - 1;
				if (ingotCost > 0) {
					ingot.setAmount(ingot.getAmount() - ingotCost);
					if (ingot.getAmount() <= 1) {
						ingot.setAmount(0);
					}

					for (HumanEntity entity : players) {
						// some strange behavior here, we have also to subtract the recipe costs here...
						ItemStack sendIngot = ingot.clone();
						sendIngot.setAmount(ingot.getAmount() - 1);
						plugin.updateSlotInventory(entity, sendIngot, ingotIndex);
					}

				} else if (ingotCost == -1 && discount == 0.0) {
					ingot.setAmount(ingot.getAmount() + 1);
					for (HumanEntity entity : players) {
						// some strange behavior here, we have also to subtract the recipe costs here...
						ItemStack sendIngot = ingot.clone();
						sendIngot.setAmount(ingot.getAmount() - 1);
						plugin.updateSlotInventory(entity, sendIngot, ingotIndex);
					}
				} else if (ingotCost < 0) {
					return null;
				}
			}
		}
		// dupe fix
		repairedItem.setAmount(1);
		return repairedItem;
	}

}
