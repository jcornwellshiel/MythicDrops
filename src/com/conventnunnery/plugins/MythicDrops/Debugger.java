package com.conventnunnery.plugins.MythicDrops;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

public class Debugger {

	public final Plugin plugin;

	private final File dataFolder;

	public Debugger(Plugin plugin) {
		this.plugin = plugin;
		this.dataFolder = plugin.getDataFolder();
	}

	public void debug(String... messages) {
		try {
			if (!dataFolder.exists()) {
				dataFolder.mkdirs();
			}
			File saveTo = new File(dataFolder, "debug.txt");
			if (!saveTo.exists()) {
				saveTo.createNewFile();
			}
			FileWriter fw = new FileWriter(saveTo, true);
			PrintWriter pw = new PrintWriter(fw);
			for (String message : messages) {
				pw.println(Calendar.getInstance().getTime().toString() + " | "
						+ message);
			}
			pw.flush();
			pw.close();
		} catch (IOException e) {
			Bukkit.getLogger().severe(e.getMessage());
		}
	}

	public Plugin getPlugin() {
		return plugin;
	}
}
