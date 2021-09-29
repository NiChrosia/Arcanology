package nichrosia.arcanology.registry.impl

import net.minecraft.util.Rarity
import nichrosia.arcanology.type.content.item.magic.RunicPickaxeItem
import nichrosia.arcanology.registry.BasicRegistrar
import nichrosia.arcanology.registry.Registrar
import nichrosia.arcanology.registry.properties.RegistryProperty
import nichrosia.arcanology.type.data.MaterialHelper
import nichrosia.arcanology.type.data.config.MaterialConfiguration
import nichrosia.arcanology.type.data.config.ore.impl.NormalAndDeepslateOreConfiguration
import nichrosia.arcanology.type.data.config.ore.impl.VariableOreConfiguration
import nichrosia.arcanology.type.data.config.tool.ToolMaterialConfiguration
import nichrosia.arcanology.type.element.Element
import nichrosia.arcanology.type.world.util.BiomeSelector
import nichrosia.arcanology.util.distanceToOreSize
import nichrosia.arcanology.util.pickaxeRecipe

open class MaterialRegistrar : BasicRegistrar<MaterialHelper>() {
    val prismatic by RegistryProperty("prismatic") {
        MaterialHelper(it, false, Rarity.RARE, element = Element.Light)
            .apply { add(::magicCrystal, ::magicHeart) }
    }

    val desolate by RegistryProperty("desolate") {
        MaterialHelper(it, false, Rarity.RARE,element = Element.Void)
            .apply { add(::magicCrystal, ::magicHeart) }
    }

    val molten by RegistryProperty("molten") {
        MaterialHelper(it, false, Rarity.RARE, element = Element.Fire)
            .apply { add(::magicCrystal, ::magicHeart) }
    }

    val tidal by RegistryProperty("tidal") {
        MaterialHelper(it, false, Rarity.RARE, element = Element.Water)
            .apply { add(::magicCrystal, ::magicHeart) }
    }

    val terrene by RegistryProperty("terrene") {
        MaterialHelper(it, false, Rarity.RARE, element = Element.Earth)
            .apply { add(::magicCrystal, ::magicHeart) }
    }

    val celestial by RegistryProperty("celestial") {
        MaterialHelper(it, false, Rarity.RARE, element = Element.Air)
            .apply { add(::magicCrystal, ::magicHeart) }
    }

    val arcane by RegistryProperty("arcane") {
        MaterialHelper(it, false, Rarity.EPIC, element = Element.Mana)
            .apply { add(::magicCrystal, ::magicHeart) }
    }

    // Generation: Slightly rarer than gold.
    val velosium by RegistryProperty("velosium") {
        MaterialHelper(it, false, Rarity.COMMON, 3, variableOreConfig = VariableOreConfiguration(
            "${it}_ore",
            Registrar.blockMaterial.velosium,
            150f,
            8,
            3,
            0 to 45,
            BiomeSelector.TheEnd,
            distanceToOreSize(2000, 6, 9)
        ))
            .apply { add(::ingot, ::variableOre, ::rawOreBlock, ::rawOreBlockItem, ::rawOre) }
    }

    val aegirite by RegistryProperty("aegirite") {
        MaterialHelper(it, false, Rarity.COMMON, 3, variableOreConfig = VariableOreConfiguration(
            "${it}_ore",
            Registrar.blockMaterial.elementalCrystal,
            100f,
            3,
            4,
            34 to 56,
            BiomeSelector.TheEnd,
            distanceToOreSize(4000, 1, 4)
        ))
            .apply { add(::crystal, ::variableOre) }
    }

    // Generation: Slightly rarer than diamond, more common than ancient debris. Purity (0-1) varies depending on how close the ore generated to a magical hotspot.
    val xenothite by RegistryProperty("xenothite") {
        MaterialHelper(it, false, Rarity.UNCOMMON, 4, variableOreConfig = VariableOreConfiguration(
            "${it}_ore",
            Registrar.blockMaterial.xenothite,
            100f,
            4,
            1,
            12 to 27,
            BiomeSelector.TheEnd,
            distanceToOreSize(10000, 2, 5)
        ))
            .apply { add(::ingot, ::variableOre, ::rawOreBlock, ::rawOreBlockItem, ::rawOre) }
    }

    val aluminum by RegistryProperty("aluminum") {
        MaterialHelper(it, true, Rarity.COMMON, 2, normalAndDeepslateOreConfig = NormalAndDeepslateOreConfiguration(
            "${it}_ore",
            Registrar.blockMaterial.aluminum,
            range = 0 to 64
        ))
            .apply { add(::ingot, ::wire, ::circuit, ::normalAndDeepslateOre, ::rawOreBlock, ::rawOreBlockItem, ::rawOre) }
    }

    val silver by RegistryProperty("silver") {
        MaterialHelper(
            it,
            false,
            Rarity.COMMON,
            2,
            toolMaterialConfig = ToolMaterialConfiguration("${it}_material", 5f, 350, 25, 2, 6f),
            pickaxeConfig = MaterialConfiguration("${it}_pickaxe", Registrar.item, {
                pickaxeRecipe(it, ingot, toolRod)
            }) { RunicPickaxeItem(toolMaterial, 1, -2.9f, settings) },
            normalAndDeepslateOreConfig = NormalAndDeepslateOreConfiguration(
                "${it}_ore",
                Registrar.blockMaterial.silver,
                range = 12 to 72
            )
        )
        .apply { add(::ingot, ::pickaxe, ::normalAndDeepslateOre, ::rawOreBlock, ::rawOreBlockItem, ::rawOre) }
    }

    val nickelZinc by RegistryProperty("nickel_zinc") {
        MaterialHelper(it, true, Rarity.COMMON, 4)
            .apply { add(::battery) }
    }
}