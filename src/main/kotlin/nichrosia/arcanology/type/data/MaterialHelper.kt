package nichrosia.arcanology.type.data

import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags
import net.minecraft.block.Block
import net.minecraft.block.Material
import net.minecraft.item.*
import net.minecraft.util.Rarity
import net.minecraft.world.gen.feature.*
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.type.content.item.energy.BatteryItem
import nichrosia.arcanology.type.content.item.energy.CircuitItem
import nichrosia.arcanology.type.content.item.energy.WireItem
import nichrosia.arcanology.type.content.item.magic.HeartItem
import nichrosia.arcanology.type.content.item.magic.MagicCrystalItem
import nichrosia.arcanology.type.content.item.weapon.OpenCrossbowItem
import nichrosia.arcanology.registry.Registrar
import nichrosia.arcanology.registry.lang.LanguageGenerator
import nichrosia.arcanology.registry.lang.impl.BasicLanguageGenerator
import nichrosia.arcanology.type.data.config.*
import nichrosia.arcanology.type.data.config.ore.impl.NormalAndDeepslateOreConfiguration
import nichrosia.arcanology.type.data.config.ore.impl.NormalOreConfiguration
import nichrosia.arcanology.type.data.config.ore.impl.VariableOreConfiguration
import nichrosia.arcanology.type.data.config.tool.ToolMaterialConfiguration
import nichrosia.arcanology.type.data.ore.NormalAndDeepslateOre
import nichrosia.arcanology.type.data.ore.Ore
import nichrosia.arcanology.type.element.Element
import nichrosia.arcanology.type.energy.EnergyTier
import nichrosia.arcanology.type.properties.ConditionedProperty
import nichrosia.arcanology.type.world.feature.CustomOreFeature
import nichrosia.arcanology.type.world.feature.CustomOreFeatureConfig
import nichrosia.arcanology.util.*
import kotlin.reflect.KProperty0

@Suppress("MemberVisibilityCanBePrivate")
data class MaterialHelper(
    val name: String,
    val isTech: Boolean,
    val rarity: Rarity,
    val miningLevel: Int = 0,
    val autogenerateLang: Boolean = true,
    val tier: EnergyTier = EnergyTier.T1,
    val element: Element = Element.Mana,
    val insulator: Item = Items.GLASS,
    val toolRod: Item = Items.STICK,
    val rawOreBlockResistance: Float = 10f,

    val toolMaterialConfig: ToolMaterialConfiguration = ToolMaterialConfiguration("${name}_material", 0f, 0, 0, 0, 0f),

    val rawOreBlockConfig: MaterialConfiguration<Block, Block> = MaterialConfiguration("raw_${name}_block", Registrar.block) {
        Block(FabricBlockSettings.of(Material.METAL)
            .requiresTool()
            .strength(5f, rawOreBlockResistance)
            .breakByTool(FabricToolTags.PICKAXES, miningLevel))
    },
    val rawOreBlockItemConfig: MaterialConfiguration<Item, BlockItem> = MaterialConfiguration("raw_${name}_block", Registrar.item, {
        normalBlockLootTable(rawOreBlock, it)
    }) { BlockItem(rawOreBlock, settings) },
    val rawOreConfig: MaterialConfiguration<Item, Item> = MaterialConfiguration("raw_$name", Registrar.item, {
        rawOreLootTable(ore.block, rawOre)
        rawOreLootTable(normalAndDeepslateOre.deepslateBlock, rawOre)
    }) { Item(settings) },

    val ingotConfig: MaterialConfiguration<Item, Item> = MaterialConfiguration("${name}_ingot", Registrar.item) { Item(settings) },
    val wireConfig: MaterialConfiguration<Item, WireItem> = MaterialConfiguration("${name}_wire", Registrar.item, {
        wireRecipe(ingot, it)
        Arcanology.runtimeResourceManager.apply { tags.add(itemTagID("${name}s"), it) }
    }) { WireItem(settings) },
    val dustConfig: MaterialConfiguration<Item, Item> = MaterialConfiguration("${name}_dust", Registrar.item, {
        Arcanology.runtimeResourceManager.tags.add(Arcanology.runtimeResourceManager.itemTagID("${name}s"), it)
    }) { Item(settings) },
    val crystalConfig: MaterialConfiguration<Item, Item> = MaterialConfiguration("${name}_crystal", Registrar.item, {
        Arcanology.runtimeResourceManager.tags.add(Arcanology.runtimeResourceManager.itemTagID("${name}s"), it)
    }) { Item(settings) },
    val batteryConfig: MaterialConfiguration<Item, BatteryItem> = MaterialConfiguration("${name}_battery", Registrar.item, {
        Arcanology.runtimeResourceManager.tags.add(Arcanology.runtimeResourceManager.itemTagID(name.replace("battery", "batteries")), it)
    }) { BatteryItem(settings, tier) },
    val circuitConfig: MaterialConfiguration<Item, CircuitItem> = MaterialConfiguration("${name}_circuit", Registrar.item, {
        circuitRecipe(insulator, wire, it)
        Arcanology.runtimeResourceManager.tags.add(Arcanology.runtimeResourceManager.itemTagID("${name}s"), it)
        Arcanology.runtimeResourceManager.tags.add(Arcanology.runtimeResourceManager.itemTagID("insulators"), insulator)
    }) { CircuitItem(settings) },

    val magicCrystalConfig: MaterialConfiguration<Item, MagicCrystalItem> = MaterialConfiguration("${name}_magic_crystal", Registrar.item) { MagicCrystalItem(settings, element) },
    val magicHeartConfig: MaterialConfiguration<Item, HeartItem> = MaterialConfiguration("${name}_heart", Registrar.item) { HeartItem(settings, element) },

    val crossbowConfig: MaterialConfiguration<Item, OpenCrossbowItem> = MaterialConfiguration("${name}_crossbow", Registrar.item) { OpenCrossbowItem(settings) },
    /** Configuration for the pickaxe. Must be overridden if used. */
    val pickaxeConfig: MaterialConfiguration<Item, PickaxeItem> = EmptyConfiguration("${name}_pickaxe", Registrar.item) {
        pickaxeRecipe(it, ingot, toolRod)
    },

    val oreConfig: NormalOreConfiguration = NormalOreConfiguration("${name}_feature"),
    val normalAndDeepslateOreConfig: NormalAndDeepslateOreConfiguration = NormalAndDeepslateOreConfiguration("${name}_feature"),
    val variableOreConfig: VariableOreConfiguration = VariableOreConfiguration(("${name}_feature"))
) {
    val settings: Item.Settings by ConditionedProperty(this::isTech, Registrar.item.magicSettings, Registrar.item.techSettings)
    val languageGenerator: LanguageGenerator = BasicLanguageGenerator()

    val toolMaterial: ToolMaterialConfiguration.ToolMaterialImpl by toolMaterialConfig
    
    val rawOre: Item by rawOreConfig
    val rawOreBlock: Block by rawOreBlockConfig
    val rawOreBlockItem: BlockItem by rawOreBlockItemConfig
    
    val ingot: Item by ingotConfig
    val wire: WireItem by wireConfig
    val dust: Item by dustConfig
    val crystal: Item by crystalConfig
    val battery: BatteryItem by batteryConfig
    val circuit: CircuitItem by circuitConfig

    val magicCrystal: MagicCrystalItem by magicCrystalConfig
    val magicHeart: HeartItem by magicHeartConfig

    val crossbow: OpenCrossbowItem by crossbowConfig
    val pickaxe: PickaxeItem by pickaxeConfig

    val ore: Ore<OreFeatureConfig, OreFeature> by oreConfig
    val normalAndDeepslateOre: NormalAndDeepslateOre by normalAndDeepslateOreConfig
    val variableOre: Ore<CustomOreFeatureConfig, CustomOreFeature> by variableOreConfig

    fun add(vararg types: KProperty0<*>) {
        types.forEach(KProperty0<*>::get)
    }
}