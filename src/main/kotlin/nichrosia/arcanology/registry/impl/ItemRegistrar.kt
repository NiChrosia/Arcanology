package nichrosia.arcanology.registry.impl

import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.MiningToolItem
import net.minecraft.util.Rarity
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.registry.category.ArcanologyCategory.arcanology
import nichrosia.arcanology.registry.lang.LanguageGenerator
import nichrosia.arcanology.registry.lang.impl.BasicLanguageGenerator
import nichrosia.arcanology.type.content.item.ModeledItem
import nichrosia.arcanology.type.content.item.guide.book.GuideBookItem
import nichrosia.common.identity.ID
import nichrosia.registry.BasicRegistrar
import nichrosia.registry.Registrar

open class ItemRegistrar : BasicRegistrar<Item>() {
    val languageGenerator: LanguageGenerator = BasicLanguageGenerator()

    val magicSettings: FabricItemSettings get() = FabricItemSettings().group(Registrar.arcanology.itemGroup.magic)
    val techSettings: FabricItemSettings get() = FabricItemSettings().group(Registrar.arcanology.itemGroup.tech)

    val altar by memberOf(ID(Arcanology.modID, "altar")) { BlockItem(Registrar.arcanology.block.altar, magicSettings.rarity(Rarity.EPIC)) }

    val separator by memberOf(ID(Arcanology.modID, "separator")) { BlockItem(Registrar.arcanology.block.separator, techSettings) }

    val runeInfuser by memberOf(ID(Arcanology.modID, "rune_infuser")) { BlockItem(Registrar.arcanology.block.runeInfuser, magicSettings.rarity(Rarity.UNCOMMON)) }

    val wireCutter by memberOf(ID(Arcanology.modID, "wire_cutter")) { Item(techSettings) }

    val arcaneAlmanac by memberOf(ID(Arcanology.modID, "arcane_almanac")) { GuideBookItem(magicSettings, "arcane_almanac") }
    val componentCompendium by memberOf(ID(Arcanology.modID, "component_compendium")) { GuideBookItem(techSettings, "component_compendium") }

    override fun <E : Item> register(location: ID, value: E): E {
        return super.register(location, value).also {
            Registry.register(Registry.ITEM, location.asIdentifier, it)
            Arcanology.packManager.englishLang.lang["item.${location.split(".")}"] = languageGenerator.generateLang(location)

            when(it) {
                is BlockItem -> ModeledItem.generateBlockItemModel(it, location)
                is MiningToolItem -> ModeledItem.generateHandheldModel(location)
                is ModeledItem -> it.generateModel(location)
                else -> ModeledItem.generateDefaultModel(location)
            }
        }
    }
}