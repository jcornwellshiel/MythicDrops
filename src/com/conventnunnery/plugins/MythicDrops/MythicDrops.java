/*
 * Copyright (c) 2013. ToppleTheNun
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.conventnunnery.plugins.MythicDrops;

import com.conventnunnery.plugins.MythicDrops.api.*;
import com.conventnunnery.plugins.MythicDrops.builders.TierBuilder;
import com.conventnunnery.plugins.MythicDrops.command.MythicDropsCommand;
import com.conventnunnery.plugins.MythicDrops.configuration.ConfigurationManager;
import com.conventnunnery.plugins.MythicDrops.listeners.EntityListener;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.BukkitMetricsLite;

import java.io.IOException;
import java.util.Random;

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
	private Random random = new Random();

	public Random getRandom() {
		return random;
	}

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
			updater = new Updater(this, "mythic", this.getFile(),
					Updater.UpdateType.DEFAULT, false);
		}
		startStatistics();
	}

	private void startStatistics() {
		try {
			BukkitMetricsLite metrics;
			metrics = new BukkitMetricsLite(this);
			metrics.start();
		} catch (IOException e) {
		}
	}

}
