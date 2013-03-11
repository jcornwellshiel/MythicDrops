package com.conventnunnery.plugins.MythicDrops.api;

import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import com.conventnunnery.plugins.MythicDrops.MythicDrops;
import com.conventnunnery.plugins.MythicDrops.objects.Tier;

public class DropAPI {
	private final MythicDrops plugin;

	public DropAPI(MythicDrops plugin) {
		this.plugin = plugin;
	}

	public ItemStack constructItemStack() {
		return constructItemStack(getPlugin().getTierAPI()
				.randomTierWithChance());
	}

	public ItemStack constructItemStack(Tier tier) {
		ItemStack itemstack = null;
		MaterialData matData = null;
		int attempts = 0;
		while (matData == null && attempts < 10) {
			matData = getPlugin().getItemAPI().getMatDataFromTier(tier);
		}
		if (matData == null || matData.getItemTypeId() == 0
				|| matData.getItemType() == Material.AIR)
			return itemstack;
		itemstack = matData.toItemStack(1);
		ItemMeta im;
		if (itemstack.hasItemMeta())
			im = itemstack.getItemMeta();
		else
			im = Bukkit.getItemFactory().getItemMeta(matData.getItemType());
		im.setDisplayName(tier.getColor()
				+ getPlugin().getNameAPI().randomFormattedName(
						itemstack.getData()) + tier.getIdentifier());
		for (Entry<Enchantment, Integer> e : tier.getAutomaticEnchantments()
				.entrySet()) {
			if (e.getValue() > 0)
				im.addEnchant(e.getKey(), e.getValue(), true);
		}
		for (Entry<Enchantment, Integer> e : tier.getNaturalEnchantments()
				.entrySet()) {
			if (e.getKey().canEnchantItem(itemstack))
				im.addEnchant(e.getKey(), e.getValue(), true);
		}
		for (int i = 0; i < tier.getMaxNumberOfRandomEnchantments(); i++) {
			int lev = getPlugin().random.nextInt(tier
					.getMaxLevelOfRandomEnchantments()) + 1;
			Enchantment ench = tier.getAllowedEnchantments().get(
					getPlugin().random.nextInt(tier.getAllowedEnchantments()
							.size()));
			if (ench.canEnchantItem(itemstack))
				im.addEnchant(ench, lev, true);
		}
		itemstack.setItemMeta(im);
		return itemstack;
	}

	/**
	 * @return the plugin
	 */
	public MythicDrops getPlugin() {
		return plugin;
	}
}
