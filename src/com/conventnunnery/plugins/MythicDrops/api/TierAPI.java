package com.conventnunnery.plugins.MythicDrops.api;

import java.util.ArrayList;
import java.util.List;

import com.conventnunnery.plugins.MythicDrops.MythicDrops;
import com.conventnunnery.plugins.MythicDrops.objects.Tier;

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

	/**
	 * @return the tiers
	 */
	public List<Tier> getTiers() {
		return tiers;
	}

	public Tier randomTier() {
		return tiers.get(getPlugin().random.nextInt(tiers.size()));
	}

	public Tier randomTierWithChance() {
		Tier tier = null;
		if (tiers.isEmpty() || tiers == null)
			return tier;
		while (tier == null) {
			for (Tier t : tiers) {
				if (plugin.random.nextDouble() <= t.getChanceToBeGiven())
					tier = t;
			}
		}
		return tier;
	}

	public void removeTier(Tier tier) {
		if (tiers.contains(tier))
			tiers.remove(tier);
	}

}
