/*
 * Copyright (c) 2013. ToppleTheNun
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.modcrafting.diablodrops.name;

/*
 * Originally created by deathmarine
 * Modified by Nunnery on March 10, 2013
 */


import com.conventnunnery.plugins.MythicDrops.MythicDrops;

import java.io.*;
import java.util.List;

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
		} catch (Exception e) {
		}
	}

	/**
	 * Creates a file with given name
	 *
	 * @param name Name of the file to write
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
			} catch (Exception e) {
			}
		}
	}

}
