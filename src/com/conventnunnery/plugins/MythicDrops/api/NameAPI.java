package com.conventnunnery.plugins.MythicDrops.api;

import com.conventnunnery.plugins.MythicDrops.MythicDrops;
import com.conventnunnery.plugins.MythicDrops.configuration.ConfigurationManager.ConfigurationFile;
import com.conventnunnery.plugins.MythicDrops.objects.Tier;
import com.conventnunnery.plugins.MythicDrops.utilites.NameLoader;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.List;

public class NameAPI {
	private final MythicDrops plugin;
	private final List<String> basicPrefixes;
	private final List<String> basicSuffixes;
	private final NameLoader nameLoader;

	public NameAPI(MythicDrops plugin) {
		this.plugin = plugin;
		basicPrefixes = new ArrayList<String>();
		basicSuffixes = new ArrayList<String>();
		nameLoader = new NameLoader(this.plugin);
		loadPrefixes();
		loadSuffixes();
	}

	/**
	 * @return the basicPrefixes
	 */
	public List<String> getBasicPrefixes() {
		return basicPrefixes;
	}

	/**
	 * @return the basicSuffixes
	 */
	public List<String> getBasicSuffixes() {
		return basicSuffixes;
	}

	public String getItemTypeName(MaterialData matData) {
		String itemType = getPlugin().getItemAPI().itemTypeFromMatData(matData);
		if (itemType == null)
			return null;
		String mythicMatName = getPlugin().getConfigurationManager()
				.getConfiguration(ConfigurationFile.LANGUAGE)
				.getString(itemType.toLowerCase());
		if (mythicMatName == null)
			mythicMatName = itemType;
		return mythicMatName.substring(0, 1).toUpperCase()
				+ itemType.substring(1, itemType.length()).toLowerCase();
	}

	public String getMinecraftMaterialName(Material material) {
		String prettyMaterialName = "";
		String matName = material.name();
		String[] split = matName.split("_");
		for (String s : split) {
			if (s.equals(split[split.length - 1])) {
				prettyMaterialName = prettyMaterialName
						+ (s.substring(0, 1).toUpperCase() + s.substring(1,
						s.length()).toLowerCase()) + " ";
			} else {
				prettyMaterialName = prettyMaterialName
						+ (s.substring(0, 1).toUpperCase() + s.substring(1,
						s.length()).toLowerCase());
			}
		}
		return prettyMaterialName;
	}

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
		return mythicMatName;
	}

	/**
	 * @return the nameLoader
	 */
	public NameLoader getNameLoader() {
		return nameLoader;
	}

	/**
	 * @return the plugin
	 */
	public MythicDrops getPlugin() {
		return plugin;
	}

	public void loadPrefixes() {
		basicPrefixes.clear();
		nameLoader.writeDefault("resources/prefix.txt", false);
		nameLoader.loadFile(basicPrefixes, "resources/prefix.txt");
		plugin.getDebug().debug(
				"Loaded basic prefixes: " + basicPrefixes.size());
	}

	public void loadSuffixes() {
		basicSuffixes.clear();
		nameLoader.writeDefault("resources/suffix.txt", false);
		nameLoader.loadFile(basicSuffixes, "resources/suffix.txt");
		plugin.getDebug().debug(
				"Loaded basic suffixes: " + basicSuffixes.size());
	}

	public String randomBasicPrefix() {
		return basicPrefixes.get(getPlugin().getRandom().nextInt(basicPrefixes
				.size()));
	}

	public String randomBasicSuffix() {
		return basicSuffixes.get(getPlugin().getRandom().nextInt(basicSuffixes
				.size()));
	}

	public String randomFormattedName(MaterialData matData, Tier tier) {
		String format = getPlugin().getPluginSettings()
				.getDisplayItemNameFormat();
		String name = format
				.replace(
						"%basematerial%",
						tier.getColor()
								+ getMinecraftMaterialName(matData
								.getItemType()) + tier.getColor())
				.replace(
						"%mythicmaterial%",
						tier.getColor() + getMythicMaterialName(matData)
								+ tier.getColor())
				.replace("%basicprefix%",
						tier.getColor() + randomBasicPrefix() + tier.getColor())
				.replace("%basicsuffix%",
						tier.getColor() + randomBasicSuffix() + tier.getColor())
				.replace(
						"%itemtype%",
						tier.getColor() + getItemTypeName(matData)
								+ tier.getColor())
				.replace(
						"%tiername%",
						tier.getColor() + tier.getDisplayName()
								+ tier.getColor()).replace('&', '\u00A7')
				.replace("\u00A7\u00A7", "&");
		return tier.getColor() + name + tier.getIdentifier();
	}
}
