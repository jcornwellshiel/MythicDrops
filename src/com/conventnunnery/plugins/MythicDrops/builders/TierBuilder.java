package com.conventnunnery.plugins.MythicDrops.builders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;

import com.conventnunnery.plugins.MythicDrops.MythicDrops;
import com.conventnunnery.plugins.MythicDrops.configuration.ConfigurationManager.ConfigurationFile;
import com.conventnunnery.plugins.MythicDrops.objects.Tier;

public class TierBuilder {

	private final MythicDrops plugin;

	public TierBuilder(MythicDrops plugin) {
		this.plugin = plugin;
	}

	public void build() {
		FileConfiguration fc = getPlugin().getConfigurationManager()
				.getConfiguration(ConfigurationFile.TIER);
		for (String tierName : fc.getKeys(false)) {
			ConfigurationSection cs = fc.getConfigurationSection(tierName);
			if (cs == null)
				continue;
			String displayName = cs.getString("displayName", tierName);
			ChatColor color;
			try {
				color = ChatColor.valueOf(fc.getString("color", "RESET"));
			}
			catch (Exception e) {
				color = ChatColor.RESET;
			}
			ChatColor identifier;
			try {
				identifier = ChatColor.valueOf(fc.getString("identifier",
						"RESET"));
			}
			catch (Exception e) {
				identifier = ChatColor.RESET;
			}
			ConfigurationSection enchCS = cs
					.getConfigurationSection("enchantment");
			int maxNumberOfRandomEnchantments = enchCS.getInt("amount", 0);
			int maxLevelOfRandomEnchantments = enchCS.getInt("level", 0);
			HashMap<Enchantment, Integer> automaticEnchantments = new HashMap<Enchantment, Integer>();
			if (enchCS.contains("automatic")) {
				for (String enchantmentName : enchCS.getConfigurationSection(
						"automatic").getKeys(false)) {
					Enchantment ench;
					try {
						ench = Enchantment.getByName(enchantmentName
								.toUpperCase());
					}
					catch (Exception e) {
						continue;
					}
					automaticEnchantments.put(ench,
							enchCS.getInt("automatic" + enchantmentName, 0));
				}
			}
			else {
				enchCS.createSection("automatic");
			}
			HashMap<Enchantment, Integer> naturalEnchantments = new HashMap<Enchantment, Integer>();
			if (enchCS.contains("natural")) {
				for (String enchantmentName : enchCS.getConfigurationSection(
						"natural").getKeys(false)) {
					Enchantment ench;
					try {
						ench = Enchantment.getByName(enchantmentName
								.toUpperCase());
					}
					catch (Exception e) {
						continue;
					}
					automaticEnchantments.put(
							ench,
							fc.getInt(tierName + ".enchantment.natural."
									+ enchantmentName, 0));
				}
			}
			else {
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
					}
					catch (Exception e) {
						continue;
					}
					allowedEnchantments.add(ench);
				}
			}
			else {
				enchCS.set("allowedEnchants", new ArrayList<String>());
			}
			List<String> itemTypes = new ArrayList<String>();
			if (fc.contains(tierName + ".items.types")) {
				itemTypes = fc.getStringList(tierName + ".items.types");
			}
			else {
				fc.set(tierName + ".items.types", itemTypes);
			}
			List<String> itemIDs = new ArrayList<String>();
			if (fc.contains(tierName + ".items.ids")) {
				itemIDs = fc.getStringList(tierName + ".items.ids");
			}
			else {
				fc.set(tierName + ".items.ids", itemIDs);
			}
			double chanceToBeGiven = fc.getDouble(
					tierName + ".chanceToBeGiven", 0.0);
			Tier tier = new Tier(tierName, displayName, color, identifier,
					maxNumberOfRandomEnchantments,
					maxLevelOfRandomEnchantments, automaticEnchantments,
					naturalEnchantments, allowedEnchantments, itemTypes,
					itemIDs, chanceToBeGiven);
			getPlugin().getTierAPI().addTier(tier);
		}
	}

	/**
	 * @return the plugin
	 */
	public MythicDrops getPlugin() {
		return plugin;
	}

}
