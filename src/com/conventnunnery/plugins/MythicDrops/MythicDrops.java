package com.conventnunnery.plugins.MythicDrops;

import java.io.IOException;
import java.util.Random;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.BukkitMetricsLite;

import com.conventnunnery.plugins.MythicDrops.api.DropAPI;
import com.conventnunnery.plugins.MythicDrops.api.EntityAPI;
import com.conventnunnery.plugins.MythicDrops.api.ItemAPI;
import com.conventnunnery.plugins.MythicDrops.api.NameAPI;
import com.conventnunnery.plugins.MythicDrops.api.TierAPI;
import com.conventnunnery.plugins.MythicDrops.builders.TierBuilder;
import com.conventnunnery.plugins.MythicDrops.command.MythicDropsCommand;
import com.conventnunnery.plugins.MythicDrops.configuration.ConfigurationManager;
import com.conventnunnery.plugins.MythicDrops.listeners.EntityListener;

public class MythicDrops extends JavaPlugin implements Listener {

	private ConfigurationManager configurationManager;
	private PluginSettings pluginSettings;
	private TierAPI tierAPI;
	private NameAPI nameAPI;
	private ItemAPI itemAPI;
	private DropAPI dropAPI;
	private EntityAPI entityAPI;
	private Debugger debug;
	private Updater updater;

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
	 * @return the entityAPI
	 */
	public EntityAPI getEntityAPI() {
		return entityAPI;
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

	/**
	 * @return the updater
	 */
	public Updater getUpdater() {
		return updater;
	}

	@Override
	public void onDisable() {
		getDropAPI().saveCustomItems();
		getTierAPI().saveTiers();
	}

	@Override
	public void onEnable() {
		debug = new Debugger(this);
		configurationManager = new ConfigurationManager(this);
		pluginSettings = new PluginSettings(this);
		pluginSettings.loadPluginSettings();
		pluginSettings.debugSettings();
		tierAPI = new TierAPI(this);
		nameAPI = new NameAPI(this);
		itemAPI = new ItemAPI(this);
		dropAPI = new DropAPI(this);
		entityAPI = new EntityAPI(this);
		new TierBuilder(this).build();
		getTierAPI().debugTiers();
		getCommand("mythicdrops").setExecutor(new MythicDropsCommand(this));
		getServer().getPluginManager().registerEvents(new EntityListener(this),
				this);
		if (getPluginSettings().isAutomaticUpdate()) {
			updater = new Updater(this, "mythicdrops", this.getFile(),
					Updater.UpdateType.DEFAULT, false);
		}
		startStatistics();
	}

	private void startStatistics() {
		try {
			BukkitMetricsLite metrics = new BukkitMetricsLite(this);
			metrics.start();
		}
		catch (IOException e) {
		}
	}

}
