/*
 * Copyright (c) 2013. ToppleTheNun
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.conventnunnery.plugins.MythicDrops.api;

import com.conventnunnery.plugins.MythicDrops.MythicDrops;
import com.conventnunnery.plugins.MythicDrops.configuration.ConfigurationManager.ConfigurationFile;
import com.conventnunnery.plugins.MythicDrops.objects.Tier;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

/**
 * The type TierAPI.
 */
public class TierAPI {

	private final List<Tier> tiers;
	private final MythicDrops plugin;

	/**
	 * Instantiates a new TierAPI.
	 *
	 * @param plugin the plugin
	 */
	public TierAPI(MythicDrops plugin) {
		this.plugin = plugin;
		tiers = new ArrayList<Tier>();
	}

	/**
	 * Add tier.
	 *
	 * @param tier the tier
	 */
	public void addTier(Tier tier) {
		tiers.add(tier);
	}

	/**
	 * Debug tiers.
	 */
	public void debugTiers() {
		List<String> tierNames = new ArrayList<String>();
		for (Tier t : tiers) {
			tierNames.add(t.getName() + " (" + t.getChanceToBeGiven() + ")");
		}
		getPlugin().getDebug().debug(
				"Loaded tiers: "
						+ tierNames.toString().replace("[", "")
						.replace("]", ""));
	}

	/**
	 * Gets plugin.
	 *
	 * @return the plugin
	 */
	public MythicDrops getPlugin() {
		return plugin;
	}

	/**
	 * Gets tier from name.
	 *
	 * @param name the name
	 * @return the tier from name
	 */
	public Tier getTierFromName(String name) {
		for (Tier t : tiers) {
			if (t.getName().equalsIgnoreCase(name)) {
				return t;
			}
		}
		return null;
	}

	/**
	 * Gets tiers.
	 *
	 * @return the tiers
	 */
	public List<Tier> getTiers() {
		return tiers;
	}

	/**
	 * Random tier.
	 *
	 * @return the tier
	 */
	public Tier randomTier() {
		return tiers.get(getPlugin().getRandom().nextInt(tiers.size()));
	}

	/**
	 * Random tier with chance.
	 *
	 * @return the tier
	 */
	public Tier randomTierWithChance() {
		Tier tier = null;
		if (tiers == null || tiers.isEmpty())
			return tier;
		while (tier == null) {
			for (Tier t : tiers) {
				double d = plugin.getRandom().nextDouble();
				if (d <= t.getChanceToBeGiven()) {
					tier = t;
					break;
				}
			}
		}
		return tier;
	}

	/**
	 * Gets tier from ItemStack.
	 *
	 * @param itemStack the item stack
	 * @return the tier from item stack
	 */
	public Tier getTierFromItemStack(ItemStack itemStack) {
		ItemMeta im;
		if (itemStack.hasItemMeta()) {
			im = itemStack.getItemMeta();
		} else {
			return null;
		}
		String name = null;
		if (im.hasDisplayName()) {
			name = im.getDisplayName();
		} else {
			return null;
		}
		ChatColor initColor = findColor(name);
		ChatColor endColor = ChatColor.getByChar(ChatColor.getLastColors(name).substring(1));
		for (Tier t : tiers) {
			if (t.getColor() != null && t.getIdentifier() != null && t.getColor() == initColor &&
					t.getIdentifier() == endColor)
				return t;
		}
		return null;
	}

	/**
	 * Find color.
	 *
	 * @param s the s
	 * @return the chat color
	 */
	public ChatColor findColor(final String s) {
		char[] c = s.toCharArray();
		for (int i = 0; i < c.length; i++)
			if ((c[i] == new Character((char) 167)) && ((i + 1) < c.length))
				return ChatColor.getByChar(c[i + 1]);
		return null;
	}

	/**
	 * Remove tier.
	 *
	 * @param tier the tier
	 */
	public void removeTier(Tier tier) {
		if (tiers.contains(tier))
			tiers.remove(tier);
	}

	/**
	 * Save tiers.
	 */
	public void saveTiers() {
		FileConfiguration fc = getPlugin().getConfigurationManager()
				.getConfiguration(ConfigurationFile.TIER);
		for (Tier t : tiers) {
			fc.set(t.getName() + ".displayName", t.getDisplayName());
			List<String> allowedEnchs = new ArrayList<String>();
			for (Enchantment ench : t.getAllowedEnchantments()) {
				allowedEnchs.add(ench.getName());
			}
			fc.set(t.getName() + ".enchantment.allowedEnchants", allowedEnchs);
			for (Entry<Enchantment, Integer> e : t.getAutomaticEnchantments()
					.entrySet()) {
				fc.set(t + ".enchantment.automatic." + e.getKey().getName(),
						e.getValue());
			}
			for (Entry<Enchantment, Integer> e : t.getNaturalEnchantments()
					.entrySet()) {
				fc.set(t + ".enchantment.natural." + e.getKey().getName(),
						e.getValue());
			}
			fc.set(t.getName() + ".identifier", t.getIdentifier().name());
			fc.set(t.getName() + ".color", t.getColor().name());
			fc.set(t.getName() + ".chanceToBeGiven", t.getChanceToBeGiven());
			fc.set(t.getName() + ".chanceToDrop",
					Float.valueOf(t.getChanceToDrop()).doubleValue());
			fc.set(t.getName() + ".items.types", t.getItemTypes());
			fc.set(t.getName() + ".items.ids", t.getItemIDs());
		}
		getPlugin().getConfigurationManager().saveConfig();
	}

}
