/**
 * 
 */
package com.someguyssoftware.gottschcore.loot;

import java.util.Collection;
import java.util.Random;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.someguyssoftware.gottschcore.loot.conditions.LootCondition;
import com.someguyssoftware.gottschcore.loot.conditions.LootConditionManager;
import com.someguyssoftware.gottschcore.loot.functions.LootFunction;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

/**
 * @author Mark Gottschling on Nov 6, 2018
 *
 */
public class LootEntryItem extends LootEntry {
	protected final Item item;
	protected final LootFunction[] functions;

	/**
	 * 
	 * @param itemIn
	 * @param weightIn
	 * @param qualityIn
	 * @param functionsIn
	 * @param conditionsIn
	 * @param entryName
	 */
	public LootEntryItem(Item itemIn, int weightIn, int qualityIn, LootFunction[] functionsIn,
			LootCondition[] conditionsIn, String entryName) {
		super(weightIn, qualityIn, conditionsIn, entryName);
		this.item = itemIn;
		this.functions = functionsIn;
	}

	@Override
	public void addLoot(Collection<ItemStack> stacks, Random rand, LootContext context) {
		ItemStack itemstack = new ItemStack(this.item);

		for (LootFunction lootfunction : this.functions) {
			if (LootConditionManager.testAllConditions(lootfunction.getConditions(), rand, context)) {
				itemstack = lootfunction.apply(itemstack, rand, context);
			}
		}

		if (!itemstack.isEmpty()) {
			if (itemstack.getCount() < this.item.getItemStackLimit(itemstack)) {
				stacks.add(itemstack);
			} else {
				int i = itemstack.getCount();

				while (i > 0) {
					ItemStack itemstack1 = itemstack.copy();
					itemstack1.setCount(Math.min(itemstack.getMaxStackSize(), i));
					i -= itemstack1.getCount();
					stacks.add(itemstack1);
				}
			}
		}
	}

	/**
	 * 
	 */
	protected void serialize(JsonObject json, JsonSerializationContext context) {
		if (this.functions != null && this.functions.length > 0) {
			json.add("functions", context.serialize(this.functions));
		}

		ResourceLocation resourcelocation = Registry.ITEM.getKey(this.item);
		if (resourcelocation == null) {
			throw new IllegalArgumentException("Can't serialize unknown item " + this.item);
		} else {
			json.addProperty("name", resourcelocation.toString());
		}
	}

	/**
	 * 
	 * @param object
	 * @param deserializationContext
	 * @param weightIn
	 * @param qualityIn
	 * @param conditionsIn
	 * @return
	 */
	public static LootEntryItem deserialize(JsonObject object, JsonDeserializationContext deserializationContext,
			int weightIn, int qualityIn, LootCondition[] conditionsIn) {
		String name = LootTableManager.readLootEntryName(object, "item");
		Item item = JSONUtils.getItem(object, "name");
		LootFunction[] alootfunction;

		if (object.has("functions")) {
			alootfunction = (LootFunction[]) JSONUtils.deserializeClass(object, "functions", deserializationContext,
					LootFunction[].class);
		} else {
			alootfunction = new LootFunction[0];
		}

		return new LootEntryItem(item, weightIn, qualityIn, alootfunction, conditionsIn, name);
	}
}
