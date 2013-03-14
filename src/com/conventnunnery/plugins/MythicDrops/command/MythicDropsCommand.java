package com.conventnunnery.plugins.MythicDrops.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.conventnunnery.plugins.MythicDrops.MythicDrops;
import com.conventnunnery.plugins.MythicDrops.NumberUtils;
import com.conventnunnery.plugins.MythicDrops.objects.Tier;

public class MythicDropsCommand implements CommandExecutor {

	private final MythicDrops plugin;

	public MythicDropsCommand(MythicDrops plugin) {
		this.plugin = plugin;
	}

	/**
	 * @return the plugin
	 */
	public MythicDrops getPlugin() {
		return plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String commandLabel, String[] args) {
		switch (args.length) {
			case 1:
				if (args[0].equalsIgnoreCase("spawn")) {
					if (sender.hasPermission("mythicdrops.command.spawn")) {
						if (!(sender instanceof Player)) {
							sender.sendMessage(ChatColor.RED
									+ "Only players can run this command.");
							break;
						}
						Player player = (Player) sender;
						player.getInventory().addItem(
								getPlugin().getDropAPI().constructItemStack());
						player.sendMessage(ChatColor.GREEN
								+ "You were given a random MythicDrops item.");
						break;
					}
					else {
						sender.sendMessage(ChatColor.RED
								+ "You don't have access to this command.");
						break;
					}
				}
				else if (args[0].equalsIgnoreCase("reload")) {
					if (sender.hasPermission("mythicdrops.command.spawn")) {
						getPlugin().getPluginSettings().loadPluginSettings();
						sender.sendMessage(ChatColor.GREEN
								+ "MythicDrops configuration reloaded.");
						break;
					}
					else {
						sender.sendMessage(ChatColor.RED
								+ "You don't have access to this command.");
						break;
					}
				}
				else {
					showHelp(sender);
					break;
				}
			case 2:
				if (args[0].equalsIgnoreCase("spawn")) {
					if (sender.hasPermission("mythicdrops.command.spawn")) {
						if (!(sender instanceof Player)) {
							sender.sendMessage(ChatColor.RED
									+ "Only players can run this command.");
							break;
						}
						Player player = (Player) sender;
						if (args[1].equalsIgnoreCase("*")) {
							player.getInventory().addItem(
									getPlugin().getDropAPI()
											.constructItemStack());
							player.sendMessage(ChatColor.GREEN
									+ "You were given a random MythicDrops item.");
							break;
						}
						else {
							Tier t = getPlugin().getTierAPI().getTierFromName(
									args[1]);
							if (t == null) {
								player.sendMessage(ChatColor.RED
										+ "That tier does not exist.");
								break;
							}
							player.getInventory().addItem(
									getPlugin().getDropAPI()
											.constructItemStack(t));
							player.sendMessage(ChatColor.GREEN
									+ "You were given a " + t.getColor()
									+ t.getDisplayName() + ChatColor.GREEN
									+ " MythicDrops item.");
							break;
						}
					}
					else {
						sender.sendMessage(ChatColor.RED
								+ "You don't have access to this command.");
						break;
					}
				}
				else if (args[0].equalsIgnoreCase("give")) {
					if (sender.hasPermission("mythicdrops.command.give")) {
						Player player = Bukkit.getPlayer(args[1]);
						if (player == null || !player.isOnline()) {
							sender.sendMessage(ChatColor.RED
									+ "That player is not online.");
							break;
						}
						player.getInventory().addItem(
								getPlugin().getDropAPI().constructItemStack());
						player.sendMessage(ChatColor.GREEN
								+ "You were given a random MythicDrops item.");
						sender.sendMessage(ChatColor.GREEN + "You gave "
								+ ChatColor.WHITE + player.getName()
								+ ChatColor.GREEN
								+ " a random MythicDrops item.");
						break;
					}
					else {
						sender.sendMessage(ChatColor.RED
								+ "You don't have access to this command.");
						break;
					}
				}
				else {
					showHelp(sender);
					break;
				}
			case 3:
				if (args[0].equalsIgnoreCase("spawn")) {
					if (sender.hasPermission("mythicdrops.command.spawn")) {
						if (!(sender instanceof Player)) {
							sender.sendMessage(ChatColor.RED
									+ "Only players can run this command.");
							break;
						}
						Player player = (Player) sender;
						if (args[1].equalsIgnoreCase("*")) {
							int amt = NumberUtils.getInt(args[2], 1);
							for (int i = 0; i < amt; i++) {
								player.getInventory().addItem(
										getPlugin().getDropAPI()
												.constructItemStack());
							}
							player.sendMessage(ChatColor.GREEN
									+ "You were given " + String.valueOf(amt)
									+ " random MythicDrops item.");
							break;
						}
						else {
							Tier t = getPlugin().getTierAPI().getTierFromName(
									args[1]);
							if (t == null) {
								player.sendMessage(ChatColor.RED
										+ "That tier does not exist.");
								break;
							}
							int amt = NumberUtils.getInt(args[2], 1);
							for (int i = 0; i < amt; i++) {
								player.getInventory().addItem(
										getPlugin().getDropAPI()
												.constructItemStack(t));
							}
							player.sendMessage(ChatColor.GREEN
									+ "You were given " + String.valueOf(amt)
									+ " " + t.getColor() + t.getDisplayName()
									+ ChatColor.GREEN + " MythicDrops item.");
							break;
						}
					}
					else {
						sender.sendMessage(ChatColor.RED
								+ "You don't have access to this command.");
						break;
					}
				}
				else if (args[0].equalsIgnoreCase("give")) {
					if (sender.hasPermission("mythicdrops.command.give")) {
						Player player = Bukkit.getPlayer(args[1]);
						if (player == null || !player.isOnline()) {
							sender.sendMessage(ChatColor.RED
									+ "That player is not online.");
							break;
						}
						if (args[2].equalsIgnoreCase("*")) {
							player.getInventory().addItem(
									getPlugin().getDropAPI()
											.constructItemStack());
							player.sendMessage(ChatColor.GREEN
									+ "You were given a random MythicDrops item.");
							break;
						}
						else {
							Tier t = getPlugin().getTierAPI().getTierFromName(
									args[2]);
							if (t == null) {
								player.sendMessage(ChatColor.RED
										+ "That tier does not exist.");
								break;
							}
							player.getInventory().addItem(
									getPlugin().getDropAPI()
											.constructItemStack(t));
							player.sendMessage(ChatColor.GREEN
									+ "You were given a " + t.getColor()
									+ t.getDisplayName() + ChatColor.GREEN
									+ " MythicDrops item.");
							sender.sendMessage(ChatColor.GREEN + "You gave "
									+ ChatColor.WHITE + player.getName()
									+ ChatColor.GREEN + " a " + t.getColor()
									+ t.getDisplayName() + ChatColor.GREEN
									+ " MythicDrops item.");
							break;
						}
					}
					else {
						sender.sendMessage(ChatColor.RED
								+ "You don't have access to this command.");
						break;
					}
				}
				else {
					showHelp(sender);
					break;
				}
			case 4:
				if (args[0].equalsIgnoreCase("give")) {
					if (sender.hasPermission("mythicdrops.command.give")) {
						Player player = Bukkit.getPlayer(args[1]);
						if (player == null || !player.isOnline()) {
							sender.sendMessage(ChatColor.RED
									+ "That player is not online.");
							break;
						}
						if (args[2].equalsIgnoreCase("*")) {
							int amt = NumberUtils.getInt(args[3], 1);
							for (int i = 0; i < amt; i++) {
								player.getInventory().addItem(
										getPlugin().getDropAPI()
												.constructItemStack());
							}
							player.sendMessage(ChatColor.GREEN
									+ "You were given " + String.valueOf(amt)
									+ " random MythicDrops item.");
							break;
						}
						else {
							Tier t = getPlugin().getTierAPI().getTierFromName(
									args[2]);
							if (t == null) {
								player.sendMessage(ChatColor.RED
										+ "That tier does not exist.");
								break;
							}
							int amt = NumberUtils.getInt(args[3], 1);
							for (int i = 0; i < amt; i++) {
								player.getInventory().addItem(
										getPlugin().getDropAPI()
												.constructItemStack(t));
							}
							player.sendMessage(ChatColor.GREEN
									+ "You were given " + String.valueOf(amt)
									+ " " + t.getColor() + t.getDisplayName()
									+ ChatColor.GREEN + " MythicDrops item.");
							sender.sendMessage(ChatColor.GREEN + "You gave "
									+ ChatColor.WHITE + player.getName()
									+ ChatColor.GREEN + " "
									+ String.valueOf(amt) + " " + t.getColor()
									+ t.getDisplayName() + ChatColor.GREEN
									+ " MythicDrops item.");
							break;
						}
					}
					else {
						sender.sendMessage(ChatColor.RED
								+ "You don't have access to this command.");
						break;
					}
				}
				else {
					showHelp(sender);
					break;
				}
			default:
				showHelp(sender);
				break;
		}
		return true;
	}

	private void showHelp(CommandSender sender) {
		sender.sendMessage(ChatColor.DARK_PURPLE
				+ "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
		sender.sendMessage(ChatColor.BLUE + "MythicDrops v"
				+ getPlugin().getDescription().getVersion() + " Help");
		sender.sendMessage(ChatColor.BLUE + "[ ] - Optional | < > - Mandatory");
		sender.sendMessage(ChatColor.DARK_BLUE + "/md " + ChatColor.AQUA
				+ " - " + ChatColor.GRAY + "Shows plugin help.");
		if (sender.hasPermission("mythicdrops.command.spawn")) {
			sender.sendMessage(ChatColor.DARK_BLUE
					+ "/md spawn [tier|*] [amount]" + ChatColor.AQUA + " - "
					+ ChatColor.GRAY
					+ "Gives the sender [amount] MythicDrops of [tier].");
		}
		if (sender.hasPermission("mythicdrops.command.give")) {
			sender.sendMessage(ChatColor.DARK_BLUE
					+ "/md give <player> [tier|*] [amount]" + ChatColor.AQUA
					+ " - " + ChatColor.GRAY
					+ "Gives the <player> [amount] MythicDrops of [tier].");
		}
		if (sender.hasPermission("mythicdrops.command.reload")) {
			sender.sendMessage(ChatColor.DARK_BLUE + "/md reload"
					+ ChatColor.AQUA + " - " + ChatColor.GRAY
					+ "Reloads the plugin's configuration files.");
		}
		sender.sendMessage(ChatColor.DARK_PURPLE
				+ "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
	}
}
