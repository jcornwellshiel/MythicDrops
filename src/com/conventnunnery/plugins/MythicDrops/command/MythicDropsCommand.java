package com.conventnunnery.plugins.MythicDrops.command;

import com.conventnunnery.plugins.MythicDrops.MythicDrops;
import com.conventnunnery.plugins.MythicDrops.NumberUtils;
import com.conventnunnery.plugins.MythicDrops.api.DropAPI;
import com.conventnunnery.plugins.MythicDrops.objects.CustomItem;
import com.conventnunnery.plugins.MythicDrops.objects.Tier;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
								getPlugin().getDropAPI().constructItemStack(
										DropAPI.GenerationReason.COMMAND));
						player.sendMessage(ChatColor.GREEN
								+ "You were given a random MythicDrops item.");
						break;
					} else {
						sender.sendMessage(ChatColor.RED
								+ "You don't have access to this command.");
						break;
					}
				} else if (args[0].equalsIgnoreCase("custom")) {
					if (sender.hasPermission("mythicdrops.command.spawn")) {
						if (!(sender instanceof Player)) {
							sender.sendMessage(ChatColor.RED
									+ "Only players can run this command.");
							break;
						}
						Player player = (Player) sender;
						CustomItem ci = getPlugin().getDropAPI().randomCustomItemWithChance();
						if (ci == null) {
							player.sendMessage(ChatColor.RED
									+ "Could not give you a custom MythicDrops item.");
							break;
						}
						ItemStack is = ci.toItemStack();
						if (is != null) {
							player.getInventory().addItem(is);
							player.sendMessage(ChatColor.GREEN
									+ "You were given a custom MythicDrops item.");
						} else {
							player.sendMessage(ChatColor.RED
									+ "Could not give you a custom MythicDrops item.");
						}
						break;
					} else {
						sender.sendMessage(ChatColor.RED
								+ "You don't have access to this command.");
						break;
					}
				} else if (args[0].equalsIgnoreCase("reload")) {
					if (sender.hasPermission("mythicdrops.command.spawn")) {
						getPlugin().getPluginSettings().loadPluginSettings();
						sender.sendMessage(ChatColor.GREEN
								+ "MythicDrops configuration reloaded.");
						break;
					} else {
						sender.sendMessage(ChatColor.RED
								+ "You don't have access to this command.");
						break;
					}
				} else {
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
											.constructItemStack(DropAPI.GenerationReason.COMMAND));
							player.sendMessage(ChatColor.GREEN
									+ "You were given a random MythicDrops item.");
							break;
						} else {
							Tier t = getPlugin().getTierAPI().getTierFromName(
									args[1]);
							if (t == null) {
								player.sendMessage(ChatColor.RED
										+ "That tier does not exist.");
								break;
							}
							player.getInventory().addItem(
									getPlugin().getDropAPI()
											.constructItemStack(t, DropAPI.GenerationReason.COMMAND));
							player.sendMessage(ChatColor.GREEN
									+ "You were given a " + t.getColor()
									+ t.getDisplayName() + ChatColor.GREEN
									+ " MythicDrops item.");
							break;
						}
					} else {
						sender.sendMessage(ChatColor.RED
								+ "You don't have access to this command.");
						break;
					}
				}
				if (args[0].equalsIgnoreCase("custom")) {
					if (sender.hasPermission("mythicdrops.command.spawn")) {
						if (!(sender instanceof Player)) {
							sender.sendMessage(ChatColor.RED
									+ "Only players can run this command.");
							break;
						}
						Player player;
						if (args[1].equalsIgnoreCase("self")) {
							player = (Player) sender;
						} else {
							player = Bukkit.getPlayer(args[1]);
						}
						if (player == null) {
							sender.sendMessage(ChatColor.RED
									+ "That player does not exist.");
							break;
						}
						CustomItem ci = getPlugin().getDropAPI().randomCustomItemWithChance();
						if (ci == null) {
							sender.sendMessage(
									ChatColor.RED + "You were unable to give " + ChatColor.WHITE + player.getName() +
											ChatColor.RED + " a custom MythicDrops item.");
							break;
						}
						ItemStack is = ci.toItemStack();
						if (is != null) {
							player.getInventory().addItem(is);
							player.sendMessage(ChatColor.GREEN
									+ "You were given a custom MythicDrops item.");
							sender.sendMessage(ChatColor.GREEN + "You gave " + ChatColor.WHITE + player.getName() +
									ChatColor.GREEN + " a custom MythicDrops item.");
							break;
						}
						sender.sendMessage(
								ChatColor.RED + "You were unable to give " + ChatColor.WHITE + player.getName() +
										ChatColor.RED + " a custom MythicDrops item.");
						break;
					} else {
						sender.sendMessage(ChatColor.RED
								+ "You don't have access to this command.");
						break;
					}
				} else if (args[0].equalsIgnoreCase("give")) {
					if (sender.hasPermission("mythicdrops.command.give")) {
						Player player = Bukkit.getPlayer(args[1]);
						if (player == null || !player.isOnline()) {
							sender.sendMessage(ChatColor.RED
									+ "That player is not online.");
							break;
						}
						player.getInventory().addItem(
								getPlugin().getDropAPI().constructItemStack(
										DropAPI.GenerationReason.COMMAND));
						player.sendMessage(ChatColor.GREEN
								+ "You were given a random MythicDrops item.");
						sender.sendMessage(ChatColor.GREEN + "You gave "
								+ ChatColor.WHITE + player.getName()
								+ ChatColor.GREEN
								+ " a random MythicDrops item.");
						break;
					} else {
						sender.sendMessage(ChatColor.RED
								+ "You don't have access to this command.");
						break;
					}
				} else {
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
												.constructItemStack(DropAPI.GenerationReason.COMMAND));
							}
							player.sendMessage(ChatColor.GREEN
									+ "You were given " + String.valueOf(amt)
									+ " random MythicDrops item.");
							break;
						} else {
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
												.constructItemStack(t, DropAPI.GenerationReason.COMMAND));
							}
							player.sendMessage(ChatColor.GREEN
									+ "You were given " + String.valueOf(amt)
									+ " " + t.getColor() + t.getDisplayName()
									+ ChatColor.GREEN + " MythicDrops item.");
							break;
						}
					} else {
						sender.sendMessage(ChatColor.RED
								+ "You don't have access to this command.");
						break;
					}
				} else if (args[0].equalsIgnoreCase("give")) {
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
											.constructItemStack(DropAPI.GenerationReason.COMMAND));
							player.sendMessage(ChatColor.GREEN
									+ "You were given a random MythicDrops item.");
							break;
						} else {
							Tier t = getPlugin().getTierAPI().getTierFromName(
									args[2]);
							if (t == null) {
								player.sendMessage(ChatColor.RED
										+ "That tier does not exist.");
								break;
							}
							player.getInventory().addItem(
									getPlugin().getDropAPI()
											.constructItemStack(t, DropAPI.GenerationReason.COMMAND));
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
					} else {
						sender.sendMessage(ChatColor.RED
								+ "You don't have access to this command.");
						break;
					}
				} else if (args[0].equalsIgnoreCase("custom")) {
					if (sender.hasPermission("mythicdrops.command.spawn")) {
						if (!(sender instanceof Player)) {
							sender.sendMessage(ChatColor.RED
									+ "Only players can run this command.");
							break;
						}
						Player player;
						if (args[1].equalsIgnoreCase("self")) {
							player = (Player) sender;
						} else {
							player = Bukkit.getPlayer(args[1]);
						}
						if (player == null) {
							sender.sendMessage(ChatColor.RED
									+ "That player does not exist.");
							break;
						}
						CustomItem ci = getPlugin().getDropAPI().getCustomItemByName(args[2]);
						if (ci == null) {
							sender.sendMessage(
									ChatColor.RED + "You were unable to give " + ChatColor.WHITE + player.getName() +
											ChatColor.RED + " a custom MythicDrops item.");
							break;
						}
						ItemStack is = ci.toItemStack();
						if (is != null) {
							player.getInventory().addItem(is);
							player.sendMessage(ChatColor.GREEN
									+ "You were given a custom MythicDrops item.");
							sender.sendMessage(ChatColor.GREEN + "You gave " + ChatColor.WHITE + player.getName() +
									ChatColor.GREEN + " a custom MythicDrops item.");
							break;
						}
						sender.sendMessage(
								ChatColor.RED + "You were unable to give " + ChatColor.WHITE + player.getName() +
										ChatColor.RED + " a custom MythicDrops item.");
						break;
					} else {
						sender.sendMessage(ChatColor.RED
								+ "You don't have access to this command.");
						break;
					}
				} else {
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
												.constructItemStack(DropAPI.GenerationReason.COMMAND));
							}
							player.sendMessage(ChatColor.GREEN
									+ "You were given " + String.valueOf(amt)
									+ " random MythicDrops item.");
							break;
						} else {
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
												.constructItemStack(t, DropAPI.GenerationReason.COMMAND));
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
					} else if (args[0].equalsIgnoreCase("custom")) {
						if (sender.hasPermission("mythicdrops.command.spawn")) {
							if (!(sender instanceof Player)) {
								sender.sendMessage(ChatColor.RED
										+ "Only players can run this command.");
								break;
							}
							Player player;
							if (args[1].equalsIgnoreCase("self")) {
								player = (Player) sender;
							} else {
								player = Bukkit.getPlayer(args[1]);
							}
							if (player == null) {
								sender.sendMessage(ChatColor.RED
										+ "That player does not exist.");
								break;
							}
							int amt = NumberUtils.getInt(args[3], 1);
							CustomItem ci = getPlugin().getDropAPI().getCustomItemByName(args[2]);
							if (ci == null) {
								sender.sendMessage(
										ChatColor.RED + "You were unable to give " + ChatColor.WHITE +
												player.getName() +
												ChatColor.RED + " a custom MythicDrops item.");
								break;
							}
							ItemStack is = ci.toItemStack();
							if (is != null) {
								for (int i = 0; i < amt; i++) {
									player.getInventory().addItem(is);
								}
								player.sendMessage(ChatColor.GREEN
										+ "You were given " + amt + " custom MythicDrops item.");
								sender.sendMessage(ChatColor.GREEN + "You gave " + ChatColor.WHITE + player.getName() +
										ChatColor.GREEN + " " + amt + " custom MythicDrops items.");
								break;
							}
							sender.sendMessage(
									ChatColor.RED + "You were unable to give " + ChatColor.WHITE + player.getName() +
											ChatColor.RED + " a custom MythicDrops item.");
							break;
						} else {
							sender.sendMessage(ChatColor.RED
									+ "You don't have access to this command.");
							break;
						}
					} else {
						sender.sendMessage(ChatColor.RED
								+ "You don't have access to this command.");
						break;
					}
				} else {
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
		sender.sendMessage(ChatColor.DARK_BLUE + "/md" + ChatColor.AQUA
				+ " - " + ChatColor.GRAY + "Shows plugin help.");
		if (sender.hasPermission("mythicdrops.command.spawn")) {
			sender.sendMessage(ChatColor.BLUE
					+ "/md spawn [tier|*] [amount]" + ChatColor.AQUA + " - "
					+ ChatColor.GRAY
					+ "Gives the sender [amount] MythicDrops of [tier].");
		}
		if (sender.hasPermission("mythicdrops.command.custom")) {
			sender.sendMessage(ChatColor.BLUE
					+ "/md custom [player|self] [name] [amount]" + ChatColor.AQUA + " - "
					+ ChatColor.GRAY
					+ "Gives the [player|sender] an [amount] of custom items with name [name].");
		}
		if (sender.hasPermission("mythicdrops.command.give")) {
			sender.sendMessage(ChatColor.BLUE
					+ "/md give <player> [tier|*] [amount]" + ChatColor.AQUA
					+ " - " + ChatColor.GRAY
					+ "Gives the <player> [amount] MythicDrops of [tier].");
		}
		if (sender.hasPermission("mythicdrops.command.reload")) {
			sender.sendMessage(ChatColor.BLUE + "/md reload"
					+ ChatColor.AQUA + " - " + ChatColor.GRAY
					+ "Reloads the plugin's configuration files.");
		}
		sender.sendMessage(ChatColor.DARK_PURPLE
				+ "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
	}
}
