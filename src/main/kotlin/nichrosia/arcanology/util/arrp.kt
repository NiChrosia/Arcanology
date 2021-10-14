package nichrosia.arcanology.util

import com.google.gson.JsonObject
import net.devtech.arrp.api.RuntimeResourcePack.id
import net.devtech.arrp.json.loot.JCondition
import net.devtech.arrp.json.loot.JLootTable.*
import net.devtech.arrp.json.models.JTextures
import net.devtech.arrp.json.recipe.JIngredient.ingredient
import net.devtech.arrp.json.recipe.JIngredients.ingredients
import net.devtech.arrp.json.recipe.JKeys.keys
import net.devtech.arrp.json.recipe.JPattern.pattern
import net.devtech.arrp.json.recipe.JRecipe.*
import net.devtech.arrp.json.recipe.JResult.result
import net.minecraft.block.Block
import net.minecraft.block.OreBlock
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.PickaxeItem
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.type.content.item.energy.CircuitItem
import nichrosia.arcanology.type.content.item.energy.WireItem

/** A separate method for creating a silk touch predicate due to the complexity. */
fun silkTouchPredicate(): JCondition {
    return predicate("minecraft:match_tool")
        .parameter("predicate", JsonObject().apply { add("enchantments", jsonArray(
            JsonObject()
                .apply { addProperty("enchantment", "minecraft:silk_touch") }
                .apply { add("levels", jsonObject("min" to 1)) }
        )) })
}

/** A loot table generator for a [Block] to drop a [BlockItem]. */
fun normalBlockLootTable(block: Block, item: BlockItem) {
    val blockName = Registry.BLOCK.getId(block).path
    val itemName = Registry.ITEM.getId(item).path

    Arcanology.packManager.main.addLootTable(
        Arcanology.packManager.blockID(blockName),
        loot("minecraft:block")
            .pool(pool()
                .rolls(1)
                .entry(entry()
                    .type("minecraft:item")
                    .name("${Arcanology.modID}:$itemName")
                )
                .condition(predicate("minecraft:survives_explosion"))
            )
    )
}

/** A loot table generator to make an [OreBlock] drop its raw ore [Item]. */
fun rawOreLootTable(ore: OreBlock, rawOre: Item) {
    val blockName = Registry.BLOCK.getId(ore).path
    val itemName = Registry.ITEM.getId(rawOre).path

    Arcanology.packManager.main.addLootTable(
        Arcanology.packManager.blockID(blockName),
        loot("minecraft:block")
            .pool(pool()
                .rolls(1)
                .bonus(0)
                .entry(entry()
                    .type("minecraft:alternatives")
                    .child(entry()
                        .type("minecraft:item")
                        .condition(silkTouchPredicate())
                        .name("${Arcanology.modID}:$blockName")
                    )
                    .child(entry()
                        .type("minecraft:item")
                        .function(function("apply_bonus")
                            .parameter("enchantment", "minecraft:fortune")
                            .parameter("formula", "minecraft:ore_drops")
                        )
                        .function(function("minecraft:explosion_decay"))
                        .name("${Arcanology.modID}:$itemName")
                    )
                )
            )
    )
}

/** A recipe generator to generate a wire recipe from wire cutters & ingots. */
fun wireRecipe(ingot: Item, wire: WireItem) {
    val wireID = Registry.ITEM.getId(wire)
    val wireName = wireID.path

    val ingotID = Registry.ITEM.getId(ingot)
    val ingotName = ingotID.path

    Arcanology.packManager.main.addRecipe(
        id("${Arcanology.modID}:$wireName"),
        shapeless(
            ingredients()
                .add(ingredient().tag("c:wire_cutters"))
                .add(ingredient().tag("c:${ingotName}s")),
            result("${Arcanology.modID}:$wireName")
        )
    )
}

/** A recipe generator to generate a circuit recipe from insulators & wires. */
fun circuitRecipe(insulator: Item, wire: WireItem, circuitItem: CircuitItem) {
    val circuitID = Registry.ITEM.getId(circuitItem)

    val wireID = Registry.ITEM.getId(wire)
    val wireName = wireID.path

    Arcanology.packManager.main.addRecipe(
        id("$circuitID"),
        shaped(
            pattern()
                .row1(" GW")
                .row2("GWG")
                .row3("WG "),
            keys()
                .key("W", ingredient().tag("c:${wireName}s"))
                .key("G", ingredient().item(insulator)),
            result("$circuitID")
        )
    )
}

/** A recipe generator to generate a pickaxe recipe from sticks and ingots. */
fun pickaxeRecipe(pickaxeItem: PickaxeItem, ingot: Item, stick: Item) {
    val pickaxeID = Registry.ITEM.getId(pickaxeItem)
    val ingotID = Registry.ITEM.getId(ingot)

    Arcanology.packManager.main.addRecipe(
        id("$pickaxeID"),
        shaped(
            pattern()
                .row1("III")
                .row2(" S ")
                .row3(" S "),
            keys()
                .key("I", ingredient().tag("c:${ingotID.path}s"))
                .key("S", ingredient().item(stick)),
            result("$pickaxeID")
        )
    )
}

/** Renamed function to avoid requiring backticks. */
fun JTextures.variable(name: String, value: String) = `var`(name, value)

/** Utility function for using [Pair]s instead of individual values. */
fun JTextures.variable(entry: Pair<String, String>) = variable(entry.first, entry.second)

/** Utility function to add several variables at once to the texture. */
fun JTextures.variables(vararg entries: Pair<String, String>): JTextures {
    entries.forEach(this::variable)
    return this
}