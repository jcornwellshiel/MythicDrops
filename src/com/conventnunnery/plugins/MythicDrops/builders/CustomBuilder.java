package com.conventnunnery.plugins.MythicDrops.builders;

import com.conventnunnery.plugins.MythicDrops.MythicDrops;

public class CustomBuilder {
	private final MythicDrops plugin;

	public CustomBuilder(MythicDrops plugin) {
		this.plugin = plugin;
	}

	/**
	 * @return the plugin
	 */
	public MythicDrops getPlugin() {
		return plugin;
	}
}
