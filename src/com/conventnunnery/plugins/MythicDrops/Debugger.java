package com.conventnunnery.plugins.MythicDrops;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

import org.bukkit.plugin.Plugin;

public class Debugger {

	public final Plugin plugin;

	private final File dataFolder;

	public Debugger(Plugin plugin) {
		this.plugin = plugin;
		this.dataFolder = plugin.getDataFolder();
	}

	public void debug(String message) {
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
			pw.println(Calendar.getInstance().getTime().toString() + " | "
					+ message);
			pw.flush();
			pw.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Plugin getPlugin() {
		return plugin;
	}
}
