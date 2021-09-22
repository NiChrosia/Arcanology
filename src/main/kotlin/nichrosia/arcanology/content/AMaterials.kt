package nichrosia.arcanology.content

import net.minecraft.util.Rarity
import nichrosia.arcanology.type.content.LoadableContent
import nichrosia.arcanology.ctype.item.magic.RunicPickaxeItem
import nichrosia.arcanology.data.MaterialHelper
import nichrosia.arcanology.registry.Registrar
import nichrosia.arcanology.type.energy.EnergyTier
import nichrosia.arcanology.type.element.Element
import nichrosia.arcanology.type.element.ElementalHeart

@Suppress("MemberVisibilityCanBePrivate")
object AMaterials : LoadableContent {
    lateinit var prismatic: MaterialHelper
    lateinit var terrene: MaterialHelper
    lateinit var arcane: MaterialHelper

    lateinit var nickelZinc: MaterialHelper

    lateinit var silver: MaterialHelper

    override fun load() {
        prismatic = MaterialHelper("prismatic", false, Rarity.RARE)
            .addHeart("prismatic_heart", ElementalHeart.Prismatic)
            .addMagicCrystal(element = Element.Light)

        MaterialHelper("desolate", false, Rarity.RARE)
            .addHeart("desolate_heart", ElementalHeart.Prismatic)
            .addMagicCrystal(element = Element.Void)

        MaterialHelper("molten", false, Rarity.RARE)
            .addHeart("molten_heart", ElementalHeart.Prismatic)
            .addMagicCrystal(element = Element.Fire)

        MaterialHelper("tidal", false, Rarity.RARE)
            .addHeart("tidal_heart", ElementalHeart.Prismatic)
            .addMagicCrystal(element = Element.Water)

        terrene = MaterialHelper("terrene", false, Rarity.RARE)
            .addHeart("terrene_heart", ElementalHeart.Prismatic)
            .addMagicCrystal(element = Element.Earth)

        MaterialHelper("celestial", false, Rarity.RARE)
            .addHeart("celestial_heart", ElementalHeart.Prismatic)
            .addMagicCrystal(element = Element.Air)

        arcane = MaterialHelper("arcane", false, Rarity.EPIC)
            .addHeart("arcane_heart", ElementalHeart.Prismatic)
            .addMagicCrystal(element = Element.Mana)

        MaterialHelper("velosium", false, Rarity.COMMON, 3)
            .addOre("velosium_ore",
                MaterialHelper.DimensionSelector(end = true),
                false,
                Registrar.blockMaterial.velosium,
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
                Registrar.blockMaterial.elementalCrystal,
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
                Registrar.blockMaterial.xenothite,
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
                Registrar.blockMaterial.aluminum,
                range = 0 to 64,
                generateToBottom = true)
            .addRawOre()
            .addIngot()
            .addWire()
            .addCircuit()

        silver = MaterialHelper("silver", true, Rarity.COMMON, 2)
            .addOre("silver_ore",
                MaterialHelper.DimensionSelector(overworld = true),
                true,
                Registrar.blockMaterial.silver,
                range = 12 to 72)
            .addRawOre()
            .addIngot()
            .addPickaxe("silver_pickaxe", RunicPickaxeItem(Registrar.toolMaterial.silver, 1, -2.9f, Registrar.item.magicSettings))

        nickelZinc = MaterialHelper("nickel_zinc", true, Rarity.COMMON, 4)
            .addBattery("nickel_zinc_battery", EnergyTier.T1)
    }
}