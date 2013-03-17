package com.conventnunnery.plugins.MythicDrops;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;

import com.conventnunnery.plugins.MythicDrops.configuration.ConfigurationManager.ConfigurationFile;

public class PluginSettings {

	private final MythicDrops plugin;

	private String displayItemNameFormat;
	private double percentageMobSpawnWithItemChance;
	private boolean worldsEnabled;
	private boolean automaticUpdate;
	private boolean safeEnchantsOnly;
	private boolean preventSpawnEgg;
	private boolean preventSpawner;
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
				"Multiworld Support Enabled: " + isWorldsEnabled());
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

	public List<String> getAdvancedToolTipFormat() {
		return advanced_toolTipFormat;
	}

	public String getDisplayItemNameFormat() {
		return displayItemNameFormat;
	}

	public Map<String, List<String>> getIDs() {
		return ids;
	}

	public double getPercentageMobSpawnWithItemChance() {
		return percentageMobSpawnWithItemChance;
	}

	public MythicDrops getPlugin() {
		return plugin;
	}

	public List<String> getWorldsGenerate() {
		return worldsGenerate;
	}

	public List<String> getWorldsUse() {
		return worldsUse;
	}

	/**
	 * @return the automaticUpdate
	 */
	public boolean isAutomaticUpdate() {
		return automaticUpdate;
	}

	/**
	 * @return the preventSpawnEgg
	 */
	public boolean isPreventSpawnEgg() {
		return preventSpawnEgg;
	}

	/**
	 * @return the preventSpawner
	 */
	public boolean isPreventSpawner() {
		return preventSpawner;
	}

	public boolean isSafeEnchantsOnly() {
		return safeEnchantsOnly;
	}

	public boolean isWorldsEnabled() {
		return worldsEnabled;
	}

	private void loadIDs() {
		ConfigurationSection cs = getPlugin().getConfigurationManager()
				.getConfiguration(ConfigurationFile.ADVANCED_CONFIG)
				.getConfigurationSection("ids.toolIDs");
		for (String toolKind : cs.getKeys(false)) {
			List<String> idList;
			idList = cs.getStringList(toolKind);
			if (idList == null)
				idList = new ArrayList<String>();
			ids.put(toolKind.toLowerCase(), idList);
		}
		cs = getPlugin().getConfigurationManager()
				.getConfiguration(ConfigurationFile.ADVANCED_CONFIG)
				.getConfigurationSection("ids.armorIDs");
		for (String armorKind : cs.getKeys(false)) {
			List<String> idList;
			idList = cs.getStringList(armorKind);
			if (idList == null)
				idList = new ArrayList<String>();
			ids.put(armorKind.toLowerCase(), idList);
		}
	}

	public void loadPluginSettings() {
		setAutomaticUpdate(getPlugin().getConfigurationManager()
				.getConfiguration(ConfigurationFile.CONFIG)
				.getBoolean("options.autoUpdate", false));
		setSafeEnchantsOnly(getPlugin().getConfigurationManager()
				.getConfiguration(ConfigurationFile.CONFIG)
				.getBoolean("options.safeEnchantsOnly", true));
		setDisplayItemNameFormat(getPlugin().getConfigurationManager()
				.getConfiguration(ConfigurationFile.CONFIG)
				.getString("display.itemNameFormat", "%material%"));
		setPreventSpawnEgg(getPlugin().getConfigurationManager()
				.getConfiguration(ConfigurationFile.CONFIG)
				.getBoolean("spawnPrevention.spawnEgg", true));
		setPreventSpawner(getPlugin().getConfigurationManager()
				.getConfiguration(ConfigurationFile.CONFIG)
				.getBoolean("spawnPrevention.spawner", true));
		setPercentageMobSpawnWithItemChance(getPlugin()
				.getConfigurationManager()
				.getConfiguration(ConfigurationFile.CONFIG)
				.getDouble("percentages.mobSpawnWithItemChance", 0.25));
		setWorldsEnabled(getPlugin().getConfigurationManager()
				.getConfiguration(ConfigurationFile.CONFIG)
				.getBoolean("worlds.enabled", false));
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
							.getDouble(creature, 1.0));
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

	public void setAdvancedMobSpawnWithItemChanceMap(
			Map<String, Double> advanced_mobSpawnWithItemChance) {
		this.advanced_mobSpawnWithItemChance = advanced_mobSpawnWithItemChance;
	}

	public void setAdvancedToolTipFormat(List<String> advanced_toolTipFormat) {
		this.advanced_toolTipFormat = advanced_toolTipFormat;
	}

	/**
	 * @param automaticUpdate
	 *            the automaticUpdate to set
	 */
	public void setAutomaticUpdate(boolean automaticUpdate) {
		this.automaticUpdate = automaticUpdate;
	}

	public void setDisplayItemNameFormat(String displayItemNameFormat) {
		this.displayItemNameFormat = displayItemNameFormat;
	}

	public void setIDs(HashMap<String, List<String>> ids) {
		this.ids = ids;
	}

	public void setPercentageMobSpawnWithItemChance(
			double percentageMobSpawnWithItemChance) {
		this.percentageMobSpawnWithItemChance = percentageMobSpawnWithItemChance;
	}

	/**
	 * @param preventSpawnEgg
	 *            the preventSpawnEgg to set
	 */
	public void setPreventSpawnEgg(boolean preventSpawnEgg) {
		this.preventSpawnEgg = preventSpawnEgg;
	}

	/**
	 * @param preventSpawner
	 *            the preventSpawner to set
	 */
	public void setPreventSpawner(boolean preventSpawner) {
		this.preventSpawner = preventSpawner;
	}

	public void setSafeEnchantsOnly(boolean safeEnchantsOnly) {
		this.safeEnchantsOnly = safeEnchantsOnly;
	}

	public void setWorldsEnabled(boolean worldsEnabled) {
		this.worldsEnabled = worldsEnabled;
	}

	public void setWorldsGenerate(List<String> worldsGenerate) {
		this.worldsGenerate = worldsGenerate;
	}

	public void setWorldsUse(List<String> worldsUse) {
		this.worldsUse = worldsUse;
	}
}
