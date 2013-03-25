/*
 * Copyright (c) 2013. ToppleTheNun
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

/*
 * Originally created by deathmarine
 * Modified by Nunnery on March 10, 2013
 */

package com.modcrafting.diablodrops.builders;

import com.conventnunnery.plugins.MythicDrops.MythicDrops;
import com.conventnunnery.plugins.MythicDrops.configuration.ConfigurationManager.ConfigurationFile;
import com.conventnunnery.plugins.MythicDrops.objects.Tier;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TierBuilder {

	private final MythicDrops plugin;

	public TierBuilder(MythicDrops plugin) {
		this.plugin = plugin;
	}

	public void build() {
		FileConfiguration fc = getPlugin().getConfigurationManager()
				.getConfiguration(ConfigurationFile.TIER);
		for (String tierName : fc.getKeys(false)) {
			if (!fc.isConfigurationSection(tierName))
				continue;
			ConfigurationSection cs = fc.getConfigurationSection(tierName);
			String displayName = cs.getString("displayName");
			if (displayName == null)
				displayName = tierName;
			ChatColor color;
			try {
				String s = cs.getString("color");
				color = ChatColor.valueOf(s
						.toUpperCase());
			} catch (Exception e) {
				color = ChatColor.RESET;
			}
			ChatColor identifier;
			try {
				String s = cs.getString("identifier");
				identifier = ChatColor.valueOf(s.toUpperCase());
			} catch (Exception e) {
				identifier = ChatColor.RESET;
			}

			if (!cs.isConfigurationSection("enchantment")) {
				cs.createSection("enchantment");
				continue;
			}
			ConfigurationSection enchCS = cs
					.getConfigurationSection("enchantment");
			int minNumberOfRandomEnchantments = enchCS.getInt("minAmount");
			int maxNumberOfRandomEnchantments = enchCS.getInt("maxAmount");
			if (minNumberOfRandomEnchantments == 0 && maxNumberOfRandomEnchantments == 0) {
				if (enchCS.contains("amount")) {
					minNumberOfRandomEnchantments = enchCS.getInt("amount");
					maxNumberOfRandomEnchantments = enchCS.getInt("amount");
				}
			}
			int maxLevelOfRandomEnchantments = enchCS.getInt("level");

			HashMap<Enchantment, Integer> automaticEnchantments = new HashMap<Enchantment, Integer>();
			if (enchCS.isConfigurationSection("automatic")) {
				ConfigurationSection autoCS = enchCS
						.getConfigurationSection("automatic");
				for (String enchantmentName : autoCS.getKeys(false)) {
					Enchantment ench = null;
					for (Enchantment ec : Enchantment.values()) {
						if (ec.getName().equalsIgnoreCase(enchantmentName)) {
							int level = autoCS.getInt(enchantmentName);
							automaticEnchantments.put(ench, level);
						}
					}
				}
			} else {
				enchCS.createSection("automatic");
			}
			HashMap<Enchantment, Integer> naturalEnchantments = new HashMap<Enchantment, Integer>();
			if (enchCS.isConfigurationSection("natural")) {
				ConfigurationSection naturalCS = enchCS
						.getConfigurationSection("natural");
				for (String enchantmentName : naturalCS.getKeys(false)) {
					Enchantment ench;
					try {
						ench = Enchantment.getByName(enchantmentName
								.toUpperCase());
					} catch (Exception e) {
						continue;
					}
					int level = naturalCS.getInt(enchantmentName, 0);
					naturalEnchantments.put(ench, level);
				}
			} else {
				enchCS.createSection("natural");
			}
			List<Enchantment> allowedEnchantments = new ArrayList<Enchantment>();
			if (enchCS.contains("allowedEnchants")) {
				for (String enchantmentName : enchCS
						.getStringList("allowedEnchants")) {
					Enchantment ench;
					try {
						ench = Enchantment.getByName(enchantmentName
								.toUpperCase());
					} catch (Exception e) {
						continue;
					}
					allowedEnchantments.add(ench);
				}
			} else {
				List<String> enchs = new ArrayList<String>();
				for (Enchantment ench : Enchantment.values()) {
					enchs.add(ench.getName());
				}
				enchCS.set("allowedEnchants", enchs);
			}
			if (allowedEnchantments.isEmpty()) {
				allowedEnchantments.addAll(Arrays.asList(Enchantment.values()));
			}
			List<String> itemTypes = new ArrayList<String>();
			if (fc.contains(tierName + ".items.types")) {
				itemTypes = fc.getStringList(tierName + ".items.types");
			} else {
				itemTypes.addAll(getPlugin().getPluginSettings().getIDs()
						.keySet());
				fc.set(tierName + ".items.types", itemTypes);
			}
			List<String> itemIDs = new ArrayList<String>();
			if (fc.contains(tierName + ".items.ids")) {
				itemIDs = fc.getStringList(tierName + ".items.ids");
			} else {
				fc.set(tierName + ".items.ids", itemIDs);
			}
			double chanceToBeGiven = fc.getDouble(
					tierName + ".chanceToBeGiven");
			float chanceToDrop = (float) fc.getDouble(tierName
					+ ".chanceToDrop");
			double durability = fc.getDouble(tierName + ".durability");
			Tier tier = new Tier(tierName, displayName, color, identifier,
					minNumberOfRandomEnchantments, maxNumberOfRandomEnchantments,
					maxLevelOfRandomEnchantments, automaticEnchantments,
					naturalEnchantments, allowedEnchantments, itemTypes,
					itemIDs, chanceToBeGiven, chanceToDrop, durability);
			getPlugin().getTierAPI().addTier(tier);
		}
		getPlugin().getConfigurationManager().saveConfig();
	}

	/**
	 * @return the plugin
	 */
	public MythicDrops getPlugin() {
		return plugin;
	}

}
