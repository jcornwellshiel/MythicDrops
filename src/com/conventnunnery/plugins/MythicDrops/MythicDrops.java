package com.conventnunnery.plugins.MythicDrops;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.conventnunnery.plugins.MythicDrops.api.DropAPI;
import com.conventnunnery.plugins.MythicDrops.api.EntityAPI;
import com.conventnunnery.plugins.MythicDrops.api.ItemAPI;
import com.conventnunnery.plugins.MythicDrops.api.NameAPI;
import com.conventnunnery.plugins.MythicDrops.api.TierAPI;
import com.conventnunnery.plugins.MythicDrops.builders.TierBuilder;
import com.conventnunnery.plugins.MythicDrops.configuration.ConfigurationManager;
import com.conventnunnery.plugins.MythicDrops.objects.Tier;

public class MythicDrops extends JavaPlugin implements Listener,
		CommandExecutor {

	private ConfigurationManager configurationManager;
	private PluginSettings pluginSettings;
	private TierAPI tierAPI;
	private NameAPI nameAPI;
	private ItemAPI itemAPI;
	private DropAPI dropAPI;
	private EntityAPI entityAPI;
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

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String commandLabel, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (!p.hasPermission("mythicdrops.command")) {
				p.sendMessage(ChatColor.RED + "You don't have permission!");
				return true;
			}
			if (args.length == 0)
				p.getInventory().addItem(getDropAPI().constructItemStack());
			else {
				int amt = NumberUtils.getInt(args[0], 1);
				if (amt > 36)
					amt = 36;
				for (int i = 0; i < amt; i++) {
					p.getInventory().addItem(getDropAPI().constructItemStack());
				}
			}
		}
		return true;
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
		entityAPI = new EntityAPI(this);
		new TierBuilder(this).build();
		getTierAPI().debugTiers();
		getServer().getPluginManager().registerEvents(this, this);
	}

	@EventHandler
	public void onEntitySpawn(CreatureSpawnEvent event) {
		EntityType entType = event.getEntityType();
		double globalChanceToSpawn = getPluginSettings()
				.getPercentageMobSpawnWithItemChance();
		double mobChanceToSpawn = 0.0;
		if (getPluginSettings().getAdvancedMobSpawnWithItemChanceMap()
				.containsKey(entType.name())) {
			mobChanceToSpawn = getPluginSettings()
					.getAdvancedMobSpawnWithItemChanceMap().get(entType.name());
		}
		double chance = globalChanceToSpawn * mobChanceToSpawn;
		for (int i = 0; i < 5; i++) {
			if (random.nextDouble() < chance) {
				Tier t = getTierAPI().randomTierWithChance();
				ItemStack itemstack = getDropAPI().constructItemStack(t);
				getEntityAPI().equipEntity(event.getEntity(), itemstack, t);
				chance *= 0.5;
				continue;
			}
			break;
		}
	}
}
