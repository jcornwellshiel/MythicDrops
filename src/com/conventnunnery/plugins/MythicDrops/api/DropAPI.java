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

		for (Entry<Enchantment, Integer> e : tier.getAutomaticEnchantments()
				.entrySet()) {
			if (e == null)
				continue;
			if (e.getKey() == null || e.getValue() == null)
				continue;
			im.addEnchant(e.getKey(),
					(e.getValue() == 0) ? 1 : Math.abs(e.getValue()), true);
		}
		for (Entry<Enchantment, Integer> e : tier.getNaturalEnchantments()
				.entrySet()) {
			if (e == null)
				continue;
			if (e.getKey() == null || e.getValue() == null)
				continue;
			if (e.getKey().canEnchantItem(itemstack))
				im.addEnchant(e.getKey(),
						(e.getValue() == 0) ? 1 : Math.abs(e.getValue()), true);
		}
		if (tier.getMaxNumberOfRandomEnchantments() > 0) {
			int randEnchs = getPlugin().random.nextInt(Math.abs(tier
					.getMaxNumberOfRandomEnchantments()) + 1);
			for (int i = 0; i < randEnchs; i++) {
				int lev = Math.abs(getPlugin().random.nextInt(tier
						.getMaxLevelOfRandomEnchantments() + 1)) + 1;
				List<Enchantment> allowEnchs = tier.getAllowedEnchantments();
				List<Enchantment> stackEnchs = getEnchantStack(itemstack);
				List<Enchantment> actual = new ArrayList<Enchantment>();
				for (Enchantment e : Enchantment.values()) {
					if (allowEnchs.contains(e) && stackEnchs.contains(e)) {
						actual.add(e);
					}
				}
				if (actual.size() > 0) {
					Enchantment ench = actual.get(getPlugin().random
							.nextInt(actual.size()));
					if (ench == null)
						continue;
					if (ench.canEnchantItem(itemstack))
						im.addEnchant(ench, (lev == 0) ? 1 : Math.abs(lev),
								true);
				}
			}
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
