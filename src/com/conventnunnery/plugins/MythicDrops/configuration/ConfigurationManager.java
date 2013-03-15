package com.conventnunnery.plugins.MythicDrops.configuration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class ConfigurationManager {

	public enum ConfigurationFile {

		CONFIG("config.yml"), ADVANCED_CONFIG("advanced_config.yml"), LANGUAGE(
				"language.yml"), TIER("tier.yml"), CUSTOM_ITEM(
				"custom_item.yml");

		public final String filename;

		private ConfigurationFile(String path) {
			this.filename = path;
		}

	}

	private final HashMap<ConfigurationFile, CommentedYamlConfiguration> configurations;

	private final Plugin plugin;

	public ConfigurationManager(Plugin plugin) {
		this.plugin = plugin;

		configurations = new HashMap<ConfigurationFile, CommentedYamlConfiguration>();

		loadConfig();
	}

	private void createConfig(ConfigurationFile config) {

		switch (config) {

			case CONFIG:
				CommentedYamlConfiguration basic_config = new CommentedYamlConfiguration(
						new File(plugin.getDataFolder(), config.filename));
				saveDefaults(basic_config, config);
				configurations.put(config, basic_config);
				break;

			case ADVANCED_CONFIG:
				CommentedYamlConfiguration advanced_config = new CommentedYamlConfiguration(
						new File(plugin.getDataFolder(), config.filename));
				saveDefaults(advanced_config, config);
				configurations.put(config, advanced_config);
				break;

			case TIER:
				CommentedYamlConfiguration tier = new CommentedYamlConfiguration(
						new File(plugin.getDataFolder(), config.filename));
				saveDefaults(tier, config);
				configurations.put(config, tier);
				break;

			case LANGUAGE:
				CommentedYamlConfiguration language = new CommentedYamlConfiguration(
						new File(plugin.getDataFolder(), config.filename));
				saveDefaults(language, config);
				configurations.put(config, language);
				break;

			case CUSTOM_ITEM:
				CommentedYamlConfiguration custom_item = new CommentedYamlConfiguration(
						new File(plugin.getDataFolder(), config.filename));
				saveDefaults(custom_item, config);
				configurations.put(config, custom_item);
				break;

			default:
				break;
		}

	}

	public CommentedYamlConfiguration getConfiguration(ConfigurationFile file) {
		return configurations.get(file);
	}

	/**
	 * Loads the plugin's configuration files
	 */
	public void loadConfig() {
		for (ConfigurationFile file : ConfigurationFile.values()) {
			File confFile = new File(plugin.getDataFolder(), file.filename);
			if (confFile.exists()) {
				CommentedYamlConfiguration config = new CommentedYamlConfiguration(
						confFile);
				config.load();
				if (!plugin.getDescription().getVersion()
						.equals(config.getString("version"))) {
					config.set("version", plugin.getDescription().getVersion());
					saveDefaults(config, file);
				}
				configurations.put(file, config);
			}
			else {
				File parentFile = confFile.getParentFile();
				if (!parentFile.exists()) {
					parentFile.mkdirs();
				}
				createConfig(file);
			}
		}
	}

	/**
	 * Saves the plugin's configs
	 */
	public void saveConfig() {
		for (ConfigurationFile file : ConfigurationFile.values()) {
			if (configurations.containsKey(file)) {
				try {
					configurations.get(file).save(
							new File(plugin.getDataFolder(), file.filename));
				}
				catch (IOException e) {
					plugin.getLogger().log(Level.WARNING,
							"Could not save " + file.filename, e);
				}
			}
		}
	}

	private void saveDefaults(CommentedYamlConfiguration config,
			ConfigurationFile file) {
		config.setDefaults(YamlConfiguration.loadConfiguration(plugin
				.getResource(file.filename)));
		config.options().copyDefaults(true);
		config.save();
	}

}
