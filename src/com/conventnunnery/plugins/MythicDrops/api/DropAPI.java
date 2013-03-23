package com.conventnunnery.plugins.MythicDrops.api;

import com.conventnunnery.plugins.MythicDrops.MythicDrops;
import com.conventnunnery.plugins.MythicDrops.configuration.ConfigurationManager.ConfigurationFile;
import com.conventnunnery.plugins.MythicDrops.objects.CustomItem;
import com.conventnunnery.plugins.MythicDrops.objects.Tier;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

/**
 * A class that handles all of the plugin's drop creation.
 */
public class DropAPI {
	private final MythicDrops plugin;
	private List<CustomItem> customItems;

	/**
	 * Instantiates a new Drop API.
	 *
	 * @param plugin the plugin
	 */
	public DropAPI(MythicDrops plugin) {
		this.plugin = plugin;
		customItems = new ArrayList<CustomItem>();
	}

	/**
	 * Construct a random ItemStack.
	 *
	 * @param reason the reason
	 * @return random ItemStack
	 */
	public ItemStack constructItemStack(GenerationReason reason) {
		switch (reason) {
			case MOB_SPAWN:
				if (getPlugin().getPluginSettings().isAllowCustomToSpawn()
						&& (getPlugin().getRandom().nextDouble() < getPlugin()
						.getPluginSettings().getPercentageCustomDrop())) {
					if (!customItems.isEmpty()) {
						return randomCustomItemWithChance().toItemStack();
					}
				}
				if (!getPlugin().getPluginSettings().isOnlyCustomItems()) {
					return constructItemStack(getPlugin().getTierAPI()
							.randomTierWithChance(), reason);
				}
				return null;
			case COMMAND:
				return constructItemStack(getPlugin().getTierAPI()
						.randomTierWithChance(), reason);
			case EXTERNAL:
				return constructItemStack(getPlugin().getTierAPI()
						.randomTierWithChance(), reason);
			default:
				return constructItemStack(getPlugin().getTierAPI()
						.randomTierWithChance(), reason);
		}
	}

	/**
	 * Construct an ItemStack based on a Tier.
	 *
	 * @param tier   Tier to base the ItemStack on
	 * @param reason reason to generate the ItemStack
	 * @return constructed ItemStack
	 */
	public ItemStack constructItemStack(Tier tier, GenerationReason reason) {
		ItemStack itemstack = null;
		MaterialData matData = null;
		int attempts = 0;
		while (matData == null && attempts < 10) {
			matData = getPlugin().getItemAPI().getMatDataFromTier(tier);
		}
		if (matData == null || matData.getItemTypeId() == 0
				|| matData.getItemType() == Material.AIR)
			return itemstack;
		itemstack = matData.toItemStack(1);
		if (reason != null && reason != GenerationReason.COMMAND) {
			short ranSize = (short) (itemstack.getType().getMaxDurability() *
					Math.min(Math.max(1.0 - tier.getDurability(), 0.0), 1.0));
			short dura = (short) getPlugin().getRandom()
					.nextInt(Math.abs(itemstack.getType().getMaxDurability() - ranSize) + 1);
			itemstack.setDurability(dura);
		}
		ItemMeta im;
		if (itemstack.hasItemMeta())
			im = itemstack.getItemMeta();
		else
			im = Bukkit.getItemFactory().getItemMeta(matData.getItemType());
		im.setDisplayName(getPlugin().getNameAPI().randomFormattedName(
				itemstack, tier));
		List<String> toolTips = getPlugin().getPluginSettings()
				.getAdvancedToolTipFormat();
		List<String> tt = new ArrayList<String>();
		for (String s : toolTips) {
			tt.add(ChatColor.translateAlternateColorCodes(
					'&',
					s.replace("%itemtype%",
							getPlugin().getNameAPI().getItemTypeName(matData))
							.replace("%tiername%",
									tier.getColor() + tier.getDisplayName())
							.replace(
									"%basematerial%",
									getPlugin().getNameAPI()
											.getMinecraftMaterialName(
													itemstack.getType()))
							.replace(
									"%mythicmaterial%",
									getPlugin().getNameAPI()
											.getMythicMaterialName(
													itemstack.getData())).replace("%enchantment%",
							tier.getColor() + getPlugin().getNameAPI().getEnchantmentTypeName(itemstack) +
									tier.getColor())));
		}
		im.setLore(tt);
		if (im instanceof Repairable) {
			Repairable r = (Repairable) im;
			r.setRepairCost(1000);
			itemstack.setItemMeta((ItemMeta) r);
		} else {
			itemstack.setItemMeta(im);
		}
		for (Entry<Enchantment, Integer> e : tier.getAutomaticEnchantments()
				.entrySet()) {
			if (e.getKey() != null)
				itemstack.addUnsafeEnchantment(e.getKey(),
						(e.getValue() == 0) ? 1 : Math.abs(e.getValue()));
		}
		for (Entry<Enchantment, Integer> e : tier.getNaturalEnchantments()
				.entrySet()) {
			if (e.getKey() != null)
				if (e.getKey().canEnchantItem(itemstack))
					itemstack.addUnsafeEnchantment(e.getKey(),
							(e.getValue() == 0) ? 1 : Math.abs(e.getValue()));
		}
		if (tier.getMaxNumberOfRandomEnchantments() > 0) {
			int randEnchs = getPlugin().getRandom().nextInt(
					Math.abs(tier.getMaxNumberOfRandomEnchantments() - tier.getMinNumberOfRandomEnchantments() + 1)) +
					tier.getMinNumberOfRandomEnchantments();
			for (int i = 0; i < randEnchs; i++) {
				int lev = Math.abs(getPlugin().getRandom().nextInt(tier
						.getMaxLevelOfRandomEnchantments() + 1)) + 1;
				List<Enchantment> allowEnchs = tier.getAllowedEnchantments();
				List<Enchantment> stackEnchs = getEnchantStack(itemstack);
				List<Enchantment> actual = new ArrayList<Enchantment>();
				for (Enchantment e : Enchantment.values()) {
					if (allowEnchs.contains(e) && stackEnchs.contains(e)) {
						actual.add(e);
					}
				}
				if (actual.size() > 0) {
					Enchantment ench = actual.get(getPlugin().getRandom()
							.nextInt(actual.size()));
					if (getPlugin().getPluginSettings().isSafeEnchantsOnly()) {
						if (!getPlugin().getPluginSettings().isAllowEnchantsPastNormalLevel()) {
							itemstack.addEnchantment(
									ench,
									getAcceptableEnchantmentLevel(ench,
											(lev == 0) ? 1 : Math.abs(lev)));
						} else {
							itemstack.addUnsafeEnchantment(ench, (lev == 0) ? 1 : Math.abs(lev));
						}
					} else
						itemstack.addUnsafeEnchantment(ench, (lev == 0) ? 1
								: Math.abs(lev));
				}
			}
		}
		return itemstack;
	}

	/**
	 * Gets acceptable Enchantment level.
	 *
	 * @param ench  the Enchantment
	 * @param level the level
	 * @return the acceptable Enchantment level
	 */
	public int getAcceptableEnchantmentLevel(Enchantment ench, int level) {
		EnchantmentWrapper ew = new EnchantmentWrapper(ench.getId());
		int i = level;
		if (i > ew.getMaxLevel()) {
			i = ew.getMaxLevel();
		} else if (i < ew.getStartLevel()) {
			i = ew.getStartLevel();
		}
		return i;
	}

	/**
	 * Gets custom items.
	 *
	 * @return the custom items
	 */
	public List<CustomItem> getCustomItems() {
		return customItems;
	}

	/**
	 * Gets a list of Enchantments that can go on an ItemStack.
	 *
	 * @param ci ItemStack to check
	 * @return list of possible Enchantments
	 */
	public List<Enchantment> getEnchantStack(final ItemStack ci) {
		List<Enchantment> set = new ArrayList<Enchantment>();
		boolean bln = getPlugin().getPluginSettings().isSafeEnchantsOnly();
		for (Enchantment e : Enchantment.values()) {
			if (bln) {
				if (e.canEnchantItem(ci)) {
					set.add(e);
				}
			} else {
				set.add(e);
			}
		}
		return set;
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
	 * Random custom item.
	 *
	 * @return the custom item
	 */
	public CustomItem randomCustomItem() {
		return customItems.get(getPlugin().getRandom().nextInt(customItems.size()));
	}

	/**
	 * Random custom item with chance.
	 *
	 * @return the custom item
	 */
	public CustomItem randomCustomItemWithChance() {
		CustomItem ci = null;
		if (customItems == null || customItems.isEmpty())
			return ci;
		while (ci == null) {
			for (CustomItem c : customItems) {
				double d = plugin.getRandom().nextDouble();
				if (d <= c.getChance()) {
					ci = c;
					break;
				}
			}
		}
		return ci;
	}

	/**
	 * Save custom items.
	 */
	public void saveCustomItems() {
		FileConfiguration fc = getPlugin().getConfigurationManager()
				.getConfiguration(ConfigurationFile.TIER);
		for (CustomItem c : customItems) {
			fc.set(c.getName() + ".displayName", c.getDisplayName());
			fc.set(c.getName() + ".lore", c.getLore());
			fc.set(c.getName() + ".materialId", c.getMatData().getItemTypeId());
			fc.set(c.getName() + ".materialData", (int) c.getMatData()
					.getData());
			for (Entry<Enchantment, Integer> e : c.getEnchantments().entrySet()) {
				fc.set(c.getName() + ".enchantments." + e.getKey().getName(),
						e.getValue());
			}
		}
		getPlugin().getConfigurationManager().saveConfig();
	}

	/**
	 * Enum of GenerationReasons.
	 */
	public enum GenerationReason {
		/**
		 * Use when spawning a mob
		 */MOB_SPAWN, /**
		 * Use for commands
		 */COMMAND, /**
		 * Use for anything else
		 */EXTERNAL
	}
}
