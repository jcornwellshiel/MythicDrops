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
import com.conventnunnery.plugins.MythicDrops.events.CreatureEquippedWithItemStackEvent;
import com.conventnunnery.plugins.MythicDrops.objects.CustomItem;
import com.conventnunnery.plugins.MythicDrops.objects.Tier;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

/**
 * The type EntityAPI.
 */
public class EntityAPI {
	private final MythicDrops plugin;

	/**
	 * Instantiates a new EntityAPI.
	 *
	 * @param plugin the plugin
	 */
	public EntityAPI(MythicDrops plugin) {
		this.plugin = plugin;
	}

	public void equipEntity(LivingEntity entity, CustomItem customItem) {
		float f;
		if (customItem != null)
			f = (float) customItem.getChance();
		else
			return;
		ItemStack itemstack = customItem.toItemStack();
		CreatureEquippedWithItemStackEvent cewise = new CreatureEquippedWithItemStackEvent(entity, itemstack);
		Bukkit.getPluginManager().callEvent(cewise);
		if (cewise.isCancelled())
			return;
		itemstack = cewise.getItemStack();
		String itemType = getPlugin().getItemAPI().itemTypeFromMatData(itemstack.getData());
		if (itemType != null && itemType.equalsIgnoreCase("boots")) {
			entity.getEquipment().setBoots(itemstack);
			entity.getEquipment().setBootsDropChance(f);
		} else if (itemType != null && itemType
				.equalsIgnoreCase("leggings")) {
			entity.getEquipment().setLeggings(itemstack);
			entity.getEquipment().setLeggingsDropChance(f);
		} else if (itemType != null && itemType
				.equalsIgnoreCase("chestplate")) {
			entity.getEquipment().setChestplate(itemstack);
			entity.getEquipment().setChestplateDropChance(f);
		} else if (itemType != null && itemType
				.equalsIgnoreCase("helmet")) {
			entity.getEquipment().setHelmet(itemstack);
			entity.getEquipment().setHelmetDropChance(f);
		} else {
			entity.getEquipment().setItemInHand(itemstack);
			entity.getEquipment().setItemInHandDropChance(f);
		}
	}

	/**
	 * Equip entity.
	 *
	 * @param entity    the entity
	 * @param itemstack the itemstack
	 * @param tier      the tier
	 */
	public void equipEntity(LivingEntity entity, ItemStack itemstack, Tier tier) {
		float f = 1.0F;
		if (tier != null)
			f = tier.getChanceToDrop();
		CreatureEquippedWithItemStackEvent cewise = new CreatureEquippedWithItemStackEvent(entity, itemstack);
		Bukkit.getPluginManager().callEvent(cewise);
		if (cewise.isCancelled())
			return;
		itemstack = cewise.getItemStack();
		String itemType = getPlugin().getItemAPI().itemTypeFromMatData(itemstack.getData());
		if (itemType != null && itemType.equalsIgnoreCase("boots")) {
			entity.getEquipment().setBoots(itemstack);
			entity.getEquipment().setBootsDropChance(f);
		} else if (itemType != null && itemType
				.equalsIgnoreCase("leggings")) {
			entity.getEquipment().setLeggings(itemstack);
			entity.getEquipment().setLeggingsDropChance(f);
		} else if (itemType != null && itemType
				.equalsIgnoreCase("chestplate")) {
			entity.getEquipment().setChestplate(itemstack);
			entity.getEquipment().setChestplateDropChance(f);
		} else if (itemType != null && itemType
				.equalsIgnoreCase("helmet")) {
			entity.getEquipment().setHelmet(itemstack);
			entity.getEquipment().setHelmetDropChance(f);
		} else {
			entity.getEquipment().setItemInHand(itemstack);
			entity.getEquipment().setItemInHandDropChance(f);
		}
	}

	/**
	 * Gets plugin.
	 *
	 * @return the plugin
	 */
	public MythicDrops getPlugin() {
		return plugin;
	}
}
