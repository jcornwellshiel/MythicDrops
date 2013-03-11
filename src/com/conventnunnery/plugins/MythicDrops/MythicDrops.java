package com.conventnunnery.plugins.MythicDrops;

import java.util.Random;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.conventnunnery.plugins.MythicDrops.api.DropAPI;
import com.conventnunnery.plugins.MythicDrops.api.ItemAPI;
import com.conventnunnery.plugins.MythicDrops.api.NameAPI;
import com.conventnunnery.plugins.MythicDrops.api.TierAPI;
import com.conventnunnery.plugins.MythicDrops.builders.TierBuilder;
import com.conventnunnery.plugins.MythicDrops.configuration.ConfigurationManager;

public class MythicDrops extends JavaPlugin implements Listener {

	private ConfigurationManager configurationManager;
	private PluginSettings pluginSettings;
	private TierAPI tierAPI;
	private NameAPI nameAPI;
	private ItemAPI itemAPI;
	private DropAPI dropAPI;
	private Debugger debug;

	public Random random = new Random();

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
	 * @return the dropAPI
	 */
	public DropAPI getDropAPI() {
		return dropAPI;
	}

	/**
	 * @return the itemAPI
	 */
	public ItemAPI getItemAPI() {
		return itemAPI;
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
		pluginSettings.loadPluginSettings();
		tierAPI = new TierAPI(this);
		nameAPI = new NameAPI(this);
		itemAPI = new ItemAPI(this);
		dropAPI = new DropAPI(this);
		new TierBuilder(this).build();
		getTierAPI().debugTiers();
		getServer().getPluginManager().registerEvents(this, this);
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		event.getPlayer().getInventory().addItem(dropAPI.constructItemStack());
	}

}
