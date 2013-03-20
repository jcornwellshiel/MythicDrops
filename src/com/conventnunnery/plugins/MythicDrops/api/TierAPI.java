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

public class TierAPI {

	private final List<Tier> tiers;
	private final MythicDrops plugin;

	public TierAPI(MythicDrops plugin) {
		this.plugin = plugin;
		tiers = new ArrayList<Tier>();
	}

	public void addTier(Tier tier) {
		tiers.add(tier);
	}

	public void debugTiers() {
		getPlugin().getDebug().debug("Amount of loaded tiers: " + tiers.size());
		List<String> tierNames = new ArrayList<String>();
		for (Tier t : tiers) {
			tierNames.add(t.getName() + " (" + t.getChanceToBeGiven() + ")");
		}
		getPlugin().getDebug().debug(
				"Loaded tier names: "
						+ tierNames.toString().replace("[", "")
						.replace("]", ""));
	}

	/**
	 * @return the plugin
	 */
	public MythicDrops getPlugin() {
		return plugin;
	}

	public Tier getTierFromName(String name) {
		for (Tier t : tiers) {
			if (t.getName().equalsIgnoreCase(name)) {
				return t;
			}
		}
		return null;
	}

	/**
	 * @return the tiers
	 */
	public List<Tier> getTiers() {
		return tiers;
	}

	public Tier randomTier() {
		return tiers.get(getPlugin().getRandom().nextInt(tiers.size()));
	}

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

	public Tier getTierFromItemStack(ItemStack itemStack) {
		Tier tier = null;
		ItemMeta im;
		if (itemStack.hasItemMeta())
			im = itemStack.getItemMeta();
		else
			return null;
		String name = null;
		if (im.hasDisplayName())
			name = im.getDisplayName();
		else
			return null;
		ChatColor initColor = findColor(name);
		ChatColor endColor = ChatColor.valueOf(ChatColor.getLastColors(name));
		for (Tier t : tiers) {
			if (t.getColor() == initColor && t.getIdentifier() == endColor)
				return t;
		}
		return null;
	}

	public ChatColor findColor(final String s) {
		char[] c = s.toCharArray();
		for (int i = 0; i < c.length; i++)
			if ((c[i] == new Character((char) 167)) && ((i + 1) < c.length))
				return ChatColor.getByChar(c[i + 1]);
		return null;
	}

	public void removeTier(Tier tier) {
		if (tiers.contains(tier))
			tiers.remove(tier);
	}

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
		}
		getPlugin().getConfigurationManager().saveConfig();
	}

}
