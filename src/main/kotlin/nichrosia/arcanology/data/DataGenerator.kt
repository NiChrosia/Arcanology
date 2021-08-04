package nichrosia.arcanology.data

import net.minecraft.block.Block
import net.minecraft.block.OreBlock
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.util.registry.Registry
import java.io.File

/** JSON data generator for use in development.
 *
 * WARNING: disable this if you are not NiChrosia or your copy of this repository is in the exact same location,
 * otherwise you will have a directory with all this stuff but no code.*/
object DataGenerator {
    private val userDir = File(System.getProperty("user.home"))
    private val resourcesDir = File("${userDir.path}/IdeaProjects/Arcanology/src/main/resources")
    private val assetsDir = File("${resourcesDir.path}/assets/arcanology")
    private val dataDir = File("${resourcesDir.path}/data/arcanology")

    enum class DataType {
        BlockState, BlockModel, ItemModel, Recipe, BlockLootTable
    }

    fun normalBlockLootTable(block: Block, item: BlockItem) {
        val lootDir = getDirectory(DataType.BlockLootTable)

        val json = DataTemplates.normalBlockLootTable(item)
        val name = Registry.BLOCK.getId(block).path

        val file = File("${lootDir.path}/$name.json")

        if (!file.exists()) {
            file.createNewFile()
            file.mkdirs()
        }

        file.writeText(json)
    }

    fun rawOreLootTable(ore: OreBlock, rawOre: Item) {
        val lootDir = getDirectory(DataType.BlockLootTable)

        val json = DataTemplates.oreBlockToRawOre(ore, rawOre)
        val name = Registry.BLOCK.getId(ore).path

        val file = File("${lootDir.path}/$name.json")

        if (!file.exists()) {
            file.createNewFile()
            file.mkdirs()
        }

        file.writeText(json)
    }

    fun normalBlockstate(block: Block) {
        val blockstateDir = getDirectory(DataType.BlockState)

        val json = DataTemplates.normalBlockstate(block)
        val name = Registry.BLOCK.getId(block).path

        val file = File("${blockstateDir.path}/$name.json")

        if (!file.exists()) {
            file.createNewFile()
            file.mkdirs()
        }

        file.writeText(json)
    }

    fun normalBlockModel(block: Block) {
        val blockModelDir = getDirectory(DataType.BlockModel)

        val json = DataTemplates.normalBlockModel(block)
        val name = Registry.BLOCK.getId(block).path

        val file = File("${blockModelDir.path}/$name.json")

        if (!file.exists()) {
            file.createNewFile()
            file.mkdirs()
        }

        file.writeText(json)
    }

    fun blockItemModel(item: BlockItem) {
        val itemModelDir = getDirectory(DataType.ItemModel)

        val json = DataTemplates.blockItemModel(item)
        val name = Registry.ITEM.getId(item).path

        val file = File("${itemModelDir.path}/$name.json")

        if (!file.exists()) {
            file.createNewFile()
            file.mkdirs()
        }

        file.writeText(json)
    }

    fun normalItemModel(item: Item) {
        val itemModelDir = getDirectory(DataType.ItemModel)

        val json = DataTemplates.normalItemModel(item)
        val name = Registry.ITEM.getId(item).path

        val file = File("${itemModelDir.path}/$name.json")

        if (!file.exists()) {
            file.createNewFile()
            file.mkdirs()
        }

        file.writeText(json)
    }

    private fun getDirectory(type: DataType): File {
        return when(type) {
            DataType.BlockState -> File("${assetsDir.path}/blockstates")
            DataType.BlockModel -> File("${assetsDir.path}/models/block")
            DataType.ItemModel -> File("${assetsDir.path}/models/item")
            DataType.Recipe -> File("${dataDir.path}/recipes")
            DataType.BlockLootTable -> File("${dataDir.path}/loot_tables/blocks")
        }
    }
}