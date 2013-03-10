package com.conventnunnery.plugins.MythicDrops.utilites;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.util.List;

import com.conventnunnery.plugins.MythicDrops.MythicDrops;

public class NameLoader {

	private final File dataFolder;
	private final MythicDrops plugin;

	public NameLoader(final MythicDrops instance) {
		plugin = instance;
		dataFolder = instance.getDataFolder();
	}

	/**
	 * @return the plugin
	 */
	public MythicDrops getPlugin() {
		return plugin;
	}

	public void loadFile(final List<String> l, final String name) {
		try {
			BufferedReader list = new BufferedReader(new FileReader(new File(
					dataFolder, name)));
			String p;
			while ((p = list.readLine()) != null) {
				if (!p.contains("#") && (p.length() > 0)) {
					l.add(p);
				}
			}
			list.close();
		}
		catch (Exception e) {
		}
	}

	/**
	 * Creates a file with given name
	 * 
	 * @param name
	 *            Name of the file to write
	 */
	public void writeDefault(final String name, boolean overwrite) {
		File actual = new File(dataFolder, name);
		if (name.contains(".jar")) {
			actual = new File(dataFolder.getParent(), name);
		}
		if (!actual.exists() || overwrite) {
			if (!actual.getParentFile().exists()) {
				actual.getParentFile().mkdirs();
			}
			try {
				InputStream input = this.getClass().getResourceAsStream(
						"/" + name);
				FileOutputStream output = new FileOutputStream(actual, false);
				byte[] buf = new byte[1024];
				int length = 0;
				while ((length = input.read(buf)) > 0) {
					output.write(buf, 0, length);
				}
				output.close();
				input.close();
			}
			catch (Exception e) {
			}
		}
	}

}
