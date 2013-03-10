package com.conventnunnery.plugins.MythicDrops.builders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;

import com.conventnunnery.plugins.MythicDrops.MythicDrops;
import com.conventnunnery.plugins.MythicDrops.configuration.CommentedYamlConfiguration;
import com.conventnunnery.plugins.MythicDrops.configuration.ConfigurationManager.ConfigurationFile;
import com.conventnunnery.plugins.MythicDrops.objects.Tier;

public class TierBuilder {

	private final MythicDrops plugin;

	public TierBuilder(MythicDrops plugin) {
		this.plugin = plugin;
	}

	public void build() {
		CommentedYamlConfiguration cyc = getPlugin().getConfigurationManager()
				.getConfiguration(ConfigurationFile.TIER);
		for (String tierName : cyc.getKeys(false)) {
			String displayName = cyc.getString(tierName + ".displayName");
			ChatColor color;
			try {
				color = ChatColor.valueOf(cyc.getString(tierName + ".color"));
			}
			catch (Exception e) {
				color = ChatColor.RESET;
			}
			ChatColor identifier;
			try {
				identifier = ChatColor.valueOf(cyc.getString(tierName
						+ ".identifier"));
			}
			catch (Exception e) {
				identifier = ChatColor.RESET;
			}
			int maxNumberOfRandomEnchantments = cyc.getInt(cyc
					+ "enchantment.amount", 0);
			int maxLevelOfRandomEnchantments = cyc.getInt(cyc
					+ "enchantment.level", 0);
			HashMap<Enchantment, Integer> automaticEnchantments = new HashMap<Enchantment, Integer>();
			for (String enchantmentName : cyc.getConfigurationSection(
					tierName + ".enchantment.automatic").getKeys(false)) {
				Enchantment ench;
				try {
					ench = Enchantment.getByName(enchantmentName.toUpperCase());
				}
				catch (Exception e) {
					continue;
				}
				automaticEnchantments.put(
						ench,
						cyc.getInt(tierName + ".enchantment.automatic."
								+ enchantmentName, 0));
			}
			HashMap<Enchantment, Integer> naturalEnchantments = new HashMap<Enchantment, Integer>();
			for (String enchantmentName : cyc.getConfigurationSection(
					tierName + ".enchantment.natural").getKeys(false)) {
				Enchantment ench;
				try {
					ench = Enchantment.getByName(enchantmentName.toUpperCase());
				}
				catch (Exception e) {
					continue;
				}
				automaticEnchantments.put(
						ench,
						cyc.getInt(tierName + ".enchantment.natural."
								+ enchantmentName, 0));
			}
			List<Enchantment> allowedEnchantments = new ArrayList<Enchantment>();
			for (String enchantmentName : cyc.getStringList(tierName
					+ ".enchantment.allowedEnchants")) {
				Enchantment ench;
				try {
					ench = Enchantment.getByName(enchantmentName.toUpperCase());
				}
				catch (Exception e) {
					continue;
				}
				allowedEnchantments.add(ench);
			}
			List<String> itemTypes = new ArrayList<String>();
			itemTypes = cyc.getStringList(tierName + ".items.types");
			if (itemTypes == null)
				itemTypes = new ArrayList<String>();
			List<String> itemIDs = new ArrayList<String>();
			itemIDs = cyc.getStringList(tierName + ".items.ids");
			if (itemIDs == null)
				itemIDs = new ArrayList<String>();
			double chanceToBeGiven = cyc.getDouble(tierName
					+ ".chanceToBeGiven", 0.0);
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
