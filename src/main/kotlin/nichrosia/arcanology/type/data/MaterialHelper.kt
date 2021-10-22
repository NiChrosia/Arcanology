package nichrosia.arcanology.type.data

import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags
import net.minecraft.block.Material
import net.minecraft.item.*
import net.minecraft.util.Identifier
import net.minecraft.util.Rarity
import net.minecraft.util.registry.Registry
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
import nichrosia.arcanology.type.id.block.AbstractBlock
import nichrosia.arcanology.type.id.block.IdentifiedBlock
import nichrosia.arcanology.type.id.item.AbstractItem
import nichrosia.arcanology.type.id.item.IdentifiedBlockItem
import nichrosia.arcanology.type.id.item.IdentifiedItem
import nichrosia.arcanology.type.id.item.IdentifiedPickaxeItem
import nichrosia.arcanology.type.world.feature.CustomOreFeature
import nichrosia.arcanology.type.world.feature.CustomOreFeatureConfig
import nichrosia.arcanology.type.world.util.ore.NormalAndDeepslateOre
import nichrosia.arcanology.type.world.util.ore.Ore
import nichrosia.arcanology.util.*
import kotlin.reflect.KProperty0

// TODO: make this code remotely nice looking
@Suppress("NestedLambdaShadowedImplicitParameter")
data class MaterialHelper(
    val ID: Identifier,
    val isTech: Boolean,
    val rarity: Rarity,
    val miningLevel: Int = 0,
    val tier: EnergyTier = EnergyTier.standard,
    val element: Element = Element.Mana,
    val insulator: Item = Items.GLASS,
    val toolRod: Item = Items.STICK,
    val rawOreBlockResistance: Float = 10f,

    val toolMaterialConfig: ToolMaterialConfig = ToolMaterialConfig("${ID.path}_material", 0f, 0, 0, 0, 0f),

    val rawOreConfig: MaterialConfig<AbstractItem, IdentifiedItem> = MaterialConfig("raw_${ID.path}", Registrar.item) { IdentifiedItem(settings, it.ID).also {
        rawOreLootTable(ore.block, it)
        rawOreLootTable(normalAndDeepslateOre.deepslateBlock, it)
    } },
    val rawOreBlockConfig: MaterialConfig<AbstractBlock, IdentifiedBlock> = MaterialConfig("raw_${ID.path}_block", Registrar.block) {
        IdentifiedBlock(FabricBlockSettings.of(Material.METAL)
            .requiresTool()
            .strength(5f, rawOreBlockResistance)
            .breakByTool(FabricToolTags.PICKAXES, miningLevel), it.ID)
    },
    val rawOreBlockItemConfig: MaterialConfig<AbstractItem, IdentifiedBlockItem<IdentifiedBlock>> = MaterialConfig("raw_${ID.path}_block", Registrar.item) {
        IdentifiedBlockItem(rawOreBlock, settings, it.ID).also { normalBlockLootTable(rawOreBlock, it) }
    },

    val ingotConfig: MaterialConfig<AbstractItem, IdentifiedItem> = MaterialConfig("${ID.path}_ingot", Registrar.item) { IdentifiedItem(settings, it.ID) },
    val wireConfig: MaterialConfig<AbstractItem, WireItem> = MaterialConfig("${ID.path}_wire", Registrar.item) { WireItem(settings, it.ID).also {
        wireRecipe(ingot, it)
        Arcanology.packManager.apply { tags.add(itemTagID("${ID.path}s"), it) }
    } },
    val dustConfig: MaterialConfig<AbstractItem, IdentifiedItem> = MaterialConfig("${ID.path}_dust", Registrar.item) { IdentifiedItem(settings, it.ID).also {
        Arcanology.packManager.tags.add(Arcanology.packManager.itemTagID("${ID.path}s"), it)
    } },
    val crystalConfig: MaterialConfig<AbstractItem, IdentifiedItem> = MaterialConfig("${ID.path}_crystal", Registrar.item) { IdentifiedItem(settings, it.ID).also {
        Arcanology.packManager.tags.add(Arcanology.packManager.itemTagID("${ID.path}s"), it)
    } },
    val batteryConfig: MaterialConfig<AbstractItem, BatteryItem> = MaterialConfig("${ID.path}_battery", Registrar.item) { BatteryItem(settings, it.ID, tier).also {
        Arcanology.packManager.tags.add(Arcanology.packManager.itemTagID(ID.path.replace("battery", "batteries")), it)
    } },
    val circuitConfig: MaterialConfig<AbstractItem, CircuitItem> = MaterialConfig("${ID.path}_circuit", Registrar.item) { CircuitItem(settings, it.ID).also {
        circuitRecipe(Registry.ITEM.getId(insulator), Arcanology.idOf("${ID.path}_wire"), Arcanology.idOf("${ID.path}_circuit"))
        Arcanology.packManager.tags.add(Arcanology.packManager.itemTagID("${ID.path}s"), it)
        Arcanology.packManager.tags.add(Arcanology.packManager.itemTagID("insulators"), insulator)
    } },

    val magicCrystalConfig: MaterialConfig<AbstractItem, MagicCrystalItem> = MaterialConfig("${ID.path}_magic_crystal", Registrar.item) { MagicCrystalItem(settings, it.ID, element) },
    val magicHeartConfig: MaterialConfig<AbstractItem, HeartItem> = MaterialConfig("${ID.path}_heart", Registrar.item) { HeartItem(settings, it.ID) },

    /** Configuration for the pickaxe. Must be overridden if used. */
    val pickaxeConfig: MaterialConfig<AbstractItem, IdentifiedPickaxeItem> = EmptyConfig(Registrar.item),

    val oreConfig: NormalOreConfig = NormalOreConfig("${ID.path}_feature"),
    val normalAndDeepslateOreConfig: NormalAndDeepslateOreConfig = NormalAndDeepslateOreConfig("${ID.path}_feature"),
    val variableOreConfig: VariableOreConfig = VariableOreConfig(("${ID.path}_feature"))
) {
    val settings: Item.Settings by ConditionedProperty(this::isTech, Registrar.item::magicSettings, Registrar.item::techSettings) { it.rarity(rarity) }
    val languageGenerator: LanguageGenerator = BasicLanguageGenerator()

    val toolMaterial: ToolMaterialConfig.ToolMaterialImpl by toolMaterialConfig
    
    val rawOre: IdentifiedItem by rawOreConfig
    val rawOreBlock: IdentifiedBlock by rawOreBlockConfig
    val rawOreBlockItem: IdentifiedBlockItem<IdentifiedBlock> by rawOreBlockItemConfig
    
    val ingot: IdentifiedItem by ingotConfig
    val wire: WireItem by wireConfig
    val dust: IdentifiedItem by dustConfig
    val crystal: IdentifiedItem by crystalConfig
    val battery: BatteryItem by batteryConfig
    val circuit: CircuitItem by circuitConfig

    val magicCrystal: MagicCrystalItem by magicCrystalConfig
    val magicHeart: HeartItem by magicHeartConfig

    val pickaxe: IdentifiedPickaxeItem by pickaxeConfig

    val ore: Ore<OreFeatureConfig, OreFeature> by oreConfig
    val normalAndDeepslateOre: NormalAndDeepslateOre by normalAndDeepslateOreConfig
    val variableOre: Ore<CustomOreFeatureConfig, CustomOreFeature> by variableOreConfig

    fun add(vararg types: KProperty0<*>) {
        types.forEach(KProperty0<*>::get)
    }
}