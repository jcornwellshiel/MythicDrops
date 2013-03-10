package com.conventnunnery.plugins.MythicDrops;

import org.bukkit.plugin.java.JavaPlugin;

import com.conventnunnery.plugins.MythicDrops.configuration.ConfigurationManager;

public class MythicDrops extends JavaPlugin {

	private ConfigurationManager configurationManager;
	private PluginSettings pluginSettings;

	public ConfigurationManager getConfigurationManager() {
		return configurationManager;
	}

	public PluginSettings getPluginSettings() {
		return pluginSettings;
	}

	@Override
	public void onEnable() {
		configurationManager = new ConfigurationManager(this);
		pluginSettings = new PluginSettings(this);
	}

}
