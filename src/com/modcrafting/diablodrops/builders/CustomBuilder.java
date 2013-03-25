/*
 * Copyright (c) 2013. ToppleTheNun
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

/*
 * Originally created by deathmarine
 * Modified by Nunnery on March 10, 2013
 */

package com.modcrafting.diablodrops.builders;

import com.conventnunnery.plugins.MythicDrops.MythicDrops;
import com.conventnunnery.plugins.MythicDrops.configuration.ConfigurationManager.ConfigurationFile;
import com.conventnunnery.plugins.MythicDrops.objects.CustomItem;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.material.MaterialData;

import java.util.HashMap;
import java.util.Map;

public class CustomBuilder {
	private final MythicDrops plugin;

	public CustomBuilder(MythicDrops plugin) {
		this.plugin = plugin;
	}

	public void build() {
		getPlugin().getDropAPI().getCustomItems().clear();
		FileConfiguration fc = getPlugin().getConfigurationManager()
				.getConfiguration(ConfigurationFile.CUSTOM_ITEM);
		for (String s : fc.getKeys(false)) {
			if (!fc.isConfigurationSection(s))
				continue;
			ConfigurationSection cs = fc.getConfigurationSection(s);
			String displayName = cs.getString("displayName");
			if (displayName == null) {
				displayName = s;
			}
			Map<Enchantment, Integer> map = new HashMap<Enchantment, Integer>();
			if (cs.isConfigurationSection("enchantments")) {
				ConfigurationSection enchCS = cs
						.getConfigurationSection("enchantments");
				for (String s2 : enchCS.getKeys(false)) {
					Enchantment ench = null;
					for (Enchantment ec : Enchantment.values()) {
						if (ec.getName().equalsIgnoreCase(s2)) {
							ench = ec;
							break;
						}
					}
					if (ench == null)
						continue;
					int level = enchCS.getInt(s2);
					map.put(ench, level);
				}
			}
			CustomItem ci = new CustomItem(s, displayName, cs.getStringList("lore"), map,
					new MaterialData(cs.getInt("materialID"), (byte) cs.getInt("materialData")),
					cs.getDouble("chance"));
			getPlugin()
					.getDropAPI()
					.getCustomItems()
					.add(ci);
		}
	}

	/**
	 * @return the plugin
	 */
	public MythicDrops getPlugin() {
		return plugin;
	}
}
