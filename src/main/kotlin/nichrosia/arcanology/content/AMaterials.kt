package nichrosia.arcanology.content

import net.minecraft.util.Rarity
import nichrosia.arcanology.content.type.Content
import nichrosia.arcanology.data.MaterialHelper
import nichrosia.arcanology.energy.EnergyTier
import nichrosia.arcanology.type.element.ElementalHeart

@Suppress("MemberVisibilityCanBePrivate")
object AMaterials : Content() {
    lateinit var prismatic: MaterialHelper
    lateinit var arcane: MaterialHelper

    lateinit var nickelZinc: MaterialHelper

    override fun load() {
        prismatic = MaterialHelper("prismatic", false, Rarity.RARE)
            .addHeart("prismatic_heart", ElementalHeart.Prismatic)

        MaterialHelper("desolate", false, Rarity.RARE)
            .addHeart("desolate_heart", ElementalHeart.Prismatic)

        MaterialHelper("molten", false, Rarity.RARE)
            .addHeart("molten_heart", ElementalHeart.Prismatic)

        MaterialHelper("tidal", false, Rarity.RARE)
            .addHeart("tidal_heart", ElementalHeart.Prismatic)

        MaterialHelper("terrene", false, Rarity.RARE)
            .addHeart("terrene_heart", ElementalHeart.Prismatic)

        MaterialHelper("celestial", false, Rarity.RARE)
            .addHeart("celestial_heart", ElementalHeart.Prismatic)

        arcane = MaterialHelper("arcane", false, Rarity.EPIC)
            .addHeart("arcane_heart", ElementalHeart.Prismatic)

        MaterialHelper("velosium", false, Rarity.COMMON, 3)
            .addOre("velosium_ore",
                MaterialHelper.DimensionSelector(end = true),
                false,
                ABlockMaterials.velosium,
                2,
                AConfiguredFeatures.distanceToOreSize(2000, 2, 15),
                12 to 65,
                false,
                3,
                true,
                150f)
            .addRawOre()
            .addIngot()


        MaterialHelper("aegirite", false, Rarity.COMMON, 3)
            .addOre("aegirite_ore",
                MaterialHelper.DimensionSelector(end = true),
                false,
                ABlockMaterials.elementalCrystal,
                2,
                AConfiguredFeatures.distanceToOreSize(4000, 2, 8),
                34 to 56,
                false,
                4,
                true,
                100f)
            .addCrystal()

        MaterialHelper("xenothite", false, Rarity.UNCOMMON, 4)
            .addOre("xenothite_ore",
                MaterialHelper.DimensionSelector(end = true),
                false,
                ABlockMaterials.xenothite,
                2,
                AConfiguredFeatures.distanceToOreSize(10000, 1, 3),
                12 to 56,
                false,
                2,
                false)
            .addRawOre()
            .addIngot()

        MaterialHelper("aluminum", true, Rarity.COMMON, 2)
            .addOre("aluminum_ore",
                MaterialHelper.DimensionSelector(overworld = true),
                true,
                range = 0 to 64,
                generateToBottom = true)
            .addRawOre()
            .addIngot()
            .addWire()
            .addCircuit()

        nickelZinc = MaterialHelper("nickel_zinc", true, Rarity.COMMON, 4)
            .addBattery("nickel_zinc_battery", EnergyTier.T1)
    }
}