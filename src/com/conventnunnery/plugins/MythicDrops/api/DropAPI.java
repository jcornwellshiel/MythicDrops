package com.conventnunnery.plugins.MythicDrops.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
		im.setDisplayName(getPlugin().getNameAPI().randomFormattedName(
				itemstack.getData(), tier));
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
		if (tier.getMaxNumberOfRandomEnchantments() > 0) {
			for (int i = 0; i < getPlugin().random.nextInt(tier
					.getMaxNumberOfRandomEnchantments()); i++) {
				int lev = Math.abs(getPlugin().random.nextInt(tier
						.getMaxLevelOfRandomEnchantments() + 1)) + 1;
				List<Enchantment> enchs = tier.getAllowedEnchantments();
				if (enchs.size() == Enchantment.values().length) {
					enchs = getEnchantStack(itemstack);
				}
				Enchantment ench = enchs.get(getPlugin().random.nextInt(enchs
						.size()));
				if (ench.canEnchantItem(itemstack))
					im.addEnchant(ench, lev, true);
			}
		}
		List<String> toolTips = getPlugin().getPluginSettings()
				.getAdvancedToolTipFormat();
		List<String> tt = new ArrayList<String>();
		for (String s : toolTips) {
			tt.add(ChatColor.translateAlternateColorCodes(
					'&',
					s.replace("%itemtype%",
							getPlugin().getNameAPI().getItemTypeName(matData))
							.replace("%tiername%",
									tier.getColor() + tier.getDisplayName())
							.replace(
									"%basematerial%",
									getPlugin().getNameAPI()
											.getMinecraftMaterialName(
													itemstack.getType()))
							.replace(
									"%mythicmaterial%",
									getPlugin().getNameAPI()
											.getMythicMaterialName(
													itemstack.getData()))));
		}
		im.setLore(tt);
		itemstack.setItemMeta(im);
		return itemstack;
	}

	public List<Enchantment> getEnchantStack(final ItemStack ci) {
		List<Enchantment> set = new ArrayList<Enchantment>();
		for (Enchantment e : Enchantment.values())
			if (e.canEnchantItem(ci)) {
				set.add(e);
			}
		return set;
	}

	/**
	 * @return the plugin
	 */
	public MythicDrops getPlugin() {
		return plugin;
	}
}
