package com.conventnunnery.plugins.MythicDrops.api;

import com.conventnunnery.plugins.MythicDrops.MythicDrops;
import com.conventnunnery.plugins.MythicDrops.objects.Tier;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public class EntityAPI {
	private final MythicDrops plugin;

	public EntityAPI(MythicDrops plugin) {
		this.plugin = plugin;
	}

	public void equipEntity(LivingEntity entity, ItemStack itemstack, Tier tier) {
		float f = 1.0F;
		if (tier != null)
			f = tier.getChanceToDrop();
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
	 * @return the plugin
	 */
	public MythicDrops getPlugin() {
		return plugin;
	}
}
