package com.conventnunnery.plugins.MythicDrops.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.inventory.ItemStack;

import com.conventnunnery.plugins.MythicDrops.MythicDrops;
import com.conventnunnery.plugins.MythicDrops.objects.Tier;

public class EntityListener implements Listener {
	private final MythicDrops plugin;

	public EntityListener(MythicDrops plugin) {
		this.plugin = plugin;
	}

	/**
	 * @return the plugin
	 */
	public MythicDrops getPlugin() {
		return plugin;
	}

	@EventHandler
	public void onEntitySpawn(CreatureSpawnEvent event) {
		if (getPlugin().getPluginSettings().isWorldsEnabled()
				&& !getPlugin().getPluginSettings().getWorldsGenerate()
						.contains(event.getEntity().getWorld().getName())) {
			return;
		}
		if (event.getSpawnReason() == SpawnReason.SPAWNER
				&& getPlugin().getPluginSettings().isPreventSpawner())
			return;
		if (event.getSpawnReason() == SpawnReason.SPAWNER_EGG
				&& getPlugin().getPluginSettings().isPreventSpawnEgg())
			return;
		EntityType entType = event.getEntityType();
		double globalChanceToSpawn = getPlugin().getPluginSettings()
				.getPercentageMobSpawnWithItemChance();
		double mobChanceToSpawn = 0.0;
		if (getPlugin().getPluginSettings()
				.getAdvancedMobSpawnWithItemChanceMap()
				.containsKey(entType.name())) {
			mobChanceToSpawn = getPlugin().getPluginSettings()
					.getAdvancedMobSpawnWithItemChanceMap().get(entType.name());
		}
		double chance = globalChanceToSpawn * mobChanceToSpawn;
		for (int i = 0; i < 5; i++) {
			if (getPlugin().random.nextDouble() < chance) {
				Tier t = getPlugin().getTierAPI().randomTierWithChance();
				ItemStack itemstack = getPlugin().getDropAPI()
						.constructItemStack(t);
				getPlugin().getEntityAPI().equipEntity(event.getEntity(),
						itemstack, t);
				chance *= 0.5;
				continue;
			}
			break;
		}
	}
}
