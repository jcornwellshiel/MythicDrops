package com.conventnunnery.plugins.MythicDrops.objects;

import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;

public class Tier {
	private final String name;
	private final String displayName;
	private final ChatColor color;
	private final ChatColor identifier;
	private final int maxNumberOfRandomEnchantments;
	private final int maxLevelOfRandomEnchantments;
	private final Map<Enchantment, Integer> automaticEnchantments;
	private final Map<Enchantment, Integer> naturalEnchantments;
	private final List<Enchantment> allowedEnchantments;
	private final List<String> itemTypes;
	private final List<String> itemIDs;
	private final double chanceToBeGiven;
	private final float chanceToDrop;

	public Tier(String name, String displayName, ChatColor color,
			ChatColor identifier, int maxNumberOfRandomEnchantments,
			int maxLevelOfRandomEnchantments,
			Map<Enchantment, Integer> automaticEnchantments,
			Map<Enchantment, Integer> naturalEnchantments,
			List<Enchantment> allowedEnchantments, List<String> itemTypes,
			List<String> itemIDs, double chanceToBeGiven, float chanceToDrop) {
		this.name = name;
		this.displayName = displayName;
		this.color = color;
		this.identifier = identifier;
		this.maxNumberOfRandomEnchantments = maxNumberOfRandomEnchantments;
		this.maxLevelOfRandomEnchantments = maxLevelOfRandomEnchantments;
		this.automaticEnchantments = automaticEnchantments;
		this.naturalEnchantments = naturalEnchantments;
		this.allowedEnchantments = allowedEnchantments;
		this.itemTypes = itemTypes;
		this.itemIDs = itemIDs;
		this.chanceToBeGiven = chanceToBeGiven;
		this.chanceToDrop = chanceToDrop;
	}

	/**
	 * @return the allowedEnchantments
	 */
	public List<Enchantment> getAllowedEnchantments() {
		return allowedEnchantments;
	}

	/**
	 * @return the automaticEnchantments
	 */
	public Map<Enchantment, Integer> getAutomaticEnchantments() {
		return automaticEnchantments;
	}

	/**
	 * @return the chanceToBeGiven
	 */
	public double getChanceToBeGiven() {
		return chanceToBeGiven;
	}

	/**
	 * @return the chanceToDrop
	 */
	public float getChanceToDrop() {
		return chanceToDrop;
	}

	/**
	 * @return the color
	 */
	public ChatColor getColor() {
		return color;
	}

	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @return the identifier
	 */
	public ChatColor getIdentifier() {
		return identifier;
	}

	/**
	 * @return the itemIDs
	 */
	public List<String> getItemIDs() {
		return itemIDs;
	}

	/**
	 * @return the itemTypes
	 */
	public List<String> getItemTypes() {
		return itemTypes;
	}

	/**
	 * @return the maxLevelOfRandomEnchantments
	 */
	public int getMaxLevelOfRandomEnchantments() {
		return maxLevelOfRandomEnchantments;
	}

	/**
	 * @return the maxNumberOfRandomEnchantments
	 */
	public int getMaxNumberOfRandomEnchantments() {
		return maxNumberOfRandomEnchantments;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the naturalEnchantments
	 */
	public Map<Enchantment, Integer> getNaturalEnchantments() {
		return naturalEnchantments;
	}

}
