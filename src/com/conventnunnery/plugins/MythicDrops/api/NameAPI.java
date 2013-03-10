package com.conventnunnery.plugins.MythicDrops.api;

import java.util.ArrayList;
import java.util.List;

import com.conventnunnery.plugins.MythicDrops.MythicDrops;
import com.conventnunnery.plugins.MythicDrops.utilites.NameLoader;

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
}
