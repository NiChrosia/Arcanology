package nichrosia.arcanology.content

import net.minecraft.util.Rarity
import nichrosia.arcanology.ctype.item.magic.RunicPickaxeItem
import nichrosia.arcanology.data.MaterialHelper
import nichrosia.arcanology.data.config.MaterialConfiguration
import nichrosia.arcanology.data.config.ore.impl.NormalAndDeepslateOreConfiguration
import nichrosia.arcanology.data.config.ore.impl.VariableOreConfiguration
import nichrosia.arcanology.registry.Registrar
import nichrosia.arcanology.type.content.LoadableContent
import nichrosia.arcanology.type.element.Element
import nichrosia.arcanology.type.world.util.BiomeSelector
import nichrosia.arcanology.util.distanceToOreSize
import nichrosia.arcanology.util.pickaxeRecipe

@Suppress("MemberVisibilityCanBePrivate")
object AMaterials : LoadableContent {
    lateinit var prismatic: MaterialHelper
    lateinit var terrene: MaterialHelper
    lateinit var arcane: MaterialHelper

    lateinit var nickelZinc: MaterialHelper

    lateinit var silver: MaterialHelper

    override fun load() {
        prismatic = MaterialHelper("prismatic", false, Rarity.RARE, element = Element.Light)
            .apply { add(::magicCrystal, ::magicHeart) }

        MaterialHelper("desolate", false, Rarity.RARE,element = Element.Void)
            .apply { add(::magicCrystal, ::magicHeart) }

        MaterialHelper("molten", false, Rarity.RARE, element = Element.Fire)
            .apply { add(::magicCrystal, ::magicHeart) }

        MaterialHelper("tidal", false, Rarity.RARE, element = Element.Water)
            .apply { add(::magicCrystal, ::magicHeart) }

        terrene = MaterialHelper("terrene", false, Rarity.RARE, element = Element.Earth)
            .apply { add(::magicCrystal, ::magicHeart) }

        MaterialHelper("celestial", false, Rarity.RARE, element = Element.Air)
            .apply { add(::magicCrystal, ::magicHeart) }

        arcane = MaterialHelper("arcane", false, Rarity.EPIC, element = Element.Mana)
            .apply { add(::magicCrystal, ::magicHeart) }

        // Generation: Slightly rarer than gold.
        MaterialHelper("velosium", false, Rarity.COMMON, 3, variableOreConfig = VariableOreConfiguration(
            "velosium_ore",
            Registrar.blockMaterial.velosium,
            150f,
            8,
            3,
            0 to 45,
            BiomeSelector.TheEnd,
            distanceToOreSize(2000, 6, 9)
        ))
            .apply { add(::ingot, ::variableOre, ::rawOreBlock, ::rawOreBlockItem, ::rawOre) }

        MaterialHelper("aegirite", false, Rarity.COMMON, 3, variableOreConfig = VariableOreConfiguration(
            "aegirite_ore",
            Registrar.blockMaterial.elementalCrystal,
            100f,
            3,
            4,
            34 to 56,
            BiomeSelector.TheEnd,
            distanceToOreSize(4000, 1, 4)
        ))
            .apply { add(::crystal, ::variableOre) }

        // Generation: Slightly rarer than diamond, more common than ancient debris. Purity (0-1) varies depending on how close the ore generated to a magical hotspot.
        MaterialHelper("xenothite", false, Rarity.UNCOMMON, 4, variableOreConfig = VariableOreConfiguration(
            "xenothite_ore",
            Registrar.blockMaterial.xenothite,
            100f,
            4,
            1,
            12 to 27,
            BiomeSelector.TheEnd,
            distanceToOreSize(10000, 2, 5)
        ))
            .apply { add(::ingot, ::variableOre, ::rawOreBlock, ::rawOreBlockItem, ::rawOre) }

        MaterialHelper("aluminum", true, Rarity.COMMON, 2, normalAndDeepslateOreConfig = NormalAndDeepslateOreConfiguration(
            "aluminum_ore",
            Registrar.blockMaterial.aluminum,
            range = 0 to 64
        ))
            .apply { add(::ingot, ::wire, ::circuit, ::normalAndDeepslateOre, ::rawOreBlock, ::rawOreBlockItem, ::rawOre) }

        silver = MaterialHelper(
            "silver",
            false,
            Rarity.COMMON,
            2,
            pickaxeConfig = MaterialConfiguration("silver_pickaxe", Registrar.item, {
                pickaxeRecipe(it, ingot, toolRod)
            }) { RunicPickaxeItem(Registrar.toolMaterial.silver, 1, -2.9f, settings) },
            normalAndDeepslateOreConfig = NormalAndDeepslateOreConfiguration(
                "silver_ore",
                Registrar.blockMaterial.silver,
                range = 12 to 72
            )
        )
            .apply { add(::ingot, ::pickaxe, ::normalAndDeepslateOre, ::rawOreBlock, ::rawOreBlockItem, ::rawOre) }

        nickelZinc = MaterialHelper("nickel_zinc", true, Rarity.COMMON, 4)
            .apply { add(::battery) }
    }
}