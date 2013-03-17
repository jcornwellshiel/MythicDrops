package com.conventnunnery.plugins.MythicDrops.api;

import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import com.conventnunnery.plugins.MythicDrops.MythicDrops;
import com.conventnunnery.plugins.MythicDrops.objects.Tier;

public class EntityAPI {
	private final MythicDrops plugin;

	public EntityAPI(MythicDrops plugin) {
		this.plugin = plugin;
	}

	public void equipEntity(LivingEntity entity, ItemStack itemstack, Tier tier) {
		float f = 1.0F;
		if (tier != null)
			f = tier.getChanceToDrop();
		if (getPlugin().getItemAPI().itemTypeFromMatData(itemstack.getData())
				.equalsIgnoreCase("boots")) {
			entity.getEquipment().setBoots(itemstack);
			entity.getEquipment().setBootsDropChance(f);
		}
		else if (getPlugin().getItemAPI()
				.itemTypeFromMatData(itemstack.getData())
				.equalsIgnoreCase("leggings")) {
			entity.getEquipment().setLeggings(itemstack);
			entity.getEquipment().setLeggingsDropChance(f);
		}
		else if (getPlugin().getItemAPI()
				.itemTypeFromMatData(itemstack.getData())
				.equalsIgnoreCase("chestplate")) {
			entity.getEquipment().setChestplate(itemstack);
			entity.getEquipment().setChestplateDropChance(f);
		}
		else if (getPlugin().getItemAPI()
				.itemTypeFromMatData(itemstack.getData())
				.equalsIgnoreCase("helmet")) {
			entity.getEquipment().setHelmet(itemstack);
			entity.getEquipment().setHelmetDropChance(f);
		}
		else {
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
