package nichrosia.arcanology.util

import net.devtech.arrp.json.models.JTextures

///** A separate method for creating a silk touch predicate due to the complexity. */
//fun silkTouchPredicate(): JCondition {
//    return predicate("minecraft:match_tool")
//        .parameter("predicate", JsonObject().apply { add("enchantments", jsonArray(
//            JsonObject()
//                .apply { addProperty("enchantment", "minecraft:silk_touch") }
//                .apply { add("levels", jsonObject("min" to 1)) }
//        )) })
//}

///** A loot table generator to make an [OreBlock] drop its raw ore [Item]. */
//fun rawOreLootTable(ore: OreBlock, rawOre: Item) {
//    val blockName = Registry.BLOCK.getId(ore).path
//    val itemName = Registry.ITEM.getId(rawOre).path
//
//    Arcanology.apply {
//        packManager.main.addLootTable(
//            packManager.blockID(blockName),
//            loot("minecraft:block")
//                .pool(pool()
//                    .rolls(1)
//                    .bonus(0)
//                    .entry(entry()
//                        .type("minecraft:alternatives")
//                        .child(entry()
//                            .type("minecraft:item")
//                            .condition(silkTouchPredicate())
//                            .name("$modID:$blockName")
//                        )
//                        .child(entry()
//                            .type("minecraft:item")
//                            .function(function("apply_bonus")
//                                .parameter("enchantment", "minecraft:fortune")
//                                .parameter("formula", "minecraft:ore_drops")
//                            )
//                            .function(function("minecraft:explosion_decay"))
//                            .name("$modID:$itemName")
//                        )
//                    )
//                )
//        )
//    }
//}

/** Renamed function to avoid requiring backticks. */
fun JTextures.variable(name: String, value: String) = `var`(name, value)

/** Utility function for using [Pair]s instead of individual values. */
fun JTextures.variable(entry: Pair<String, String>) = variable(entry.first, entry.second)

/** Utility function to add several variables at once to the texture. */
fun JTextures.variables(vararg entries: Pair<String, String>): JTextures {
    entries.forEach(this::variable)
    return this
}