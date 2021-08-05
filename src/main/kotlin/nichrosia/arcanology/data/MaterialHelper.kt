package nichrosia.arcanology.data

import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags
import net.minecraft.block.*
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.util.Identifier
import net.minecraft.util.Rarity
import net.minecraft.util.registry.Registry
import net.minecraft.world.gen.feature.OreFeatureConfig
import net.minecraft.world.gen.feature.util.FeatureContext
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.content.AConfiguredFeatures
import nichrosia.arcanology.content.AItems.magicSettings
import nichrosia.arcanology.content.AItems.techSettings
import nichrosia.arcanology.energy.EnergyTier
import nichrosia.arcanology.type.element.ElementalHeart
import nichrosia.arcanology.type.item.HeartItem
import nichrosia.arcanology.type.item.energy.BatteryItem
import nichrosia.arcanology.type.item.energy.WireItem

@Suppress("MemberVisibilityCanBePrivate")
data class MaterialHelper(val name: String,
                          private val isTech: Boolean,
                          private val rarity: Rarity,
                          private val miningLevel: Int = 0,
                          private val dropDust: Boolean = false) {
    val settings: Item.Settings
      get() = (if (isTech) techSettings else magicSettings).rarity(rarity)

    lateinit var ore: OreBlock
    lateinit var oreItem: BlockItem
    lateinit var deepslateOre: OreBlock
    lateinit var deepslateOreItem: BlockItem
    
    lateinit var rawOre: Item
    lateinit var rawOreBlock: Block
    lateinit var rawOreBlockItem: BlockItem
    
    lateinit var ingot: Item
    lateinit var wire: WireItem
    lateinit var dust: Item
    lateinit var crystal: Item
    lateinit var batteryItem: BatteryItem

    lateinit var heart: HeartItem

    fun addOre(
        name: String = "${this.name}_ore",
        dimensions: DimensionSelector,
        deepslateVariant: Boolean,
        material: Material = Material.STONE,
        size: Int = 8,
        sizeProvider: (FeatureContext<OreFeatureConfig>) -> Int = { size },
        range: Pair<Int, Int> = 0 to 200,
        generateToBottom: Boolean = false,
        veinsPerChunk: Int = 8,
        randomizeVeinsPerChunk: Boolean = false,
        oreResistance: Float = 100f
    ): MaterialHelper {
        val oreID = Identifier(Arcanology.modID, name)
        ore = register(oreID.path, OreBlock(FabricBlockSettings.of(material)
            .requiresTool()
            .strength(5f, oreResistance)
            .breakByTool(FabricToolTags.PICKAXES, miningLevel)))

        oreItem = register(oreID.path, BlockItem(ore, settings))

        fun registerOre(
            ore: OreBlock,
            biomeSelector: AConfiguredFeatures.BiomeSelector,
            deepslateVariant: Boolean = false,
            deepslateOre: OreBlock? = null
        ) {
            AConfiguredFeatures.registerOre(
                Identifier(Arcanology.modID, "${oreID.path}_feature"), when(biomeSelector) {
                   AConfiguredFeatures.BiomeSelector.Overworld -> Blocks.STONE
                   AConfiguredFeatures.BiomeSelector.TheNether -> Blocks.NETHERRACK
                   AConfiguredFeatures.BiomeSelector.TheEnd -> Blocks.END_STONE
                }, ore, size, sizeProvider, range, generateToBottom, veinsPerChunk,
                randomizeVeinsPerChunk, biomeSelector, deepslateVariant, deepslateOre
            )
        }

        if (dimensions.overworld) {
            if (deepslateVariant) {
                deepslateOre = register(
                    "deepslate_${oreID.path}", OreBlock(
                        FabricBlockSettings.of(material)
                            .requiresTool()
                            .strength(5f, oreResistance * 2f)
                            .breakByTool(FabricToolTags.PICKAXES, miningLevel)
                    )
                )

                deepslateOreItem = register("deepslate_${oreID.path}", BlockItem(deepslateOre, settings))

                registerOre(ore, AConfiguredFeatures.BiomeSelector.Overworld, true, deepslateOre)
            } else {
                registerOre(ore, AConfiguredFeatures.BiomeSelector.Overworld)
            }
        }

        if (dimensions.nether) registerOre(ore, AConfiguredFeatures.BiomeSelector.TheNether)
        if (dimensions.end) registerOre(ore, AConfiguredFeatures.BiomeSelector.TheEnd)

        return this
    }
    
    fun addRawOre(name: String = "raw_${this.name}", addRawOreBlock: Boolean = true, rawOreBlockResistance: Float = 100f): MaterialHelper {
        rawOre = register(name, Item(settings))

        if (this::ore.isInitialized) DataGenerator.rawOreLootTable(ore, rawOre)
        if (this::deepslateOre.isInitialized) DataGenerator.rawOreLootTable(deepslateOre, rawOre)
        
        if (addRawOreBlock) {
            rawOreBlock = register("${name}_block", Block(FabricBlockSettings.of(Material.METAL)
                .requiresTool()
                .strength(5f, rawOreBlockResistance)
                .breakByTool(FabricToolTags.PICKAXES, miningLevel)))

            rawOreBlockItem = register("${name}_block", BlockItem(rawOreBlock, settings))

            DataGenerator.normalBlockLootTable(rawOreBlock, rawOreBlockItem)
        }

        return this
    }

    fun addIngot(name: String = "${this.name}_ingot"): MaterialHelper {
        ingot = register(name, Item(settings))

        return this
    }

    fun addWire(name: String = "${this.name}_wire"): MaterialHelper {
        wire = register(name, WireItem(settings))

        return this
    }

    fun addDust(name: String = "${this.name}_dust"): MaterialHelper {
        dust = register(name, Item(settings))

        return this
    }

    fun addCrystal(name: String = "${this.name}_crystal"): MaterialHelper {
        crystal = register(name, Item(settings))

        return this
    }

    fun addBattery(name: String = "${this.name}_battery", tier: EnergyTier): MaterialHelper {
        batteryItem = register(name, BatteryItem(settings, tier))

        return this
    }

    fun addHeart(name: String = "${this.name}_heart", elementalHeart: ElementalHeart): MaterialHelper {
        heart = register(name, HeartItem(settings, elementalHeart))

        return this
    }

    private fun <T : Item> register(name: String, content: T): T {
        val registeredContent = Registry.register(Registry.ITEM, Identifier(Arcanology.modID, name), content)

        println("Attempting to register content")
        println("Content is ${content.name.asString()}")

        if (registeredContent is BlockItem) {
            println("BlockItem")
            DataGenerator.blockItemModel(registeredContent)
        } else {
            println("Item")
            DataGenerator.normalItemModel(registeredContent)
        }

        return registeredContent
    }

    private fun <T : Block> register(name: String, content: T): T {
        val registeredContent = Registry.register(Registry.BLOCK, Identifier(Arcanology.modID, name), content)

        println("Attempting to register content")
        println("Content is ${registeredContent.name.asString()}")

        println("blockstate")
        DataGenerator.normalBlockstate(registeredContent)
        println("block model")
        DataGenerator.normalBlockModel(registeredContent)

        return registeredContent
    }

    class DimensionSelector(val overworld: Boolean = false, val nether: Boolean = false, val end: Boolean = false)
}