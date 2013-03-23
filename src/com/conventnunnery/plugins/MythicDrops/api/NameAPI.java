package com.conventnunnery.plugins.MythicDrops.api;

import com.conventnunnery.plugins.MythicDrops.MythicDrops;
import com.conventnunnery.plugins.MythicDrops.configuration.ConfigurationManager.ConfigurationFile;
import com.conventnunnery.plugins.MythicDrops.objects.Tier;
import com.conventnunnery.plugins.MythicDrops.utilites.NameLoader;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The type NameAPI.
 */
public class NameAPI {
	private final MythicDrops plugin;
	private final List<String> basicPrefixes;
	private final List<String> basicSuffixes;
	private final NameLoader nameLoader;

	/**
	 * Instantiates a new NameAPI.
	 *
	 * @param plugin the plugin
	 */
	public NameAPI(MythicDrops plugin) {
		this.plugin = plugin;
		basicPrefixes = new ArrayList<String>();
		basicSuffixes = new ArrayList<String>();
		nameLoader = new NameLoader(this.plugin);
		loadPrefixes();
		loadSuffixes();
	}

	/**
	 * Gets basic prefixes.
	 *
	 * @return the basicPrefixes
	 */
	public List<String> getBasicPrefixes() {
		return basicPrefixes;
	}

	/**
	 * Gets basic suffixes.
	 *
	 * @return the basicSuffixes
	 */
	public List<String> getBasicSuffixes() {
		return basicSuffixes;
	}

	/**
	 * Gets item type name.
	 *
	 * @param matData the mat data
	 * @return the item type name
	 */
	public String getItemTypeName(MaterialData matData) {
		String itemType = getPlugin().getItemAPI().itemTypeFromMatData(matData);
		if (itemType == null)
			return null;
		String mythicMatName = getPlugin().getConfigurationManager()
				.getConfiguration(ConfigurationFile.LANGUAGE)
				.getString(itemType.toLowerCase());
		if (mythicMatName == null)
			mythicMatName = itemType;
		return getInitCappedString(mythicMatName.split(" "));
	}

	public String getInitCappedString(String... args) {
		String endResult = "";
		for (int i = 0; i < args.length; i++) {
			String s = args[i];
			if (i == args.length - 1) {
				endResult = endResult + s.substring(0, 1).toUpperCase() + s.substring(1, s.length()).toLowerCase();
			} else {
				endResult =
						endResult + s.substring(0, 1).toUpperCase() + s.substring(1, s.length()).toLowerCase() + " ";
			}
		}
		return endResult;
	}

	/**
	 * Gets Minecraft material name.
	 *
	 * @param material the material
	 * @return the minecraft material name
	 */
	public String getMinecraftMaterialName(Material material) {
		String prettyMaterialName = "";
		String matName = material.name();
		String[] split = matName.split("_");
		for (String s : split) {
			if (s.equals(split[split.length - 1])) {
				prettyMaterialName = prettyMaterialName
						+ (s.substring(0, 1).toUpperCase() + s.substring(1,
						s.length()).toLowerCase());
			} else {
				prettyMaterialName = prettyMaterialName
						+ (s.substring(0, 1).toUpperCase() + s.substring(1,
						s.length()).toLowerCase()) + " ";
			}
		}
		return getInitCappedString(prettyMaterialName.split(" "));
	}

	public String getEnchantmentTypeName(ItemStack itemStack) {
		Enchantment enchantment = null;
		Integer level = 0;
		for (Map.Entry<Enchantment, Integer> e : itemStack.getEnchantments().entrySet()) {
			if (e.getValue() > level) {
				enchantment = e.getKey();
				level = e.getValue();
			}
		}
		return enchantment == null ? "Ordinary" : getPlugin().getConfigurationManager()
				.getConfiguration(ConfigurationFile.LANGUAGE).getString(enchantment.getName());
	}

	/**
	 * Gets mythic material name.
	 *
	 * @param matData the mat data
	 * @return the mythic material name
	 */
	public String getMythicMaterialName(MaterialData matData) {
		String comb = String.valueOf(matData.getItemTypeId()) + ";"
				+ String.valueOf(matData.getData());
		String comb2;
		if (matData.getData() == (byte) 0) {
			comb2 = String.valueOf(matData.getItemTypeId());
		} else {
			comb2 = comb;
		}
		String mythicMatName = getPlugin().getConfigurationManager()
				.getConfiguration(ConfigurationFile.LANGUAGE).getString(comb);
		if (mythicMatName == null) {
			mythicMatName = getPlugin().getConfigurationManager()
					.getConfiguration(ConfigurationFile.LANGUAGE)
					.getString(comb2);
			if (mythicMatName == null)
				mythicMatName = getMinecraftMaterialName(matData.getItemType());
		}
		return getInitCappedString(mythicMatName.split(" "));
	}

	/**
	 * Gets name loader.
	 *
	 * @return the nameLoader
	 */
	public NameLoader getNameLoader() {
		return nameLoader;
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
	 * Load prefixes.
	 */
	public void loadPrefixes() {
		basicPrefixes.clear();
		nameLoader.writeDefault("resources/prefix.txt", false);
		nameLoader.loadFile(basicPrefixes, "resources/prefix.txt");
		plugin.getDebug().debug(
				"Loaded basic prefixes: " + basicPrefixes.size());
	}

	/**
	 * Load suffixes.
	 */
	public void loadSuffixes() {
		basicSuffixes.clear();
		nameLoader.writeDefault("resources/suffix.txt", false);
		nameLoader.loadFile(basicSuffixes, "resources/suffix.txt");
		plugin.getDebug().debug(
				"Loaded basic suffixes: " + basicSuffixes.size());
	}

	/**
	 * Random basic prefix.
	 *
	 * @return the string
	 */
	public String randomBasicPrefix() {
		return basicPrefixes.get(getPlugin().getRandom().nextInt(basicPrefixes
				.size()));
	}

	/**
	 * Random basic suffix.
	 *
	 * @return the string
	 */
	public String randomBasicSuffix() {
		return basicSuffixes.get(getPlugin().getRandom().nextInt(basicSuffixes
				.size()));
	}

	/**
	 * Random formatted name.
	 *
	 * @param itemStack the mat data
	 * @param tier      the tier
	 * @return the string
	 */
	public String randomFormattedName(ItemStack itemStack, Tier tier) {
		String format = getPlugin().getPluginSettings()
				.getDisplayItemNameFormat();
		String name = format
				.replace(
						"%basematerial%",
						tier.getColor()
								+ getMinecraftMaterialName(itemStack.getData()
								.getItemType()) + tier.getColor())
				.replace(
						"%mythicmaterial%",
						tier.getColor() + getMythicMaterialName(itemStack.getData())
								+ tier.getColor())
				.replace("%basicprefix%",
						tier.getColor() + randomBasicPrefix() + tier.getColor())
				.replace("%basicsuffix%",
						tier.getColor() + randomBasicSuffix() + tier.getColor())
				.replace(
						"%itemtype%",
						tier.getColor() + getItemTypeName(itemStack.getData())
								+ tier.getColor())
				.replace(
						"%tiername%",
						tier.getColor() + tier.getDisplayName()
								+ tier.getColor()).replace('&', '\u00A7')
				.replace("%enchantment%", tier.getColor() + getEnchantmentTypeName(itemStack) + tier.getColor())
				.replace("\u00A7\u00A7", "&");
		return tier.getColor() + name + tier.getIdentifier();
	}
}
