package nichrosia.arcanology.data

import net.devtech.arrp.api.RRPCallback
import net.devtech.arrp.api.RuntimeResourcePack.id
import net.devtech.arrp.json.blockstate.JState.*
import net.devtech.arrp.json.lang.JLang.lang
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
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.Arcanology.commonResourcePack
import nichrosia.arcanology.Arcanology.modID
import nichrosia.arcanology.Arcanology.resourcePack
import nichrosia.arcanology.content.type.Content
import nichrosia.arcanology.type.block.AltarBlock
import nichrosia.arcanology.type.item.energy.CircuitItem
import nichrosia.arcanology.type.item.energy.WireItem
import net.devtech.arrp.json.blockstate.JState.model as blockModel

object DataGenerator : Content() {
    private val tags = mutableMapOf<Identifier, MutableList<Identifier>>()
    val lang = lang()

    private fun blockID(name: String) = Identifier(modID, "blocks/$name")
    private fun blockModelID(name: String) = Identifier(modID, "block/$name")
    private fun itemModelID(name: String) = Identifier(modID, "item/$name")

    fun blockTagID(name: String) = Identifier("c", "blocks/$name")
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
                        .name("arcanology:$itemName")
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
                            .name("arcanology:$blockName")
                        )
                        .child(entry()
                            .type("minecraft:item")
                            .function(function("apply_bonus")
                                .parameter("enchantment", "minecraft:fortune")
                                .parameter("formula", "minecraft:ore_drops")
                            )
                            .function(function("minecraft:explosion_decay"))
                            .name("arcanology:$itemName")
                        )
                    )
                )
        )
    }

    fun normalBlockstate(block: Block) {
        val blockName = Registry.BLOCK.getId(block).path

        resourcePack.addBlockState(
            state(variant(blockModel("arcanology:block/$blockName"))),
            id("arcanology:$blockName")
        )
    }

    fun normalBlockModel(block: Block) {
        val name = Registry.BLOCK.getId(block).path

        resourcePack.addModel(
            model("block/cube_all")
                .textures(textures().`var`("all", "arcanology:block/$name")),
            blockModelID(name)
        )
    }

    fun blockItemModel(item: BlockItem) {
        val itemName = Registry.ITEM.getId(item).path
        val blockName = Registry.BLOCK.getId(item.block).path

        resourcePack.addModel(
            model("arcanology:block/$blockName"),
            itemModelID(itemName)
        )
    }

    fun normalItemModel(item: Item) {
        val name = Registry.ITEM.getId(item).path

        resourcePack.addModel(
            model("item/generated")
                .textures(textures().layer0("arcanology:item/$name")),
            itemModelID(name)
        )
    }

    fun altarBlockModel(block: AltarBlock) {
        val name = Registry.BLOCK.getId(block).path

        resourcePack.addModel(
            model("minecraft:block/cube")
                .textures(
                    textures()
                        .particle("arcanology:block/altar_top")
                        .`var`("east", "arcanology:block/altar_side")
                        .`var`("west", "arcanology:block/altar_side")
                        .`var`("north", "arcanology:block/altar_side")
                        .`var`("south", "arcanology:block/altar_side")
                        .`var`("down", "arcanology:block/altar_bottom")
                        .`var`("up", "arcanology:block/altar_top")
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
            id("arcanology:$wireName"),
            shapeless(
                ingredients()
                    .add(ingredient().tag("c:wire_cutters"))
                    .add(ingredient().tag("c:${ingotName}s")),
                result("arcanology:$wireName")
            )
        )
    }

    fun circuitRecipe(insulator: Item, wire: WireItem, circuitItem: CircuitItem) {
        val circuitID = Registry.ITEM.getId(circuitItem)
        val circuitName = circuitID.path

        val wireID = Registry.ITEM.getId(wire)
        val wireName = wireID.path

        resourcePack.addRecipe(
            id("arcanology:$circuitName"),
            shaped(
                pattern()
                    .row1(" GW")
                    .row2("GWG")
                    .row3("WG "),
                keys()
                    .key("W", ingredient().tag("c:${wireName}s"))
                    .key("G", ingredient().item(insulator)),
                result("arcanology:$circuitName")
            )
        )
    }

    private fun createLang() {
        resourcePack.addLang(id("arcanology:en_us"), lang)
    }
}