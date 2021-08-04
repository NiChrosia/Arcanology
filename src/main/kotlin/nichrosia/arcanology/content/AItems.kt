package nichrosia.arcanology.content

import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.util.Rarity
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.energy.EnergyTier
import nichrosia.arcanology.integration.patchouli.GuideBookItem
import nichrosia.arcanology.type.element.ElementalHeart
import nichrosia.arcanology.type.item.HeartItem
import nichrosia.arcanology.data.MaterialHelper
import nichrosia.arcanology.type.item.energy.BatteryItem

@Suppress("MemberVisibilityCanBePrivate")
object AItems : RegisterableContent<Item>(Registry.ITEM) {
    lateinit var altar: BlockItem

    lateinit var prismaticHeart: HeartItem
    lateinit var desolateHeart: HeartItem
    lateinit var moltenHeart: HeartItem
    lateinit var tidalHeart: HeartItem
    lateinit var terreneHeart: HeartItem
    lateinit var celestialHeart: HeartItem
    lateinit var arcaneHeart: HeartItem
    
    lateinit var nickelZincBatteryItem: BatteryItem

    lateinit var arcaneAlmanac: GuideBookItem
    lateinit var componentCompendium: GuideBookItem

    val magicSettings: FabricItemSettings
        get() = FabricItemSettings().group(AItemGroups.magic)

    val techSettings: FabricItemSettings
        get() = FabricItemSettings().group(AItemGroups.tech)

    override fun load() {
        // Elemental hearts
        prismaticHeart = register("prismatic_heart", HeartItem(magicSettings.rarity(Rarity.RARE), ElementalHeart.Prismatic))
        desolateHeart = register("desolate_heart", HeartItem(magicSettings.rarity(Rarity.RARE), ElementalHeart.Desolate))
        moltenHeart = register("molten_heart", HeartItem(magicSettings.rarity(Rarity.RARE), ElementalHeart.Molten))
        tidalHeart = register("tidal_heart", HeartItem(magicSettings.rarity(Rarity.RARE), ElementalHeart.Tidal))
        terreneHeart = register("terrene_heart", HeartItem(magicSettings.rarity(Rarity.RARE), ElementalHeart.Terrene))
        celestialHeart = register("celestial_heart", HeartItem(magicSettings.rarity(Rarity.RARE), ElementalHeart.Celestial))
        arcaneHeart = register("arcane_heart", HeartItem(magicSettings.rarity(Rarity.EPIC), ElementalHeart.Arcane))

        // Materials
        MaterialHelper("velosium", false, Rarity.COMMON, 3)
            .addOre("velosium_ore",
                MaterialHelper.DimensionSelector(end = true),
                false,
                AMaterials.velosium,
                2,
                AConfiguredFeatures.distanceToOreSize(2000, 2, 15),
                12 to 65,
                false,
                3,
                true,
                150f)
            .addRawOre()
            .addIngot()
            .generateData()

        MaterialHelper("aegirite", false, Rarity.COMMON, 3)
            .addOre("aegirite_ore",
                MaterialHelper.DimensionSelector(end = true),
                false,
                AMaterials.elementalCrystal,
                2,
                AConfiguredFeatures.distanceToOreSize(4000, 2, 8),
                34 to 56,
                false,
                4,
                true,
                100f)
            .addCrystal()
            .generateData()

        MaterialHelper("xenothite", false, Rarity.UNCOMMON, 4)
            .addOre("xenothite_ore",
                MaterialHelper.DimensionSelector(end = true),
                false,
                AMaterials.xenothite,
                2,
                AConfiguredFeatures.distanceToOreSize(10000, 1, 3),
                12 to 56,
                false,
                2,
                false)
            .addRawOre()
            .addIngot()
            .generateData()

        MaterialHelper("aluminum", true, Rarity.COMMON, 2)
            .addOre("aluminum_ore",
                MaterialHelper.DimensionSelector(overworld = true),
                true,
                range = 0 to 64,
                generateToBottom = true)
            .addRawOre()
            .addIngot()
            .addWire()
            .generateData()

        // Circuits


        // Batteries
        nickelZincBatteryItem = register("nickel_zinc_battery", BatteryItem(techSettings, EnergyTier.T1))

        // Misc
        altar = register("altar", BlockItem(ABlocks.altar, magicSettings.rarity(Rarity.EPIC)))

        arcaneAlmanac = register("arcane_almanac", GuideBookItem(magicSettings, "arcane_almanac"))
        componentCompendium = register("component_compendium", GuideBookItem(techSettings, "component_compendium"))
    }
}