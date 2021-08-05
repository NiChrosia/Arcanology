package nichrosia.arcanology.data

import net.devtech.arrp.api.RuntimeResourcePack.id
import net.minecraft.block.Block
import net.minecraft.block.OreBlock
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.util.registry.Registry
import net.devtech.arrp.json.loot.JLootTable.*
import net.devtech.arrp.json.blockstate.JState.*
import net.devtech.arrp.json.blockstate.JState.model as blockModel
import net.devtech.arrp.json.models.JModel.*
import net.minecraft.util.Identifier
import nichrosia.arcanology.Arcanology.modID
import nichrosia.arcanology.Arcanology.resourcePack

object DataGenerator {
    private fun blockID(name: String) = Identifier(modID, "blocks/$name")
    private fun blockModelID(name: String) = Identifier(modID, "block/$name")
    private fun itemModelID(name: String) = Identifier(modID, "item/$name")

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
}