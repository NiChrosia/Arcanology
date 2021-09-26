package nichrosia.arcanology.content

import net.minecraft.util.Rarity
import nichrosia.arcanology.ctype.item.magic.RunicPickaxeItem
import nichrosia.arcanology.data.MaterialHelper
import nichrosia.arcanology.registry.Registrar
import nichrosia.arcanology.type.content.LoadableContent
import nichrosia.arcanology.type.element.Element
import nichrosia.arcanology.type.element.ElementalHeart
import nichrosia.arcanology.type.energy.EnergyTier
import nichrosia.arcanology.type.world.util.BiomeSelector
import nichrosia.arcanology.util.distanceToOreSize

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

        // Generation: Slightly rarer than gold.
        MaterialHelper("velosium", false, Rarity.COMMON, 3)
            .addVariableOre("velosium_ore",
                Registrar.blockMaterial.velosium,
                150f,
                8,
                3,
                0 to 45,
                BiomeSelector.TheEnd,
                distanceToOreSize(2000, 6, 9))
            .addRawOre()
            .addIngot()

        MaterialHelper("aegirite", false, Rarity.COMMON, 3)
            .addVariableOre("aegirite_ore",
                Registrar.blockMaterial.elementalCrystal,
                100f,
                3,
                4,
                34 to 56,
                BiomeSelector.TheEnd,
                distanceToOreSize(4000, 1, 4))
            .addCrystal()

        // Generation: Slightly rarer than diamond, more common than ancient debris. Purity (0-1) varies depending on how close the ore generated to a magical hotspot.
        MaterialHelper("xenothite", false, Rarity.UNCOMMON, 4)
            .addVariableOre("xenothite_ore",
                Registrar.blockMaterial.xenothite,
                100f,
                4,
                1,
                12 to 27,
                BiomeSelector.TheEnd,
                distanceToOreSize(10000, 2, 5))
            .addRawOre()
            .addIngot()

        MaterialHelper("aluminum", true, Rarity.COMMON, 2)
            .addOverworldDeepslateOre("aluminum_ore",
                Registrar.blockMaterial.aluminum,
                range = 0 to 64)
            .addRawOre()
            .addIngot()
            .addWire()
            .addCircuit()

        silver = MaterialHelper("silver", true, Rarity.COMMON, 2)
            .addOverworldDeepslateOre("silver_ore",
                Registrar.blockMaterial.silver,
                range = 12 to 72)
            .addRawOre()
            .addIngot()
            .addPickaxe("silver_pickaxe", RunicPickaxeItem(Registrar.toolMaterial.silver, 1, -2.9f, Registrar.item.magicSettings))

        nickelZinc = MaterialHelper("nickel_zinc", true, Rarity.COMMON, 4)
            .addBattery("nickel_zinc_battery", EnergyTier.T1)
    }
}