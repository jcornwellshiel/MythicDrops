package com.conventnunnery.plugins.MythicDrops.builders;

import java.util.ArrayList;
import java.util.Arrays;
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
			if (!fc.isConfigurationSection(tierName))
				continue;
			ConfigurationSection cs = fc.getConfigurationSection(tierName);
			String displayName = cs.getString("displayName", tierName);
			ChatColor color;
			try {
				color = ChatColor.valueOf(cs.getString("color", "RESET")
						.toUpperCase());
			}
			catch (Exception e) {
				color = ChatColor.RESET;
			}
			ChatColor identifier;
			try {
				identifier = ChatColor.valueOf(cs.getString("identifier",
						"RESET").toUpperCase());
			}
			catch (Exception e) {
				identifier = ChatColor.RESET;
			}

			if (!cs.isConfigurationSection("enchantment")) {
				cs.createSection("enchantment");
				continue;
			}
			ConfigurationSection enchCS = cs
					.getConfigurationSection("enchantment");
			int maxNumberOfRandomEnchantments = enchCS.getInt("amount", 0);
			int maxLevelOfRandomEnchantments = enchCS.getInt("level", 0);

			HashMap<Enchantment, Integer> automaticEnchantments = new HashMap<Enchantment, Integer>();
			if (enchCS.isConfigurationSection("automatic")) {
				ConfigurationSection autoCS = enchCS
						.getConfigurationSection("automatic");
				for (String enchantmentName : autoCS.getKeys(false)) {
					Enchantment ench = null;
					for (Enchantment ec : Enchantment.values()) {
						if (ec.getName().equalsIgnoreCase(enchantmentName)) {
							ench = ec;
							break;
						}
					}
					if (ench == null)
						continue;
					int level = autoCS.getInt(enchantmentName, 0);
					getPlugin().getDebug().debug(
							"Tier: " + tierName + " | Enchantment: "
									+ ench.getName() + " | Level: " + level);
					automaticEnchantments.put(ench, level);
				}
			}
			else {
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
					}
					catch (Exception e) {
						continue;
					}
					int level = naturalCS.getInt(enchantmentName, 0);
					naturalEnchantments.put(ench, level);
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
			if (allowedEnchantments.isEmpty()) {
				allowedEnchantments.addAll(Arrays.asList(Enchantment.values()));
			}
			List<String> itemTypes = new ArrayList<String>();
			if (fc.contains(tierName + ".items.types")) {
				itemTypes = fc.getStringList(tierName + ".items.types");
			}
			else {
				itemTypes.addAll(getPlugin().getPluginSettings().getIDs()
						.keySet());
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
		getPlugin().getConfigurationManager().saveConfig();
	}

	/**
	 * @return the plugin
	 */
	public MythicDrops getPlugin() {
		return plugin;
	}

}
