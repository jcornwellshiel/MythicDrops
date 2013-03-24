package com.conventnunnery.plugins.MythicDrops.builders;

import com.conventnunnery.plugins.MythicDrops.MythicDrops;
import com.conventnunnery.plugins.MythicDrops.configuration.ConfigurationManager.ConfigurationFile;
import com.conventnunnery.plugins.MythicDrops.objects.CustomItem;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
			String name = s;
			String displayName = cs.getString("displayName");
			List<String> lore = new ArrayList<String>();
			lore = cs.getStringList("lore");
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
			int matId = cs.getInt("materialId");
			byte matData = (byte) cs.getInt("materialData");
			double chance = cs.getDouble("chance");
			getPlugin()
					.getDropAPI()
					.getCustomItems()
					.add(new CustomItem(name, displayName, lore, map,
							new MaterialData(matId, matData), chance));
		}
	}

	/**
	 * @return the plugin
	 */
	public MythicDrops getPlugin() {
		return plugin;
	}
}
