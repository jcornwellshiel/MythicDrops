package com.conventnunnery.plugins.MythicDrops;

import com.conventnunnery.plugins.MythicDrops.configuration.ConfigurationManager.ConfigurationFile;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PluginSettings {

	private final MythicDrops plugin;
	private String displayItemNameFormat;
	private double percentageMobSpawnWithItemChance;
	private double percentageCustomDrop;
	private boolean worldsEnabled;
	private boolean automaticUpdate;
	private boolean safeEnchantsOnly;
	private boolean preventSpawnEgg;
	private boolean preventSpawner;
	private boolean allowCustomToSpawn;
	private boolean onlyCustomItems;
	private List<String> worldsGenerate = new ArrayList<String>();
	private List<String> worldsUse = new ArrayList<String>();
	private Map<String, List<String>> ids = new HashMap<String, List<String>>();
	private Map<String, Double> advanced_mobSpawnWithItemChance = new HashMap<String, Double>();
	private List<String> advanced_toolTipFormat = new ArrayList<String>();

	public PluginSettings(MythicDrops plugin) {
		this.plugin = plugin;
	}

	public void debugSettings() {
		getPlugin().getDebug().debug("Auto Update: " + isAutomaticUpdate(),
				"Safe Enchants Only: " + isSafeEnchantsOnly(),
				"Multiworld Support Enabled: " + isWorldsEnabled(), "Item Name Format: " + getDisplayItemNameFormat(),
				"Item Types: " + getIDs().keySet().toString().replace("[", "").replace("]", ""));
		if (isWorldsEnabled()) {
			getPlugin().getDebug().debug(
					"Generate Worlds: " + getWorldsGenerate(),
					"Use Worlds: " + getWorldsUse());
		}
		getPlugin().getDebug().debug(
				"Global Spawn Rate: " + getPercentageMobSpawnWithItemChance());
	}

	public Map<String, Double> getAdvancedMobSpawnWithItemChanceMap() {
		return advanced_mobSpawnWithItemChance;
	}

	public void setAdvancedMobSpawnWithItemChanceMap(
			Map<String, Double> advanced_mobSpawnWithItemChance) {
		this.advanced_mobSpawnWithItemChance = advanced_mobSpawnWithItemChance;
	}

	public List<String> getAdvancedToolTipFormat() {
		return advanced_toolTipFormat;
	}

	public void setAdvancedToolTipFormat(List<String> advanced_toolTipFormat) {
		this.advanced_toolTipFormat = advanced_toolTipFormat;
	}

	public String getDisplayItemNameFormat() {
		return displayItemNameFormat;
	}

	public void setDisplayItemNameFormat(String displayItemNameFormat) {
		this.displayItemNameFormat = displayItemNameFormat;
	}

	public Map<String, List<String>> getIDs() {
		return ids;
	}

	public void setIDs(HashMap<String, List<String>> ids) {
		this.ids = ids;
	}

	/**
	 * @return the percentageCustomDrop
	 */
	public double getPercentageCustomDrop() {
		return percentageCustomDrop;
	}

	/**
	 * @param percentageCustomDrop the percentageCustomDrop to set
	 */
	public void setPercentageCustomDrop(double percentageCustomDrop) {
		this.percentageCustomDrop = percentageCustomDrop;
	}

	public double getPercentageMobSpawnWithItemChance() {
		return percentageMobSpawnWithItemChance;
	}

	public void setPercentageMobSpawnWithItemChance(
			double percentageMobSpawnWithItemChance) {
		this.percentageMobSpawnWithItemChance = percentageMobSpawnWithItemChance;
	}

	public MythicDrops getPlugin() {
		return plugin;
	}

	public List<String> getWorldsGenerate() {
		return worldsGenerate;
	}

	public void setWorldsGenerate(List<String> worldsGenerate) {
		this.worldsGenerate = worldsGenerate;
	}

	public List<String> getWorldsUse() {
		return worldsUse;
	}

	public void setWorldsUse(List<String> worldsUse) {
		this.worldsUse = worldsUse;
	}

	/**
	 * @return the allowCustomToSpawn
	 */
	public boolean isAllowCustomToSpawn() {
		return allowCustomToSpawn;
	}

	/**
	 * @param allowCustomToSpawn the allowCustomToSpawn to set
	 */
	public void setAllowCustomToSpawn(boolean allowCustomToSpawn) {
		this.allowCustomToSpawn = allowCustomToSpawn;
	}

	/**
	 * @return the automaticUpdate
	 */
	public boolean isAutomaticUpdate() {
		return automaticUpdate;
	}

	/**
	 * @param automaticUpdate the automaticUpdate to set
	 */
	public void setAutomaticUpdate(boolean automaticUpdate) {
		this.automaticUpdate = automaticUpdate;
	}

	/**
	 * @return the onlyCustomItems
	 */
	public boolean isOnlyCustomItems() {
		return onlyCustomItems;
	}

	/**
	 * @param onlyCustomItems the onlyCustomItems to set
	 */
	public void setOnlyCustomItems(boolean onlyCustomItems) {
		this.onlyCustomItems = onlyCustomItems;
	}

	/**
	 * @return the preventSpawnEgg
	 */
	public boolean isPreventSpawnEgg() {
		return preventSpawnEgg;
	}

	/**
	 * @param preventSpawnEgg the preventSpawnEgg to set
	 */
	public void setPreventSpawnEgg(boolean preventSpawnEgg) {
		this.preventSpawnEgg = preventSpawnEgg;
	}

	/**
	 * @return the preventSpawner
	 */
	public boolean isPreventSpawner() {
		return preventSpawner;
	}

	/**
	 * @param preventSpawner the preventSpawner to set
	 */
	public void setPreventSpawner(boolean preventSpawner) {
		this.preventSpawner = preventSpawner;
	}

	public boolean isSafeEnchantsOnly() {
		return safeEnchantsOnly;
	}

	public void setSafeEnchantsOnly(boolean safeEnchantsOnly) {
		this.safeEnchantsOnly = safeEnchantsOnly;
	}

	public boolean isWorldsEnabled() {
		return worldsEnabled;
	}

	public void setWorldsEnabled(boolean worldsEnabled) {
		this.worldsEnabled = worldsEnabled;
	}

	private void loadIDs() {
		FileConfiguration fc = getPlugin().getConfigurationManager()
				.getConfiguration(ConfigurationFile.ADVANCED_CONFIG);
		if (!fc.isConfigurationSection("ids"))
			return;
		ConfigurationSection idCS = fc.getConfigurationSection("ids");
		if (idCS.isConfigurationSection("toolIDs")) {
			ConfigurationSection toolCS = idCS.getConfigurationSection("toolIDs");
			for (String toolKind : toolCS.getKeys(false)) {
				List<String> idList;
				idList = toolCS.getStringList(toolKind);
				if (idList == null)
					idList = new ArrayList<String>();
				ids.put(toolKind.toLowerCase(), idList);
			}
		}
		if (idCS.isConfigurationSection("armorIDs")) {
			ConfigurationSection toolCS = idCS.getConfigurationSection("armorIDs");
			for (String toolKind : toolCS.getKeys(false)) {
				List<String> idList;
				idList = toolCS.getStringList(toolKind);
				if (idList == null)
					idList = new ArrayList<String>();
				ids.put(toolKind.toLowerCase(), idList);
			}
		}
	}

	public void loadPluginSettings() {
		setAutomaticUpdate(getPlugin().getConfigurationManager()
				.getConfiguration(ConfigurationFile.CONFIG)
				.getBoolean("options.autoUpdate"));
		setPercentageCustomDrop(getPlugin().getConfigurationManager()
				.getConfiguration(ConfigurationFile.CONFIG)
				.getDouble("options.customDropChance"));
		setSafeEnchantsOnly(getPlugin().getConfigurationManager()
				.getConfiguration(ConfigurationFile.CONFIG)
				.getBoolean("options.safeEnchantsOnly"));
		setOnlyCustomItems(getPlugin().getConfigurationManager()
				.getConfiguration(ConfigurationFile.CONFIG)
				.getBoolean("options.customItemsOnly"));
		setAllowCustomToSpawn(getPlugin().getConfigurationManager()
				.getConfiguration(ConfigurationFile.CONFIG)
				.getBoolean("options.customItemsSpawn"));
		setDisplayItemNameFormat(getPlugin().getConfigurationManager()
				.getConfiguration(ConfigurationFile.CONFIG)
				.getString("display.itemNameFormat"));
		setPreventSpawnEgg(getPlugin().getConfigurationManager()
				.getConfiguration(ConfigurationFile.CONFIG)
				.getBoolean("spawnPrevention.spawnEgg"));
		setPreventSpawner(getPlugin().getConfigurationManager()
				.getConfiguration(ConfigurationFile.CONFIG)
				.getBoolean("spawnPrevention.spawner"));
		setPercentageMobSpawnWithItemChance(getPlugin()
				.getConfigurationManager()
				.getConfiguration(ConfigurationFile.CONFIG)
				.getDouble("percentages.mobSpawnWithItemChance"));
		setWorldsEnabled(getPlugin().getConfigurationManager()
				.getConfiguration(ConfigurationFile.CONFIG)
				.getBoolean("worlds.enabled"));
		setWorldsGenerate(getPlugin().getConfigurationManager()
				.getConfiguration(ConfigurationFile.CONFIG)
				.getStringList("worlds.generate"));
		if (getWorldsGenerate() == null)
			setWorldsGenerate(new ArrayList<String>());
		setWorldsUse(getPlugin().getConfigurationManager()
				.getConfiguration(ConfigurationFile.CONFIG)
				.getStringList("worlds.use"));
		if (getWorldsUse() == null)
			setWorldsUse(new ArrayList<String>());
		loadIDs();
		Map<String, Double> map = new HashMap<String, Double>();
		for (String creature : getPlugin().getConfigurationManager()
				.getConfiguration(ConfigurationFile.ADVANCED_CONFIG)
				.getConfigurationSection("mobs.spawnWithItemChance")
				.getKeys(false)) {
			map.put(creature.toUpperCase(),
					getPlugin()
							.getConfigurationManager()
							.getConfiguration(ConfigurationFile.ADVANCED_CONFIG)
							.getConfigurationSection("mobs.spawnWithItemChance")
							.getDouble(creature));
			setAdvancedMobSpawnWithItemChanceMap(map);
		}
		List<String> toolTipFormat = getPlugin().getConfigurationManager()
				.getConfiguration(ConfigurationFile.ADVANCED_CONFIG)
				.getStringList("tooltips.format.lines");
		if (toolTipFormat == null) {
			toolTipFormat = new ArrayList<String>();
			getPlugin().getConfigurationManager()
					.getConfiguration(ConfigurationFile.ADVANCED_CONFIG)
					.set("tooltips.format.lines", toolTipFormat);
		}
		setAdvancedToolTipFormat(toolTipFormat);
		getPlugin().getConfigurationManager().saveConfig();
	}
}
