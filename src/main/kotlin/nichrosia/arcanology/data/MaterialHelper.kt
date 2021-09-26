package nichrosia.arcanology.data

import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.`object`.builder.v1.client.model.FabricModelPredicateProviderRegistry
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags
import net.minecraft.block.Block
import net.minecraft.block.Material
import net.minecraft.block.OreBlock
import net.minecraft.item.*
import net.minecraft.util.Identifier
import net.minecraft.util.Rarity
import net.minecraft.util.registry.Registry
import net.minecraft.world.gen.feature.ConfiguredFeature
import net.minecraft.world.gen.feature.OreFeature
import net.minecraft.world.gen.feature.OreFeatureConfig
import net.minecraft.world.gen.feature.util.FeatureContext
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.content.ConfiguredFeatureRegistrar
import nichrosia.arcanology.ctype.item.energy.BatteryItem
import nichrosia.arcanology.ctype.item.energy.CircuitItem
import nichrosia.arcanology.ctype.item.energy.WireItem
import nichrosia.arcanology.ctype.item.magic.HeartItem
import nichrosia.arcanology.ctype.item.magic.MagicCrystalItem
import nichrosia.arcanology.ctype.item.weapon.OpenCrossbowItem
import nichrosia.arcanology.ctype.item.weapon.OpenCrossbowItem.Companion.charged
import nichrosia.arcanology.ctype.item.weapon.OpenCrossbowItem.Companion.hasProjectile
import nichrosia.arcanology.registry.Registrar
import nichrosia.arcanology.registry.lang.LanguageGenerator
import nichrosia.arcanology.registry.lang.impl.BasicLanguageGenerator
import nichrosia.arcanology.type.element.Element
import nichrosia.arcanology.type.element.ElementalHeart
import nichrosia.arcanology.type.energy.EnergyTier
import nichrosia.arcanology.type.world.feature.CustomOreFeature
import nichrosia.arcanology.type.world.feature.CustomOreFeatureConfig
import nichrosia.arcanology.type.world.util.BiomeSelector
import nichrosia.arcanology.util.*

@Suppress("MemberVisibilityCanBePrivate")
data class MaterialHelper(val name: String,
                          private val isTech: Boolean,
                          private val rarity: Rarity,
                          private val miningLevel: Int = 0,
                          private val dropDust: Boolean = false,
                          private val autogenerateLang: Boolean = true) {
    val settings: Item.Settings
      get() = (if (isTech) Registrar.item.techSettings else Registrar.item.magicSettings).rarity(rarity)

    val languageGenerator: LanguageGenerator = BasicLanguageGenerator()

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

    lateinit var oreFeature: ConfiguredFeature<OreFeatureConfig, OreFeature>
    lateinit var variableOreFeature: ConfiguredFeature<CustomOreFeatureConfig, CustomOreFeature>

    fun addOverworldDeepslateOre(
        name: String = "${this.name}_ore",
        blockMaterial: Material = Material.STONE,
        oreResistance: Float = 100f,
        oreBlobSize: Int = 8,
        blobsPerChunk: Int = 8,
        range: Pair<Int, Int> = 0 to 200,
    ): MaterialHelper {
        val oreID = Arcanology.idOf(name)
        ore = registerBlock(oreID.path, OreBlock(FabricBlockSettings.of(blockMaterial)
            .requiresTool()
            .strength(5f, oreResistance)
            .breakByTool(FabricToolTags.PICKAXES, miningLevel)))

        oreItem = registerItem(oreID.path, BlockItem(ore, settings))
        deepslateOre = registerBlock(
            "deepslate_${oreID.path}", OreBlock(
                FabricBlockSettings.of(blockMaterial)
                    .requiresTool()
                    .strength(5f, oreResistance * 2f)
                    .breakByTool(FabricToolTags.PICKAXES, miningLevel)
            )
        )

        deepslateOreItem = registerItem("deepslate_${oreID.path}", BlockItem(deepslateOre, settings))
        oreFeature = ConfiguredFeatureRegistrar.registerNormalAndDeepslateOre(oreID, ore, deepslateOre, oreBlobSize, blobsPerChunk, range)

        return this
    }

    fun addOre(
        name: String = "${this.name}_ore",
        blockMaterial: Material = Material.STONE,
        oreResistance: Float = 100f,
        oreBlobSize: Int = 8,
        blobsPerChunk: Int = 8,
        range: Pair<Int, Int> = 0 to 200,
        biomeSelector: BiomeSelector
    ): MaterialHelper {
        val oreID = Arcanology.idOf(name)
        ore = registerBlock(oreID.path, OreBlock(FabricBlockSettings.of(blockMaterial)
            .requiresTool()
            .strength(5f, oreResistance)
            .breakByTool(FabricToolTags.PICKAXES, miningLevel)))

        oreItem = registerItem(oreID.path, BlockItem(ore, settings))
        oreFeature = ConfiguredFeatureRegistrar.registerOre(oreID, ore, oreBlobSize, blobsPerChunk, range, biomeSelector)

        return this
    }

    fun addVariableOre(
        name: String = "${this.name}_ore",
        blockMaterial: Material = Material.STONE,
        oreResistance: Float = 100f,
        fallbackOreBlobSize: Int = 8,
        blobsPerChunk: Int = 8,
        range: Pair<Int, Int> = 0 to 200,
        biomeSelector: BiomeSelector,
        sizeFactory: (FeatureContext<CustomOreFeatureConfig>) -> Int = { fallbackOreBlobSize }
    ): MaterialHelper {
        val oreID = Arcanology.idOf(name)
        ore = registerBlock(oreID.path, OreBlock(FabricBlockSettings.of(blockMaterial)
            .requiresTool()
            .strength(5f, oreResistance)
            .breakByTool(FabricToolTags.PICKAXES, miningLevel)))

        oreItem = registerItem(oreID.path, BlockItem(ore, settings))
        variableOreFeature = ConfiguredFeatureRegistrar.registerVariableOre(oreID, ore, range, blobsPerChunk, biomeSelector, sizeFactory)

        return this
    }
    
    fun addRawOre(name: String = "raw_${this.name}", addRawOreBlock: Boolean = true, rawOreBlockResistance: Float = 100f): MaterialHelper {
        rawOre = registerItem(name, Item(settings))

        if (this::ore.isInitialized) rawOreLootTable(ore, rawOre)
        if (this::deepslateOre.isInitialized) rawOreLootTable(deepslateOre, rawOre)
        
        if (addRawOreBlock) {
            rawOreBlock = registerBlock("${name}_block", Block(FabricBlockSettings.of(Material.METAL)
                .requiresTool()
                .strength(5f, rawOreBlockResistance)
                .breakByTool(FabricToolTags.PICKAXES, miningLevel)))

            rawOreBlockItem = registerItem("${name}_block", BlockItem(rawOreBlock, settings))

            normalBlockLootTable(rawOreBlock, rawOreBlockItem)
        }

        return this
    }

    fun addIngot(name: String = "${this.name}_ingot", ingotItem: Item = Item(settings)): MaterialHelper {
        ingot = registerItem(name, ingotItem)

        Arcanology.resourceManager.tags.add(Arcanology.resourceManager.itemTagID("${name}s"), ingot)

        return this
    }

    fun addWire(name: String = "${this.name}_wire", wireItem: WireItem = WireItem(settings)): MaterialHelper {
        wire = registerItem(name, wireItem)

        if (this::ingot.isInitialized) wireRecipe(ingot, wire)
        Arcanology.resourceManager.tags.add(Arcanology.resourceManager.itemTagID("${name}s"), wire)

        return this
    }

    fun addDust(name: String = "${this.name}_dust", dustItem: Item = Item(settings)): MaterialHelper {
        dust = registerItem(name, dustItem)

        Arcanology.resourceManager.tags.add(Arcanology.resourceManager.itemTagID("${name}s"), dust)

        return this
    }

    fun addCrystal(name: String = "${this.name}_crystal", crystalItem: Item = Item(settings)): MaterialHelper {
        crystal = registerItem(name, crystalItem)

        Arcanology.resourceManager.tags.add(Arcanology.resourceManager.itemTagID("${name}s"), crystal)

        return this
    }

    fun addBattery(name: String = "${this.name}_battery", tier: EnergyTier, batteryItem: BatteryItem = BatteryItem(settings, tier)): MaterialHelper {
        battery = registerItem(name, batteryItem)

        Arcanology.resourceManager.tags.add(Arcanology.resourceManager.itemTagID(name.replace("battery", "batteries")), battery)

        return this
    }

    fun addCircuit(
        name: String = "${this.name}_circuit",
        wire: WireItem = this.wire,
        insulator: Item = Items.GLASS
    ): MaterialHelper {
        circuit = registerItem(name, CircuitItem(settings))

        circuitRecipe(insulator, wire, circuit)
        Arcanology.resourceManager.tags.add(Arcanology.resourceManager.itemTagID("${name}s"), circuit)
        Arcanology.resourceManager.tags.add(Arcanology.resourceManager.itemTagID("insulators"), insulator)

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

        if (this::ingot.isInitialized) pickaxeRecipe(pickaxe, ingot, Items.STICK)

        return this
    }

    private fun <T : Item> registerItem(name: String, content: T): T {
        val registeredContent = Registry.register(Registry.ITEM, Arcanology.idOf(name), content)

        when(registeredContent) {
            is BlockItem -> blockItemModel(registeredContent)
            is MiningToolItem -> handheldItemModel(registeredContent)
            is OpenCrossbowItem -> {
                crossbowItemModel(registeredContent)

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

            else -> normalItemModel(registeredContent)
        }

        val id = Registry.ITEM.getId(registeredContent)

        Arcanology.resourceManager.englishLang.item(id, when(registeredContent) {
            is MagicCrystalItem -> languageGenerator.generateLang(id.path.replace("_magic_", "_"))
            else -> languageGenerator.generateLang(id.path)
        })

        return registeredContent
    }

    private fun <T : Block> registerBlock(name: String, content: T): T {
        val registeredContent = Registry.register(Registry.BLOCK, Arcanology.idOf(name), content)

        normalBlockstate(registeredContent)
        normalBlockModel(registeredContent)

        val id = Registry.BLOCK.getId(registeredContent)
        Arcanology.resourceManager.englishLang.block(id, languageGenerator.generateLang(id.path))

        return registeredContent
    }
}