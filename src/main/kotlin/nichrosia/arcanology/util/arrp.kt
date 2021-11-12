package nichrosia.arcanology.util

import net.devtech.arrp.api.RuntimeResourcePack
import net.devtech.arrp.json.blockstate.JState
import net.devtech.arrp.json.models.JModel
import net.devtech.arrp.json.models.JTextures
import nichrosia.common.identity.ID

///** A separate method for creating a silk touch predicate due to the complexity. */
//fun silkTouchPredicate(): JCondition {
//    return predicate("minecraft:match_tool")
//        .parameter("predicate", JsonObject().apply {
//            add("enchantments", JsonArray().apply {
//                add(
//                    JsonObject()
//                        .apply { addProperty("enchantment", "minecraft:silk_touch") }
//                        .apply { add("levels", JsonObject().apply {
//                            addProperty("min",  1)
//                        })}
//                )
//            })
//        })
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

fun RuntimeResourcePack.addModel(ID: ID, model: JModel): ByteArray {
    return addModel(model, ID)
}

fun RuntimeResourcePack.addBlockstate(ID: ID, blockstate: JState): ByteArray {
    return addBlockState(blockstate, ID)
}

fun JTextures.addProperty(name: String, value: String) = `var`(name, value)

fun JTextures.addProperty(entry: Pair<String, String>) = addProperty(entry.first, entry.second)

fun JTextures.addProperties(vararg entries: Pair<String, String>): JTextures {
    return apply {
        entries.forEach(this::addProperty)
    }
}

fun JTextures(vararg properties: Pair<String, String>): JTextures {
    return JTextures().apply {
        properties.forEach(this::addProperty)
    }
}

fun JModel(parent: String): JModel {
    return JModel.model(parent)
}