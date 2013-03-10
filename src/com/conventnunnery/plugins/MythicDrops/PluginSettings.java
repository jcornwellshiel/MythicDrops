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
	private List<String> worldsGenerate;
	private List<String> worldsUse;
	private HashMap<String, List<String>> ids;
	private Map<String, Double> advanced_mobSpawnWithItemChance;

	public PluginSettings(MythicDrops plugin) {
		this.plugin = plugin;
	}

	public Map<String, Double> getAdvancedMobSpawnWithItemChanceMap() {
		return advanced_mobSpawnWithItemChance;
	}

	public String getDisplayItemNameFormat() {
		return displayItemNameFormat;
	}

	public HashMap<String, List<String>> getIDs() {
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
		setDisplayItemNameFormat(getPlugin().getConfigurationManager()
				.getConfiguration(ConfigurationFile.CONFIG)
				.getString("display.itemNameFormat", "%material%"));
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
		}
	}

	public void setAdvancedMobSpawnWithItemChanceMap(
			Map<String, Double> advanced_mobSpawnWithItemChance) {
		this.advanced_mobSpawnWithItemChance = advanced_mobSpawnWithItemChance;
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
