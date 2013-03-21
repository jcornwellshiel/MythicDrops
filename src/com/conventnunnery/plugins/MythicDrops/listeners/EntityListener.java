/*
 * Copyright (c) 2013. ToppleTheNun
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.conventnunnery.plugins.MythicDrops.listeners;

import com.conventnunnery.plugins.MythicDrops.MythicDrops;
import com.conventnunnery.plugins.MythicDrops.api.DropAPI;
import com.conventnunnery.plugins.MythicDrops.objects.CustomItem;
import com.conventnunnery.plugins.MythicDrops.objects.Tier;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

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

	@EventHandler(priority = EventPriority.LOWEST)
	public void onEntityDeath(EntityDeathEvent event) {
		if (getPlugin().getPluginSettings().isWorldsEnabled()
				&& !getPlugin().getPluginSettings().getWorldsGenerate()
				.contains(event.getEntity().getWorld().getName())) {
			return;
		}
		for (ItemStack is : event.getEntity().getEquipment().getArmorContents()) {
			if (is == null || is.getType() == Material.AIR)
				continue;
			Tier t = getPlugin().getTierAPI().getTierFromItemStack(is);
			if (t == null) {
				continue;
			}
			is.setDurability((short) getPlugin().getRandom().nextInt((short) Math.abs(is.getType().getMaxDurability() - (is.getType().getMaxDurability() * Math.min(Math.max(1.0 - t.getDurability(), 0.0), 1.0))) + 1));
		}
		ItemStack is = event.getEntity().getEquipment().getItemInHand();
		if (is == null)
			return;
		Tier t = getPlugin().getTierAPI().getTierFromItemStack(is);
		if (t == null) {
			return;
		}
		is.setDurability((short) getPlugin().getRandom().nextInt((short) Math.abs(is.getType().getMaxDurability() - (is.getType().getMaxDurability() * Math.min(Math.max(1.0 - t.getDurability(), 0.0), 1.0))) + 1));
	}

	@EventHandler
	public void onEntitySpawn(CreatureSpawnEvent event) {
		if (getPlugin().getPluginSettings().isWorldsEnabled()
				&& !getPlugin().getPluginSettings().getWorldsGenerate()
				.contains(event.getEntity().getWorld().getName())) {
			return;
		}
		if (getPlugin().getPluginSettings().isAllowCustomToSpawn()
				&& (getPlugin().getRandom().nextDouble() < getPlugin()
				.getPluginSettings().getPercentageCustomDrop())) {
			if (!getPlugin().getDropAPI().getCustomItems().isEmpty()) {
				double globalChanceToSpawn = getPlugin().getPluginSettings()
						.getPercentageMobSpawnWithItemChance();
				double mobChanceToSpawn = 0.0;
				if (getPlugin().getPluginSettings()
						.getAdvancedMobSpawnWithItemChanceMap()
						.containsKey(event.getEntity().getType().name())) {
					mobChanceToSpawn = getPlugin().getPluginSettings()
							.getAdvancedMobSpawnWithItemChanceMap().get(event.getEntity().getType().name());
				}
				double chance = globalChanceToSpawn * mobChanceToSpawn;
				for (int i = 0; i < 5; i++) {
					if (getPlugin().getRandom().nextDouble() < chance) {
						if (getPlugin().getPluginSettings().isAllowCustomToSpawn()
								&& (getPlugin().getRandom().nextDouble() < getPlugin()
								.getPluginSettings().getPercentageCustomDrop())) {
							if (!getPlugin().getDropAPI().getCustomItems().isEmpty()) {
								getPlugin()
										.getEntityAPI()
										.equipEntity(
												event.getEntity(),
												getPlugin()
														.getDropAPI()
														.getCustomItems()
														.get(getPlugin().getRandom()
																.nextInt(getPlugin()
																		.getDropAPI()
																		.getCustomItems()
																		.size()))
														.toItemStack(), null);
								chance *= 0.5;
							}
							continue;
						}
						if (!getPlugin().getPluginSettings().isOnlyCustomItems() && !getPlugin()
								.getDropAPI()
								.getCustomItems().isEmpty()) {
							CustomItem customItem = getPlugin()
									.getDropAPI()
									.getCustomItems()
									.get(getPlugin().getRandom().nextInt(getPlugin()
											.getDropAPI().getCustomItems().size()));
							getPlugin().getEntityAPI().equipEntity(event.getEntity(),
									customItem);
							chance *= 0.5;
						}
						continue;
					}
					break;
				}
			}
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
			if (getPlugin().getRandom().nextDouble() < chance) {
				if (getPlugin().getPluginSettings().isAllowCustomToSpawn()
						&& (getPlugin().getRandom().nextDouble() < getPlugin()
						.getPluginSettings().getPercentageCustomDrop())) {
					if (!getPlugin().getDropAPI().getCustomItems().isEmpty()) {
						getPlugin()
								.getEntityAPI()
								.equipEntity(
										event.getEntity(),
										getPlugin()
												.getDropAPI()
												.getCustomItems()
												.get(getPlugin().getRandom()
														.nextInt(getPlugin()
																.getDropAPI()
																.getCustomItems()
																.size()))
												.toItemStack(), null);
						chance *= 0.5;
					}
				}
				if (!getPlugin().getPluginSettings().isOnlyCustomItems()) {
					Tier t = getPlugin().getTierAPI().randomTierWithChance();
					ItemStack itemstack = getPlugin().getDropAPI()
							.constructItemStack(t, DropAPI.GenerationReason.MOB_SPAWN);
					getPlugin().getEntityAPI().equipEntity(event.getEntity(),
							itemstack, t);
					chance *= 0.5;
				}
				continue;
			}
			break;
		}
	}
}
