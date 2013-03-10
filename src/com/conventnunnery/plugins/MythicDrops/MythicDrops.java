package com.conventnunnery.plugins.MythicDrops;

import org.bukkit.plugin.java.JavaPlugin;

import com.conventnunnery.plugins.MythicDrops.api.NameAPI;
import com.conventnunnery.plugins.MythicDrops.api.TierAPI;
import com.conventnunnery.plugins.MythicDrops.builders.TierBuilder;
import com.conventnunnery.plugins.MythicDrops.configuration.ConfigurationManager;

public class MythicDrops extends JavaPlugin {

	private ConfigurationManager configurationManager;
	private PluginSettings pluginSettings;
	private TierAPI tierAPI;
	private NameAPI nameAPI;
	private Debugger debug;

	public ConfigurationManager getConfigurationManager() {
		return configurationManager;
	}

	/**
	 * @return the debug
	 */
	public Debugger getDebug() {
		return debug;
	}

	/**
	 * @return the nameAPI
	 */
	public NameAPI getNameAPI() {
		return nameAPI;
	}

	public PluginSettings getPluginSettings() {
		return pluginSettings;
	}

	/**
	 * @return the tierAPI
	 */
	public TierAPI getTierAPI() {
		return tierAPI;
	}

	@Override
	public void onEnable() {
		debug = new Debugger(this);
		configurationManager = new ConfigurationManager(this);
		pluginSettings = new PluginSettings(this);
		tierAPI = new TierAPI(this);
		nameAPI = new NameAPI(this);
		new TierBuilder(this).build();
		getTierAPI().debugTiers();
	}

}
