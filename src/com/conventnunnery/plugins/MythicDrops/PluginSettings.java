package com.conventnunnery.plugins.MythicDrops;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.conventnunnery.plugins.MythicDrops.configuration.ConfigurationManager.ConfigurationFile;

public class PluginSettings {

	private final MythicDrops plugin;

	private String displayItemNameFormat;
	private double percentageMobSpawnWithItemChance;
	private boolean worldsEnabled;
	private List<String> worldsGenerate;
	private List<String> worldsUse;
	private List<String> idsToolIdsSwords;
	private List<String> idsToolIdsAxes;
	private List<String> idsToolIdsPickaxes;
	private List<String> idsToolIdsShovels;
	private List<String> idsToolIdsHoes;
	private List<String> idsToolIdsMisc;
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

	public List<String> getIdsToolIdsAxes() {
		return idsToolIdsAxes;
	}

	public List<String> getIdsToolIdsHoes() {
		return idsToolIdsHoes;
	}

	public List<String> getIdsToolIdsMisc() {
		return idsToolIdsMisc;
	}

	public List<String> getIdsToolIdsPickaxes() {
		return idsToolIdsPickaxes;
	}

	public List<String> getIdsToolIdsShovels() {
		return idsToolIdsShovels;
	}

	public List<String> getIdsToolIdsSwords() {
		return idsToolIdsSwords;
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
		setIdsToolIdsSwords(getPlugin().getConfigurationManager()
				.getConfiguration(ConfigurationFile.ADVANCED_CONFIG)
				.getStringList("ids.toolIDs.sword"));
		if (getIdsToolIdsSwords() == null)
			setIdsToolIdsSwords(new ArrayList<String>());
		setIdsToolIdsAxes(getPlugin().getConfigurationManager()
				.getConfiguration(ConfigurationFile.ADVANCED_CONFIG)
				.getStringList("ids.toolIDs.axe"));
		if (getIdsToolIdsAxes() == null)
			setIdsToolIdsAxes(new ArrayList<String>());
		setIdsToolIdsPickaxes(getPlugin().getConfigurationManager()
				.getConfiguration(ConfigurationFile.ADVANCED_CONFIG)
				.getStringList("ids.toolIDs.pickaxe"));
		if (getIdsToolIdsPickaxes() == null)
			setIdsToolIdsPickaxes(new ArrayList<String>());
		setIdsToolIdsShovels(getPlugin().getConfigurationManager()
				.getConfiguration(ConfigurationFile.ADVANCED_CONFIG)
				.getStringList("ids.toolIDs.shovel"));
		if (getIdsToolIdsShovels() == null)
			setIdsToolIdsShovels(new ArrayList<String>());
		setIdsToolIdsHoes(getPlugin().getConfigurationManager()
				.getConfiguration(ConfigurationFile.ADVANCED_CONFIG)
				.getStringList("ids.toolIDs.hoe"));
		if (getIdsToolIdsHoes() == null)
			setIdsToolIdsHoes(new ArrayList<String>());
		setIdsToolIdsMisc(getPlugin().getConfigurationManager()
				.getConfiguration(ConfigurationFile.ADVANCED_CONFIG)
				.getStringList("ids.toolIDs.misc"));
		if (getIdsToolIdsMisc() == null)
			setIdsToolIdsMisc(new ArrayList<String>());
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

	public void setIdsToolIdsAxes(List<String> idsToolIdsAxes) {
		this.idsToolIdsAxes = idsToolIdsAxes;
	}

	public void setIdsToolIdsHoes(List<String> idsToolIdsHoes) {
		this.idsToolIdsHoes = idsToolIdsHoes;
	}

	public void setIdsToolIdsMisc(List<String> idsToolIdsMisc) {
		this.idsToolIdsMisc = idsToolIdsMisc;
	}

	public void setIdsToolIdsPickaxes(List<String> idsToolIdsPickaxes) {
		this.idsToolIdsPickaxes = idsToolIdsPickaxes;
	}

	public void setIdsToolIdsShovels(List<String> idsToolIdsShovels) {
		this.idsToolIdsShovels = idsToolIdsShovels;
	}

	public void setIdsToolIdsSwords(List<String> idsToolIdsSwords) {
		this.idsToolIdsSwords = idsToolIdsSwords;
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
