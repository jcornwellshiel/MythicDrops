package com.conventnunnery.plugins.MythicDrops.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.material.MaterialData;

import com.conventnunnery.plugins.MythicDrops.MythicDrops;
import com.conventnunnery.plugins.MythicDrops.NumberUtils;
import com.conventnunnery.plugins.MythicDrops.objects.Tier;

public class ItemAPI {
	private final MythicDrops plugin;

	public ItemAPI(MythicDrops plugin) {
		this.plugin = plugin;
	}

	public boolean containsIgnoreCase(Collection<String> collection,
			String string) {
		for (String s : collection) {
			if (s.equalsIgnoreCase(string))
				return true;
		}
		return false;
	}

	public MaterialData getMatDataFromItemType(String itemType) {
		Map<String, List<String>> ids = getPlugin().getPluginSettings()
				.getIDs();
		if (!ids.containsKey(itemType.toLowerCase())) {
			return null;
		}
		List<String> idList = ids.get(itemType.toLowerCase());
		if (idList == null || idList.isEmpty())
			return null;
		String s = idList.get(getPlugin().random.nextInt(idList.size()));
		MaterialData matData = null;
		if (s == null)
			return null;
		if (s.contains(";")) {
			String[] split = s.split(";");
			int id = NumberUtils.getInt(split[0], 0);
			int data = NumberUtils.getInt(split[1], 0);
			if (id == 0)
				return null;
			matData = new MaterialData(id, (byte) data);
		}
		else {
			int id = NumberUtils.getInt(s, 0);
			if (id == 0)
				return null;
			matData = new MaterialData(id);
		}
		return matData;
	}

	public MaterialData getMatDataFromTier(Tier tier) {
		List<String> idList = tier.getItemIDs();
		for (String itemType : tier.getItemTypes()) {
			idList.addAll(getMaterialIDsForItemType(itemType.toLowerCase()));
		}
		String s = idList.get(getPlugin().random.nextInt(idList.size()));
		MaterialData matData = null;
		if (s == null)
			return null;
		if (s.contains(";")) {
			String[] split = s.split(";");
			int id = NumberUtils.getInt(split[0], 0);
			int data = NumberUtils.getInt(split[1], 0);
			if (id == 0)
				return null;
			matData = new MaterialData(id, (byte) data);
		}
		else {
			int id = NumberUtils.getInt(s, 0);
			if (id == 0)
				return null;
			matData = new MaterialData(id);
		}
		return matData;
	}

	public List<String> getMaterialIDsForItemType(String itemType) {
		List<String> materialIds = new ArrayList<String>();
		Map<String, List<String>> map = getPlugin().getPluginSettings()
				.getIDs();
		if (map == null || map.isEmpty())
			return materialIds;
		if (itemType == null)
			return materialIds;
		if (map.containsKey(itemType.toLowerCase())) {
			materialIds = getPlugin().getPluginSettings().getIDs()
					.get(itemType.toLowerCase());
		}
		return materialIds;
	}

	/**
	 * @return the plugin
	 */
	public MythicDrops getPlugin() {
		return plugin;
	}

	public boolean isItemType(String itemType, MaterialData matData) {
		String comb = String.valueOf(matData.getItemTypeId()) + ";"
				+ String.valueOf(matData.getData());
		String comb2;
		if (matData.getData() == (byte) 0) {
			comb2 = String.valueOf(matData.getItemTypeId());
		}
		else {
			comb2 = comb;
		}
		Map<String, List<String>> ids = getPlugin().getPluginSettings()
				.getIDs();
		List<String> list = new ArrayList<String>();
		if (ids.keySet().contains(itemType.toLowerCase())) {
			list = ids.get(itemType.toLowerCase());
		}
		if (containsIgnoreCase(list, comb) || containsIgnoreCase(list, comb2))
			return true;
		return false;
	}

	public String itemTypeFromMatData(MaterialData matData) {
		String comb = String.valueOf(matData.getItemTypeId()) + ";"
				+ String.valueOf(matData.getData());
		String comb2;
		if (matData.getData() == (byte) 0) {
			comb2 = String.valueOf(matData.getItemTypeId());
		}
		else {
			comb2 = comb;
		}
		Map<String, List<String>> ids = getPlugin().getPluginSettings()
				.getIDs();
		for (Entry<String, List<String>> e : ids.entrySet()) {
			if (containsIgnoreCase(e.getValue(), comb)
					|| containsIgnoreCase(e.getValue(), comb2)) {
				return e.getKey();
			}
		}
		return null;
	}
}
