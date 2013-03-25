/*
 * Copyright (c) 2013. ToppleTheNun
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.conventnunnery.plugins.MythicDrops.builders;

import com.conventnunnery.plugins.MythicDrops.MythicDrops;
import com.conventnunnery.plugins.MythicDrops.api.EffectAPI;
import com.conventnunnery.plugins.MythicDrops.configuration.ConfigurationManager;
import com.conventnunnery.plugins.MythicDrops.objects.ItemEffect;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class EffectBuilder {

	private final MythicDrops plugin;

	public EffectBuilder(MythicDrops plugin) {
		this.plugin = plugin;
	}

	public void build() {
		buildToolItemEffects();
		buildArmorItemEffects();
	}

	private void buildToolItemEffects() {
		FileConfiguration fc = getPlugin().getConfigurationManager()
				.getConfiguration(ConfigurationManager.ConfigurationFile.EFFECTS);
		if (fc.isConfigurationSection("tools")) {
			ConfigurationSection cs = fc.getConfigurationSection("tools");
			for (String effectName : cs.getKeys(false)) {
				if (!cs.isConfigurationSection(effectName))
					continue;
				ConfigurationSection effectCS = cs.getConfigurationSection(effectName);
				EffectAPI.BaseEffectType bet = EffectAPI.BaseEffectType.fromName(effectCS.getString("type"));
				if (bet == null)
					bet = EffectAPI.BaseEffectType.NONE;
				EffectAPI.BaseEffectTarget baseEffectTarget =
						effectCS.getString("target").equalsIgnoreCase("SELF") ? EffectAPI.BaseEffectTarget.OTHER :
								EffectAPI.BaseEffectTarget.SELF;
				getPlugin().getEffectAPI().getItemEffects()
						.add(new ItemEffect(effectName, effectCS.getBoolean("enabled"), effectCS.getInt("intensity"),
								effectCS.getLong("duration"),
								ItemEffect.ItemEffectType.TOOL, bet, baseEffectTarget));
			}
		}
	}

	private void buildArmorItemEffects() {
		FileConfiguration fc = getPlugin().getConfigurationManager()
				.getConfiguration(ConfigurationManager.ConfigurationFile.EFFECTS);
		if (fc.isConfigurationSection("armor")) {
			ConfigurationSection cs = fc.getConfigurationSection("armor");
			for (String effectName : cs.getKeys(false)) {
				if (!cs.isConfigurationSection(effectName))
					continue;
				ConfigurationSection effectCS = cs.getConfigurationSection(effectName);
				EffectAPI.BaseEffectType bet = EffectAPI.BaseEffectType.fromName(effectCS.getString("type"));
				if (bet == null)
					continue;
				EffectAPI.BaseEffectTarget baseEffectTarget =
						effectCS.getString("target").equalsIgnoreCase("SELF") ? EffectAPI.BaseEffectTarget.OTHER :
								EffectAPI.BaseEffectTarget.SELF;
				getPlugin().getEffectAPI().getItemEffects()
						.add(new ItemEffect(effectName, effectCS.getBoolean("enabled"), effectCS.getInt("intensity"),
								effectCS.getLong("duration"),
								ItemEffect.ItemEffectType.TOOL, bet, baseEffectTarget));
			}
		}
	}

	public MythicDrops getPlugin() {
		return plugin;
	}
}