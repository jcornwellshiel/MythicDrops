/*
 * Copyright (c) 2013. ToppleTheNun
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package de.funkyclan.mc.RepairRecipe;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RepairRecipeCommand implements CommandExecutor {

	private RepairRecipe plugin;

	public RepairRecipeCommand(RepairRecipe plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		if (command.getName().equalsIgnoreCase("repairrecipe")) {
			if (args.length == 1 && args[0].equalsIgnoreCase("reload") && hasAdminPermission(sender)) {
				plugin.getConfigurator().reloadConfig();
				sender.sendMessage("[RepairRecipe] Config reloaded");
				sender.sendMessage("[RepairRecipe] If you changed items.yml, please restart server.");
				return true;
			}
			if (args.length == 1 && args[0].equalsIgnoreCase("debug") && hasAdminPermission(sender)) {
				RepairRecipeConfig.DEBUG = !RepairRecipeConfig.DEBUG;
				sender.sendMessage("[RepairRecipe] Debug set to " + RepairRecipeConfig.DEBUG);
				return true;
			}
			if (args.length == 2 && args[0].equalsIgnoreCase("debug") && hasAdminPermission(sender)) {
				boolean debug = !RepairRecipeConfig.DEBUG;
				if (args[1].equalsIgnoreCase("true") || args[1].equalsIgnoreCase("1")) {
					debug = true;
				} else if (args[1].equalsIgnoreCase("false") || args[1].equalsIgnoreCase("0")) {
					debug = false;
				}
				RepairRecipeConfig.DEBUG = debug;
				sender.sendMessage("[RepairRecipe] Debug set to " + RepairRecipeConfig.DEBUG);
				return true;
			}
		}
		return false;
	}

	private boolean hasAdminPermission(CommandSender sender) {
		boolean permission = true;
		if (sender instanceof Player) {
			permission = plugin.getConfigurator().hasPermission((Player) sender, RepairRecipeConfig.PERM_ADMIN);
		}
		if (!permission) {
			sender.sendMessage("You are not allowed to use this command.");
		}
		return permission;
	}

}
