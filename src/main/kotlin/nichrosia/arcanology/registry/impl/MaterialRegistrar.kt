package nichrosia.arcanology.registry.impl

import net.minecraft.util.Rarity
import nichrosia.arcanology.type.content.item.magic.RunicPickaxeItem
import nichrosia.arcanology.registry.BasicRegistrar
import nichrosia.arcanology.registry.Registrar
import nichrosia.arcanology.registry.properties.RegistrarProperty
import nichrosia.arcanology.type.data.MaterialHelper
import nichrosia.arcanology.type.data.config.MaterialConfig
import nichrosia.arcanology.type.data.config.ore.impl.NormalAndDeepslateOreConfig
import nichrosia.arcanology.type.data.config.ore.impl.VariableOreConfig
import nichrosia.arcanology.type.data.config.tool.ToolMaterialConfig
import nichrosia.arcanology.type.element.Element
import nichrosia.arcanology.type.world.util.BiomeSelector
import nichrosia.arcanology.util.distanceToOreSize
import nichrosia.arcanology.util.pickaxeRecipe

open class MaterialRegistrar : BasicRegistrar<MaterialHelper>() {
    val prismatic by RegistrarProperty("prismatic") {
        MaterialHelper(it, false, Rarity.RARE, element = Element.Light)
            .apply { add(::magicCrystal, ::magicHeart) }
    }

    val desolate by RegistrarProperty("desolate") {
        MaterialHelper(it, false, Rarity.RARE,element = Element.Void)
            .apply { add(::magicCrystal, ::magicHeart) }
    }

    val molten by RegistrarProperty("molten") {
        MaterialHelper(it, false, Rarity.RARE, element = Element.Fire)
            .apply { add(::magicCrystal, ::magicHeart) }
    }

    val tidal by RegistrarProperty("tidal") {
        MaterialHelper(it, false, Rarity.RARE, element = Element.Water)
            .apply { add(::magicCrystal, ::magicHeart) }
    }

    val terrene by RegistrarProperty("terrene") {
        MaterialHelper(it, false, Rarity.RARE, element = Element.Earth)
            .apply { add(::magicCrystal, ::magicHeart) }
    }

    val celestial by RegistrarProperty("celestial") {
        MaterialHelper(it, false, Rarity.RARE, element = Element.Air)
            .apply { add(::magicCrystal, ::magicHeart) }
    }

    val arcane by RegistrarProperty("arcane") {
        MaterialHelper(it, false, Rarity.EPIC, element = Element.Mana)
            .apply { add(::magicCrystal, ::magicHeart) }
    }

    // Generation: Slightly rarer than gold.
    val velosium by RegistrarProperty("velosium") {
        MaterialHelper(it, false, Rarity.COMMON, 3, variableOreConfig = VariableOreConfig(
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

    val aegirite by RegistrarProperty("aegirite") {
        MaterialHelper(it, false, Rarity.COMMON, 3, variableOreConfig = VariableOreConfig(
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
    val xenothite by RegistrarProperty("xenothite") {
        MaterialHelper(it, false, Rarity.UNCOMMON, 4, variableOreConfig = VariableOreConfig(
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

    val aluminum by RegistrarProperty("aluminum") {
        MaterialHelper(it, true, Rarity.COMMON, 2, normalAndDeepslateOreConfig = NormalAndDeepslateOreConfig(
            "${it}_ore",
            Registrar.blockMaterial.aluminum,
            range = 0 to 64
        ))
            .apply { add(::ingot, ::wire, ::circuit, ::normalAndDeepslateOre, ::rawOreBlock, ::rawOreBlockItem, ::rawOre) }
    }

    val silver by RegistrarProperty("silver") {
        MaterialHelper(
            it,
            false,
            Rarity.COMMON,
            2,
            toolMaterialConfig = ToolMaterialConfig("${it}_material", 5f, 350, 25, 2, 6f),
            pickaxeConfig = MaterialConfig("${it}_pickaxe", Registrar.item, { pickaxe ->
                pickaxeRecipe(pickaxe, ingot, toolRod)
            }) { RunicPickaxeItem(toolMaterial, 1, -2.9f, settings) },
            normalAndDeepslateOreConfig = NormalAndDeepslateOreConfig(
                "${it}_ore",
                Registrar.blockMaterial.silver,
                range = 12 to 72
            )
        )
        .apply { add(::ingot, ::pickaxe, ::normalAndDeepslateOre, ::rawOreBlock, ::rawOreBlockItem, ::rawOre) }
    }

    val nickelZinc by RegistrarProperty("nickel_zinc") {
        MaterialHelper(it, true, Rarity.COMMON, 4)
            .apply { add(::battery) }
    }
}