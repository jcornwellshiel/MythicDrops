package de.funkyclan.mc.RepairRecipe;

import com.conventnunnery.plugins.MythicDrops.MythicDrops;
import com.conventnunnery.plugins.MythicDrops.configuration.ConfigurationManager;
import de.funkyclan.mc.RepairRecipe.Listener.CraftingListener;
import de.funkyclan.mc.RepairRecipe.Recipe.ShapelessRepairRecipe;
import net.minecraft.server.v1_5_R2.Packet103SetSlot;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_5_R2.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_5_R2.inventory.CraftItemStack;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class RepairRecipe {

	private final MythicDrops plugin;
	private Set<ShapelessRepairRecipe> repairRecipes;

	public RepairRecipe(MythicDrops plugin) {
		this.plugin = plugin;

		getPlugin().getServer().getPluginManager().registerEvents(new CraftingListener(this), getPlugin());

		repairRecipes = new HashSet<ShapelessRepairRecipe>();
		addRecipes();

		if (repairRecipes.size() == 0) {
			return;
		}
	}

	public MythicDrops getPlugin() {
		return plugin;
	}

	public Set<ShapelessRepairRecipe> getRecipes() {
		return repairRecipes;
	}

	public ShapelessRepairRecipe getRepairRecipeFor(ItemStack itemStack) {
		return getRepairRecipeFor(itemStack.getType());
	}

	public ShapelessRepairRecipe getRepairRecipeFor(Material item) {
		for (ShapelessRepairRecipe recipe : repairRecipes) {
			if (recipe.getResult().getType().equals(item)) {
				return recipe;
			}
		}
		return null;
	}

	public void updateSlotInventory(HumanEntity player, ItemStack item, int index) {
		if (player instanceof CraftPlayer) {
			CraftPlayer craftPlayer = (CraftPlayer) player;
			if (craftPlayer.getHandle().activeContainer != null) {
				Packet103SetSlot packet = new Packet103SetSlot();
				packet.a = craftPlayer.getHandle().activeContainer.windowId;
				packet.b = index;
				packet.c = CraftItemStack.asNMSCopy(item);

				craftPlayer.getHandle().playerConnection.sendPacket(packet);
			}
		}
	}

	private void addRecipes() {
		FileConfiguration fc = getPlugin().getConfigurationManager().getConfiguration(
				ConfigurationManager.ConfigurationFile.REPAIR_ITEMS);
		for (String key : fc.getKeys(false)) {
			if (!fc.isConfigurationSection(key))
				continue;
			ConfigurationSection cs = fc.getConfigurationSection(key);
			Material item = Material.matchMaterial(key);
			if (item == null) {
				continue;
			}

			Material baseItem = Material.matchMaterial(cs.getString("base_item"));
			if (baseItem == null) {
				continue;
			}

			int baseAmount = cs.getInt("base_amount");

			ShapelessRepairRecipe recipe = new ShapelessRepairRecipe(item, baseItem, baseAmount, this);

			repairRecipes.add(recipe);

			getPlugin().getServer().addRecipe(recipe);
		}
	}

}
