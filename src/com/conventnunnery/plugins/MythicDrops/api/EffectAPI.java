/*
 * Copyright (c) 2013. ToppleTheNun
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.conventnunnery.plugins.MythicDrops.api;

import com.conventnunnery.plugins.MythicDrops.MythicDrops;
import com.conventnunnery.plugins.MythicDrops.objects.ItemEffect;

import java.util.ArrayList;
import java.util.List;

public class EffectAPI {

	private final MythicDrops plugin;
	private final List<ItemEffect> itemEffects;

	public EffectAPI(MythicDrops plugin) {
		this.plugin = plugin;
		itemEffects = new ArrayList<ItemEffect>();
	}

	public List<ItemEffect> getItemEffects() {
		return itemEffects;
	}

	public MythicDrops getPlugin() {
		return plugin;
	}

	public void debugItemEffects() {
		List<String> iEffects = new ArrayList<String>();
		for (ItemEffect ie : itemEffects) {
			if (ie.getItemEffectType().equals(ItemEffect.ItemEffectType.TOOL))
				iEffects.add(ie.getName());
		}
		getPlugin().getDebug().debug(
				"Loaded tool effects: "
						+ iEffects.toString().replace("[", "")
						.replace("]", ""));
		iEffects.clear();
		for (ItemEffect ie : itemEffects) {
			if (ie.getItemEffectType().equals(ItemEffect.ItemEffectType.ARMOR))
				iEffects.add(ie.getName());
		}
		getPlugin().getDebug().debug(
				"Loaded armor effects: "
						+ iEffects.toString().replace("[", "")
						.replace("]", ""));
	}

	public enum BaseEffectTarget {
		SELF, OTHER
	}

	public enum BaseEffectType {
		FIRE("FIRE"), TELEPORT("TELEPORT"), FLIGHT("FLIGHT"), INVISIBILITY("INVISIBILTY"), NONE("NONE");
		private final String name;

		private BaseEffectType(String name) {
			this.name = name;
		}

		public static BaseEffectType fromName(String name) {
			for (BaseEffectType bet : BaseEffectType.values()) {
				if (bet.getName().equalsIgnoreCase(name))
					return bet;
			}
			return null;
		}

		public String getName() {
			return name;
		}
	}

}
