package nichrosia.arcanology.data

import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.`object`.builder.v1.client.model.FabricModelPredicateProviderRegistry
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags
import net.minecraft.block.*
import net.minecraft.item.*
import net.minecraft.util.Identifier
import net.minecraft.util.Rarity
import net.minecraft.util.registry.Registry
import net.minecraft.world.gen.feature.OreFeatureConfig
import net.minecraft.world.gen.feature.util.FeatureContext
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.content.AConfiguredFeatures
import nichrosia.arcanology.content.AItems.generateLang
import nichrosia.arcanology.content.AItems.magicSettings
import nichrosia.arcanology.content.AItems.techSettings
import nichrosia.arcanology.data.DataGenerator.itemTagID
import nichrosia.arcanology.energy.EnergyTier
import nichrosia.arcanology.math.Math.clamp
import nichrosia.arcanology.type.element.Element
import nichrosia.arcanology.type.element.ElementalHeart
import nichrosia.arcanology.type.item.HeartItem
import nichrosia.arcanology.type.item.energy.BatteryItem
import nichrosia.arcanology.type.item.energy.CircuitItem
import nichrosia.arcanology.type.item.energy.WireItem
import nichrosia.arcanology.type.item.magic.MagicCrystalItem
import nichrosia.arcanology.type.item.weapon.crossbow.OpenCrossbowItem
import nichrosia.arcanology.type.item.weapon.crossbow.OpenCrossbowItem.Companion.charged
import nichrosia.arcanology.type.item.weapon.crossbow.OpenCrossbowItem.Companion.hasProjectile

@Suppress("MemberVisibilityCanBePrivate")
data class MaterialHelper(val name: String,
                          private val isTech: Boolean,
                          private val rarity: Rarity,
                          private val miningLevel: Int = 0,
                          private val dropDust: Boolean = false,
                          private val autogenerateLang: Boolean = true) {
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
    lateinit var battery: BatteryItem
    lateinit var circuit: CircuitItem

    lateinit var magicCrystal: MagicCrystalItem
    lateinit var heart: HeartItem

    lateinit var crossbow: OpenCrossbowItem
    lateinit var pickaxe: PickaxeItem

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
        ore = registerBlock(oreID.path, OreBlock(FabricBlockSettings.of(material)
            .requiresTool()
            .strength(5f, oreResistance)
            .breakByTool(FabricToolTags.PICKAXES, miningLevel)))

        oreItem = registerItem(oreID.path, BlockItem(ore, settings))

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
                deepslateOre = registerBlock(
                    "deepslate_${oreID.path}", OreBlock(
                        FabricBlockSettings.of(material)
                            .requiresTool()
                            .strength(5f, oreResistance * 2f)
                            .breakByTool(FabricToolTags.PICKAXES, miningLevel)
                    )
                )

                deepslateOreItem = registerItem("deepslate_${oreID.path}", BlockItem(deepslateOre, settings))

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
        rawOre = registerItem(name, Item(settings))

        if (this::ore.isInitialized) DataGenerator.rawOreLootTable(ore, rawOre)
        if (this::deepslateOre.isInitialized) DataGenerator.rawOreLootTable(deepslateOre, rawOre)
        
        if (addRawOreBlock) {
            rawOreBlock = registerBlock("${name}_block", Block(FabricBlockSettings.of(Material.METAL)
                .requiresTool()
                .strength(5f, rawOreBlockResistance)
                .breakByTool(FabricToolTags.PICKAXES, miningLevel)))

            rawOreBlockItem = registerItem("${name}_block", BlockItem(rawOreBlock, settings))

            DataGenerator.normalBlockLootTable(rawOreBlock, rawOreBlockItem)
        }

        return this
    }

    fun addIngot(name: String = "${this.name}_ingot", ingotItem: Item = Item(settings)): MaterialHelper {
        ingot = registerItem(name, ingotItem)

        DataGenerator.addTags(itemTagID("${name}s"), Registry.ITEM.getId(ingot))

        return this
    }

    fun addWire(name: String = "${this.name}_wire", wireItem: WireItem = WireItem(settings)): MaterialHelper {
        wire = registerItem(name, wireItem)

        if (this::ingot.isInitialized) DataGenerator.wireRecipe(ingot, wire)
        DataGenerator.addTags(itemTagID("${name}s"), Registry.ITEM.getId(wire))

        return this
    }

    fun addDust(name: String = "${this.name}_dust", dustItem: Item = Item(settings)): MaterialHelper {
        dust = registerItem(name, dustItem)

        DataGenerator.addTags(itemTagID("${name}s"), Registry.ITEM.getId(dust))

        return this
    }

    fun addCrystal(name: String = "${this.name}_crystal", crystalItem: Item = Item(settings)): MaterialHelper {
        crystal = registerItem(name, crystalItem)

        DataGenerator.addTags(itemTagID("${name}s"), Registry.ITEM.getId(crystal))

        return this
    }

    fun addBattery(name: String = "${this.name}_battery", tier: EnergyTier, batteryItem: BatteryItem = BatteryItem(settings, tier)): MaterialHelper {
        battery = registerItem(name, batteryItem)

        DataGenerator.addTags(itemTagID(name.replace("battery", "batteries")), Registry.ITEM.getId(battery))

        return this
    }

    fun addCircuit(
        name: String = "${this.name}_circuit",
        wire: WireItem = this.wire,
        insulator: Item = Items.GLASS
    ): MaterialHelper {
        circuit = registerItem(name, CircuitItem(settings))

        DataGenerator.circuitRecipe(insulator, wire, circuit)
        DataGenerator.addTags(itemTagID("${name}s"), Registry.ITEM.getId(circuit))
        DataGenerator.addTags(itemTagID("insulators"), Registry.ITEM.getId(insulator))

        return this
    }

    fun addHeart(name: String = "${this.name}_heart", elementalHeart: ElementalHeart, heartItem: HeartItem = HeartItem(settings, elementalHeart)): MaterialHelper {
        heart = registerItem(name, heartItem)

        return this
    }

    fun addMagicCrystal(name: String = "${this.name}_magic_crystal", element: Element, crystalItem: MagicCrystalItem = MagicCrystalItem(settings, element)): MaterialHelper {
        magicCrystal = registerItem(name, crystalItem)

        return this
    }

    fun addBlock(name: String, type: Block): MaterialHelper {
        registerBlock(name, type)
        registerItem(name, BlockItem(type, settings))

        return this
    }

    fun addCrossbow(name: String = "${this.name}_crossbow", crossbowItem: OpenCrossbowItem = OpenCrossbowItem(settings)): MaterialHelper {
        crossbow = registerItem(name, crossbowItem)

        return this
    }

    fun addPickaxe(name: String = "${this.name}_pickaxe", pickaxeItem: PickaxeItem): MaterialHelper {
        pickaxe = registerItem(name, pickaxeItem)

        if (this::ingot.isInitialized) DataGenerator.pickaxeRecipe(pickaxe, ingot, Items.STICK)

        return this
    }

    private fun <T : Item> registerItem(name: String, content: T): T {
        val registeredContent = Registry.register(Registry.ITEM, Identifier(Arcanology.modID, name), content)

        when(registeredContent) {
            is BlockItem -> DataGenerator.blockItemModel(registeredContent)
            is MiningToolItem -> DataGenerator.handheldItemModel(registeredContent)
            is OpenCrossbowItem -> {
                DataGenerator.crossbowItemModel(registeredContent)

                FabricModelPredicateProviderRegistry.register(registeredContent, Identifier("pull")) { stack, _, entity, _ ->
                    entity ?: return@register 0.0f

                    return@register if (entity.activeItem !== stack) 0.0f else (stack.maxUseTime - entity.itemUseTimeLeft).clamp() / 20.0f
                }

                FabricModelPredicateProviderRegistry.register(registeredContent, Identifier("pulling")) { stack, _, entity, _ ->
                    entity ?: return@register 0.0f

                    return@register if (entity.isUsingItem && entity.activeItem === stack) 1.0f else 0.0f
                }

                FabricModelPredicateProviderRegistry.register(registeredContent, Identifier("firework")) { stack, _, entity, _ ->
                    entity ?: return@register 0.0f

                    return@register if (stack.hasProjectile(Items.FIREWORK_ROCKET)) 1.0f else 0.0f
                }

                FabricModelPredicateProviderRegistry.register(registeredContent, Identifier("charged")) { stack, _, entity, _ ->
                    entity ?: return@register 0.0f

                    return@register if (stack.charged) 1.0f else 0.0f
                }
            }

            else -> DataGenerator.normalItemModel(registeredContent)
        }

        val id = Registry.ITEM.getId(registeredContent)

        DataGenerator.lang.item(id, when(registeredContent) {
            is MagicCrystalItem -> generateCrystalLang(id.path)
            else -> generateLang(id.path)
        })

        return registeredContent
    }

    fun generateCrystalLang(name: String): String {
        return generateLang(name.replace("_magic_", "_"))
    }

    private fun <T : Block> registerBlock(name: String, content: T): T {
        val registeredContent = Registry.register(Registry.BLOCK, Identifier(Arcanology.modID, name), content)

        DataGenerator.normalBlockstate(registeredContent)
        DataGenerator.normalBlockModel(registeredContent)

        val id = Registry.BLOCK.getId(registeredContent)
        DataGenerator.lang.block(id, generateLang(id.path))

        return registeredContent
    }

    class DimensionSelector(val overworld: Boolean = false, val nether: Boolean = false, val end: Boolean = false)
}