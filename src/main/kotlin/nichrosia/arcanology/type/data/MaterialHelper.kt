package nichrosia.arcanology.type.data

import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags
import net.minecraft.block.Block
import net.minecraft.block.Material
import net.minecraft.item.*
import net.minecraft.util.Rarity
import net.minecraft.world.gen.feature.*
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.registry.Registrar
import nichrosia.arcanology.registry.lang.LanguageGenerator
import nichrosia.arcanology.registry.lang.impl.BasicLanguageGenerator
import nichrosia.arcanology.type.content.item.energy.BatteryItem
import nichrosia.arcanology.type.content.item.energy.CircuitItem
import nichrosia.arcanology.type.content.item.energy.WireItem
import nichrosia.arcanology.type.content.item.magic.HeartItem
import nichrosia.arcanology.type.content.item.magic.MagicCrystalItem
import nichrosia.arcanology.type.data.config.*
import nichrosia.arcanology.type.data.config.ore.impl.NormalAndDeepslateOreConfig
import nichrosia.arcanology.type.data.config.ore.impl.NormalOreConfig
import nichrosia.arcanology.type.data.config.ore.impl.VariableOreConfig
import nichrosia.arcanology.type.data.config.tool.ToolMaterialConfig
import nichrosia.arcanology.type.delegates.ConditionedProperty
import nichrosia.arcanology.type.element.Element
import nichrosia.arcanology.type.energy.EnergyTier
import nichrosia.arcanology.type.world.feature.CustomOreFeature
import nichrosia.arcanology.type.world.feature.CustomOreFeatureConfig
import nichrosia.arcanology.type.world.util.ore.NormalAndDeepslateOre
import nichrosia.arcanology.type.world.util.ore.Ore
import nichrosia.arcanology.util.*
import kotlin.reflect.KProperty0

data class MaterialHelper(
    val name: String,
    val isTech: Boolean,
    val rarity: Rarity,
    val miningLevel: Int = 0,
    val tier: EnergyTier = EnergyTier.standard,
    val element: Element = Element.Mana,
    val insulator: Item = Items.GLASS,
    val toolRod: Item = Items.STICK,
    val rawOreBlockResistance: Float = 10f,

    val toolMaterialConfig: ToolMaterialConfig = ToolMaterialConfig("${name}_material", 0f, 0, 0, 0, 0f),

    val rawOreBlockConfig: MaterialConfig<Block, Block> = MaterialConfig("raw_${name}_block", Registrar.block) {
        Block(FabricBlockSettings.of(Material.METAL)
            .requiresTool()
            .strength(5f, rawOreBlockResistance)
            .breakByTool(FabricToolTags.PICKAXES, miningLevel))
    },
    val rawOreBlockItemConfig: MaterialConfig<Item, BlockItem> = MaterialConfig("raw_${name}_block", Registrar.item, {
        normalBlockLootTable(rawOreBlock, it)
    }) { BlockItem(rawOreBlock, settings) },
    val rawOreConfig: MaterialConfig<Item, Item> = MaterialConfig("raw_$name", Registrar.item, {
        rawOreLootTable(ore.block, rawOre)
        rawOreLootTable(normalAndDeepslateOre.deepslateBlock, rawOre)
    }) { Item(settings) },

    val ingotConfig: MaterialConfig<Item, Item> = MaterialConfig("${name}_ingot", Registrar.item) { Item(settings) },
    val wireConfig: MaterialConfig<Item, WireItem> = MaterialConfig("${name}_wire", Registrar.item, {
        wireRecipe(ingot, it)
        Arcanology.packManager.apply { tags.add(itemTagID("${name}s"), it) }
    }) { WireItem(settings) },
    val dustConfig: MaterialConfig<Item, Item> = MaterialConfig("${name}_dust", Registrar.item, {
        Arcanology.packManager.tags.add(Arcanology.packManager.itemTagID("${name}s"), it)
    }) { Item(settings) },
    val crystalConfig: MaterialConfig<Item, Item> = MaterialConfig("${name}_crystal", Registrar.item, {
        Arcanology.packManager.tags.add(Arcanology.packManager.itemTagID("${name}s"), it)
    }) { Item(settings) },
    val batteryConfig: MaterialConfig<Item, BatteryItem> = MaterialConfig("${name}_battery", Registrar.item, {
        Arcanology.packManager.tags.add(Arcanology.packManager.itemTagID(name.replace("battery", "batteries")), it)
    }) { BatteryItem(settings, tier) },
    val circuitConfig: MaterialConfig<Item, CircuitItem> = MaterialConfig("${name}_circuit", Registrar.item, {
        circuitRecipe(insulator, wire, it)
        Arcanology.packManager.tags.add(Arcanology.packManager.itemTagID("${name}s"), it)
        Arcanology.packManager.tags.add(Arcanology.packManager.itemTagID("insulators"), insulator)
    }) { CircuitItem(settings) },

    val magicCrystalConfig: MaterialConfig<Item, MagicCrystalItem> = MaterialConfig("${name}_magic_crystal", Registrar.item) { MagicCrystalItem(settings, element) },
    val magicHeartConfig: MaterialConfig<Item, HeartItem> = MaterialConfig("${name}_heart", Registrar.item) { HeartItem(settings, element) },

    /** Configuration for the pickaxe. Must be overridden if used. */
    val pickaxeConfig: MaterialConfig<Item, PickaxeItem> = EmptyConfig("${name}_pickaxe", Registrar.item) {
        pickaxeRecipe(it, ingot, toolRod)
    },

    val oreConfig: NormalOreConfig = NormalOreConfig("${name}_feature"),
    val normalAndDeepslateOreConfig: NormalAndDeepslateOreConfig = NormalAndDeepslateOreConfig("${name}_feature"),
    val variableOreConfig: VariableOreConfig = VariableOreConfig(("${name}_feature"))
) {
    val settings: Item.Settings by ConditionedProperty(this::isTech, Registrar.item::magicSettings, Registrar.item::techSettings) { it.rarity(rarity) }
    val languageGenerator: LanguageGenerator = BasicLanguageGenerator()

    val toolMaterial: ToolMaterialConfig.ToolMaterialImpl by toolMaterialConfig
    
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

    val pickaxe: PickaxeItem by pickaxeConfig

    val ore: Ore<OreFeatureConfig, OreFeature> by oreConfig
    val normalAndDeepslateOre: NormalAndDeepslateOre by normalAndDeepslateOreConfig
    val variableOre: Ore<CustomOreFeatureConfig, CustomOreFeature> by variableOreConfig

    fun add(vararg types: KProperty0<*>) {
        types.forEach(KProperty0<*>::get)
    }
}