package nichrosia.arcanology.content

import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.util.Rarity
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.type.element.ElementalHeart
import nichrosia.arcanology.type.item.HeartItem
import nichrosia.arcanology.type.item.energy.BatteryItem

@Suppress("MemberVisibilityCanBePrivate")
object AItems : RegisterableContent<Item>(Registry.ITEM) {
    lateinit var velosiumOre: BlockItem
    lateinit var aegiriteOre: BlockItem
    lateinit var xenothiteOre: BlockItem

    lateinit var aegirite: Item
    lateinit var velosiumRawOre: Item

    lateinit var velosiumIngot: Item

    lateinit var altar: BlockItem

    lateinit var prismaticHeart: HeartItem
    lateinit var desolateHeart: HeartItem
    lateinit var moltenHeart: HeartItem
    lateinit var tidalHeart: HeartItem
    lateinit var terreneHeart: HeartItem
    lateinit var celestialHeart: HeartItem
    lateinit var arcaneHeart: HeartItem
    
    lateinit var nickelZincBatteryItem: BatteryItem

    val magicSettings: FabricItemSettings
        get() = FabricItemSettings().group(AItemGroups.magic)

    val techSettings: FabricItemSettings
        get() = FabricItemSettings().group(AItemGroups.tech)

    override fun load() {
        // Ores
        velosiumOre = register("velosium_ore_item", BlockItem(ABlocks.velosiumOre, magicSettings))
        aegiriteOre = register("aegirite_ore_item", BlockItem(ABlocks.aegiriteOre, magicSettings))
        xenothiteOre = register("xenothite_ore_item", BlockItem(ABlocks.xenothiteOre, magicSettings.rarity(Rarity.UNCOMMON)))

        // Crystals / Raw ore
        aegirite = register("aegirite", Item(magicSettings))

        // Ingots

        // Elemental hearts
        prismaticHeart = register("prismatic_heart", HeartItem(magicSettings.rarity(Rarity.RARE), ElementalHeart.Prismatic))
        desolateHeart = register("desolate_heart", HeartItem(magicSettings.rarity(Rarity.RARE), ElementalHeart.Desolate))
        moltenHeart = register("molten_heart", HeartItem(magicSettings.rarity(Rarity.RARE), ElementalHeart.Molten))
        tidalHeart = register("tidal_heart", HeartItem(magicSettings.rarity(Rarity.RARE), ElementalHeart.Tidal))
        terreneHeart = register("terrene_heart", HeartItem(magicSettings.rarity(Rarity.RARE), ElementalHeart.Terrene))
        celestialHeart = register("celestial_heart", HeartItem(magicSettings.rarity(Rarity.RARE), ElementalHeart.Celestial))
        arcaneHeart = register("arcane_heart", HeartItem(magicSettings.rarity(Rarity.EPIC), ElementalHeart.Arcane))
        
        // Batteries
        nickelZincBatteryItem = register("nickel_zinc_battery", BatteryItem(techSettings, 1000.0, 64.0, 64.0))

        // Misc
        altar = register("altar_item", BlockItem(ABlocks.altar, magicSettings.rarity(Rarity.EPIC)))
    }
}