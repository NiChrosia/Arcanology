package nichrosia.arcanology.data

import net.devtech.arrp.api.RRPCallback
import net.devtech.arrp.api.RuntimeResourcePack.id
import net.devtech.arrp.json.blockstate.JState.*
import net.devtech.arrp.json.lang.JLang
import net.devtech.arrp.json.lang.JLang.lang
import net.devtech.arrp.json.loot.JCondition
import net.devtech.arrp.json.loot.JLootTable.*
import net.devtech.arrp.json.models.JModel.*
import net.devtech.arrp.json.recipe.JIngredient.ingredient
import net.devtech.arrp.json.recipe.JIngredients.ingredients
import net.devtech.arrp.json.recipe.JKeys.keys
import net.devtech.arrp.json.recipe.JPattern.pattern
import net.devtech.arrp.json.recipe.JRecipe.*
import net.devtech.arrp.json.recipe.JResult.result
import net.devtech.arrp.json.tags.JTag
import net.devtech.arrp.json.tags.JTag.tag
import net.minecraft.block.Block
import net.minecraft.block.OreBlock
import net.minecraft.item.*
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.Arcanology.commonResourcePack
import nichrosia.arcanology.Arcanology.modID
import nichrosia.arcanology.Arcanology.resourcePack
import nichrosia.arcanology.content.type.Content
import nichrosia.arcanology.ctype.block.AltarBlock
import nichrosia.arcanology.ctype.item.energy.CircuitItem
import nichrosia.arcanology.ctype.item.energy.WireItem
import nichrosia.arcanology.ctype.item.weapon.OpenCrossbowItem
import net.devtech.arrp.json.blockstate.JState.model as blockModel

object DataGenerator : Content {
    private val tags = mutableMapOf<Identifier, MutableList<Identifier>>()
    val lang: JLang = lang()

    private fun predicate() = JCondition()

    private fun blockID(name: String) = Identifier(modID, "blocks/$name")
    private fun blockModelID(name: String) = Identifier(modID, "block/$name")
    private fun itemModelID(name: String) = Identifier(modID, "item/$name")
    private fun recipeID(name: String) = Identifier(modID, "recipes/$name")

    fun itemTagID(name: String) = Identifier("c", "items/$name")

    fun normalBlockLootTable(block: Block, item: BlockItem) {
        val blockName = Registry.BLOCK.getId(block).path
        val itemName = Registry.ITEM.getId(item).path

        resourcePack.addLootTable(
            blockID(blockName),
            loot("minecraft:block")
                .pool(pool()
                    .rolls(1)
                    .entry(entry()
                        .type("minecraft:item")
                        .name("${modID}:$itemName")
                    )
                    .condition(predicate("minecraft:survives_explosion"))
                )
        )
    }

    fun rawOreLootTable(ore: OreBlock, rawOre: Item) {
        val blockName = Registry.BLOCK.getId(ore).path
        val itemName = Registry.ITEM.getId(rawOre).path

        resourcePack.addLootTable(
            blockID(blockName),
            loot("minecraft:block")
                .pool(pool()
                    .rolls(1)
                    .bonus(0)
                    .entry(entry()
                        .type("minecraft:alternatives")
                        .child(entry()
                            .type("minecraft:item")
                            .condition(ARRPUtilities.silkTouchPredicate())
                            .name("${modID}:$blockName")
                        )
                        .child(entry()
                            .type("minecraft:item")
                            .function(function("apply_bonus")
                                .parameter("enchantment", "minecraft:fortune")
                                .parameter("formula", "minecraft:ore_drops")
                            )
                            .function(function("minecraft:explosion_decay"))
                            .name("${modID}:$itemName")
                        )
                    )
                )
        )
    }

    fun normalBlockstate(block: Block) {
        val blockName = Registry.BLOCK.getId(block).path

        resourcePack.addBlockState(
            state(variant(blockModel("${modID}:block/$blockName"))),
            id("${modID}:$blockName")
        )
    }

    fun normalBlockModel(block: Block) {
        val name = Registry.BLOCK.getId(block).path

        resourcePack.addModel(
            model("block/cube_all")
                .textures(textures().`var`("all", "${modID}:block/$name")),
            blockModelID(name)
        )
    }

    fun blockItemModel(item: BlockItem) {
        val itemName = Registry.ITEM.getId(item).path
        val blockName = Registry.BLOCK.getId(item.block).path

        resourcePack.addModel(
            model("${modID}:block/$blockName"),
            itemModelID(itemName)
        )
    }

    fun normalItemModel(item: Item) {
        val name = Registry.ITEM.getId(item).path

        resourcePack.addModel(
            model("item/generated")
                .textures(textures().layer0("${modID}:item/$name")),
            itemModelID(name)
        )
    }

    fun crossbowItemModel(item: OpenCrossbowItem) {
        val name = Registry.ITEM.getId(item).path

        resourcePack.addModel(
            model("item/generated")
                .textures(
                    textures().layer0("${modID}:item/${name}_standby")
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
                    itemModelID("${name}_pulling_0")
                )).addOverride(override(
                    predicate().parameter("pulling", 1).parameter("pull", 0.58),
                    itemModelID("${name}_pulling_1")
                )).addOverride(override(
                    predicate().parameter("pulling", 1).parameter("pull", 0.99),
                    itemModelID("${name}_pulling_2")
                )).addOverride(override(
                    predicate().parameter("charged", 1),
                    itemModelID("${name}_arrow")
                )).addOverride(override(
                    predicate().parameter("charged", 1).parameter("firework", 1),
                    itemModelID("${name}_firework")
                )),
            itemModelID(name)
        )

        for (i in 0..2) {
            resourcePack.addModel(
                model("${modID}:item/${name}")
                    .textures(textures().layer0("${modID}:item/${name}_pulling_$i")),
                itemModelID("${name}_pulling_$i")
            )
        }

        resourcePack.addModel(
            model("${modID}:item/${name}")
                .textures(textures().layer0("${modID}:item/${name}_arrow")),
            itemModelID("${name}_arrow")
        )

        resourcePack.addModel(
            model("${modID}:item/${name}")
                .textures(textures().layer0("${modID}:item/${name}_firework")),
            itemModelID("${name}_firework")
        )
    }
    
    fun handheldItemModel(item: MiningToolItem) {
        val name = Registry.ITEM.getId(item).path
        
        resourcePack.addModel(
            model("item/handheld")
                .textures(textures().layer0("${modID}:item/${name}")),
            itemModelID(name)
        )
    }

    fun altarBlockModel(block: AltarBlock) {
        val name = Registry.BLOCK.getId(block).path

        resourcePack.addModel(
            model("minecraft:block/cube")
                .textures(
                    textures()
                        .particle("${modID}:block/altar_top")
                        .`var`("east", "${modID}:block/altar_side")
                        .`var`("west", "${modID}:block/altar_side")
                        .`var`("north", "${modID}:block/altar_side")
                        .`var`("south", "${modID}:block/altar_side")
                        .`var`("down", "${modID}:block/altar_bottom")
                        .`var`("up", "${modID}:block/altar_top")
                ),
            blockModelID(name)
        )
    }

    fun addTags(name: Identifier, vararg items: Identifier) {
        if (tags.contains(name)) {
            tags[name]?.addAll(items)
        } else {
            tags[name] = items.toMutableList()
        }
    }

    private fun JTag.addAll(vararg tags: Identifier): JTag {
        tags.forEach(this::add)

        return this
    }

    private fun JTag.addAll(tags: Iterable<Identifier>): JTag {
        return addAll(*(tags.toList().toTypedArray()))
    }

    override fun load() {
        loadTags()
        createLang()

        RRPCallback.BEFORE_VANILLA.register {
            it.add(commonResourcePack)
            it.add(resourcePack)
        }

        resourcePack.dump()
        commonResourcePack.dump()
    }

    private fun loadTags() {
        tags.forEach { (name, items) ->
            commonResourcePack.addTag(name, tag().addAll(items))
        }
    }

    fun wireRecipe(ingot: Item, wire: WireItem) {
        val wireID = Registry.ITEM.getId(wire)
        val wireName = wireID.path

        val ingotID = Registry.ITEM.getId(ingot)
        val ingotName = ingotID.path

        resourcePack.addRecipe(
            id("${modID}:$wireName"),
            shapeless(
                ingredients()
                    .add(ingredient().tag("c:wire_cutters"))
                    .add(ingredient().tag("c:${ingotName}s")),
                result("${modID}:$wireName")
            )
        )
    }

    fun circuitRecipe(insulator: Item, wire: WireItem, circuitItem: CircuitItem) {
        val circuitID = Registry.ITEM.getId(circuitItem)

        val wireID = Registry.ITEM.getId(wire)
        val wireName = wireID.path

        resourcePack.addRecipe(
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

        resourcePack.addRecipe(
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

    private fun createLang() {
        resourcePack.addLang(id("${modID}:en_us"), lang)
    }
}