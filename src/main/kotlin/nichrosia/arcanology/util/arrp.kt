package nichrosia.arcanology.util

import com.google.gson.JsonObject
import net.devtech.arrp.api.RuntimeResourcePack.id
import net.devtech.arrp.json.blockstate.JState.*
import net.devtech.arrp.json.loot.JCondition
import net.devtech.arrp.json.loot.JLootTable.*
import net.devtech.arrp.json.models.JModel.*
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
import net.minecraft.item.MiningToolItem
import net.minecraft.item.PickaxeItem
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.type.content.block.AltarBlock
import nichrosia.arcanology.type.content.item.energy.CircuitItem
import nichrosia.arcanology.type.content.item.energy.WireItem
import nichrosia.arcanology.type.content.item.weapon.OpenCrossbowItem
import net.devtech.arrp.json.blockstate.JState.model as blockModel

fun predicate() = JCondition()

fun silkTouchPredicate(): JCondition {
    return predicate("minecraft:match_tool")
        .parameter("predicate", JsonObject().apply { add("enchantments", jsonArray(
            JsonObject()
                .apply { addProperty("enchantment", "minecraft:silk_touch") }
                .apply { add("levels", jsonObject("min" to 1)) }
        )) })
}

fun normalBlockLootTable(block: Block, item: BlockItem) {
    val blockName = Registry.BLOCK.getId(block).path
    val itemName = Registry.ITEM.getId(item).path

    Arcanology.runtimeResourceManager.main.addLootTable(
        Arcanology.runtimeResourceManager.blockID(blockName),
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

fun rawOreLootTable(ore: OreBlock, rawOre: Item) {
    val blockName = Registry.BLOCK.getId(ore).path
    val itemName = Registry.ITEM.getId(rawOre).path

    Arcanology.runtimeResourceManager.main.addLootTable(
        Arcanology.runtimeResourceManager.blockID(blockName),
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

fun normalBlockstate(block: Block) {
    val blockName = Registry.BLOCK.getId(block).path

    Arcanology.runtimeResourceManager.main.addBlockState(
        state(variant(blockModel("${Arcanology.modID}:block/$blockName"))),
        id("${Arcanology.modID}:$blockName")
    )
}

fun normalBlockModel(block: Block) {
    val name = Registry.BLOCK.getId(block).path

    Arcanology.runtimeResourceManager.main.addModel(
        model("block/cube_all")
            .textures(textures().`var`("all", "${Arcanology.modID}:block/$name")),
        Arcanology.runtimeResourceManager.blockModelID(name)
    )
}

fun blockItemModel(item: BlockItem) {
    val itemName = Registry.ITEM.getId(item).path
    val blockName = Registry.BLOCK.getId(item.block).path

    Arcanology.runtimeResourceManager.main.addModel(
        model("${Arcanology.modID}:block/$blockName"),
        Arcanology.runtimeResourceManager.itemModelID(itemName)
    )
}

fun normalItemModel(item: Item) {
    val name = Registry.ITEM.getId(item).path

    Arcanology.runtimeResourceManager.main.addModel(
        model("item/generated")
            .textures(textures().layer0("${Arcanology.modID}:item/$name")),
        Arcanology.runtimeResourceManager.itemModelID(name)
    )
}

fun crossbowItemModel(item: OpenCrossbowItem) {
    val name = Registry.ITEM.getId(item).path

    Arcanology.runtimeResourceManager.main.addModel(
        model("item/generated")
            .textures(
                textures().layer0("${Arcanology.modID}:item/${name}_standby")
            ).display(
                display()
                    .setThirdperson_righthand(position()
                        .rotation(-90f, 0f, -60f)
                        .translation(2f, 0.1f, -3f)
                        .scale(0.9f, 0.9f, 0.9f))
                    .setThirdperson_lefthand(position()
                        .rotation(-90f, 0f, 30f)
                        .translation(2f, 0.1f, -3f)
                        .scale(0.9f, 0.9f, 0.9f))
                    .setFirstperson_righthand(position()
                        .rotation(-90f, 0f, -55f)
                        .translation(1.13f, -1f, 1.13f)
                        .scale(0.68f, 0.68f, 0.68f))
                    .setFirstperson_lefthand(position()
                        .rotation(-90f, 0f, 35f)
                        .translation(1.13f, -1f, 1.13f)
                        .scale(0.68f, 0.68f, 0.68f))
            ).addOverride(override(
                predicate().parameter("pulling", 1),
                Arcanology.runtimeResourceManager.itemModelID("${name}_pulling_0")
            )).addOverride(override(
                predicate().parameter("pulling", 1).parameter("pull", 0.58),
                Arcanology.runtimeResourceManager.itemModelID("${name}_pulling_1")
            )).addOverride(override(
                predicate().parameter("pulling", 1).parameter("pull", 0.99),
                Arcanology.runtimeResourceManager.itemModelID("${name}_pulling_2")
            )).addOverride(override(
                predicate().parameter("charged", 1),
                Arcanology.runtimeResourceManager.itemModelID("${name}_arrow")
            )).addOverride(override(
                predicate().parameter("charged", 1).parameter("firework", 1),
                Arcanology.runtimeResourceManager.itemModelID("${name}_firework")
            )),
        Arcanology.runtimeResourceManager.itemModelID(name)
    )

    for (i in 0..2) {
        Arcanology.runtimeResourceManager.main.addModel(
            model("${Arcanology.modID}:item/${name}")
                .textures(textures().layer0("${Arcanology.modID}:item/${name}_pulling_$i")),
            Arcanology.runtimeResourceManager.itemModelID("${name}_pulling_$i")
        )
    }

    Arcanology.runtimeResourceManager.main.addModel(
        model("${Arcanology.modID}:item/${name}")
            .textures(textures().layer0("${Arcanology.modID}:item/${name}_arrow")),
        Arcanology.runtimeResourceManager.itemModelID("${name}_arrow")
    )

    Arcanology.runtimeResourceManager.main.addModel(
        model("${Arcanology.modID}:item/${name}")
            .textures(textures().layer0("${Arcanology.modID}:item/${name}_firework")),
        Arcanology.runtimeResourceManager.itemModelID("${name}_firework")
    )
}

fun handheldItemModel(item: MiningToolItem) {
    val name = Registry.ITEM.getId(item).path

    Arcanology.runtimeResourceManager.main.addModel(
        model("item/handheld")
            .textures(textures().layer0("${Arcanology.modID}:item/${name}")),
        Arcanology.runtimeResourceManager.itemModelID(name)
    )
}

fun altarBlockModel(block: AltarBlock) {
    val name = Registry.BLOCK.getId(block).path

    Arcanology.runtimeResourceManager.main.addModel(
        model("minecraft:block/cube")
            .textures(
                textures()
                    .particle("${Arcanology.modID}:block/altar_top")
                    .`var`("east", "${Arcanology.modID}:block/altar_side")
                    .`var`("west", "${Arcanology.modID}:block/altar_side")
                    .`var`("north", "${Arcanology.modID}:block/altar_side")
                    .`var`("south", "${Arcanology.modID}:block/altar_side")
                    .`var`("down", "${Arcanology.modID}:block/altar_bottom")
                    .`var`("up", "${Arcanology.modID}:block/altar_top")
            ),
        Arcanology.runtimeResourceManager.blockModelID(name)
    )
}

fun wireRecipe(ingot: Item, wire: WireItem) {
    val wireID = Registry.ITEM.getId(wire)
    val wireName = wireID.path

    val ingotID = Registry.ITEM.getId(ingot)
    val ingotName = ingotID.path

    Arcanology.runtimeResourceManager.main.addRecipe(
        id("${Arcanology.modID}:$wireName"),
        shapeless(
            ingredients()
                .add(ingredient().tag("c:wire_cutters"))
                .add(ingredient().tag("c:${ingotName}s")),
            result("${Arcanology.modID}:$wireName")
        )
    )
}

fun circuitRecipe(insulator: Item, wire: WireItem, circuitItem: CircuitItem) {
    val circuitID = Registry.ITEM.getId(circuitItem)

    val wireID = Registry.ITEM.getId(wire)
    val wireName = wireID.path

    Arcanology.runtimeResourceManager.main.addRecipe(
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

fun pickaxeRecipe(pickaxeItem: PickaxeItem, ingot: Item, stick: Item) {
    val pickaxeID = Registry.ITEM.getId(pickaxeItem)
    val ingotID = Registry.ITEM.getId(ingot)

    Arcanology.runtimeResourceManager.main.addRecipe(
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