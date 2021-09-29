package nichrosia.arcanology.registry.impl

import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.MiningToolItem
import net.minecraft.util.Identifier
import net.minecraft.util.Rarity
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.type.content.item.guide.book.GuideBookItem
import nichrosia.arcanology.registry.Registrar
import nichrosia.arcanology.registry.RegistryRegistrar
import nichrosia.arcanology.registry.lang.LanguageGenerator
import nichrosia.arcanology.registry.lang.impl.BasicLanguageGenerator
import nichrosia.arcanology.registry.properties.RegistryProperty
import nichrosia.arcanology.util.blockItemModel
import nichrosia.arcanology.util.handheldItemModel
import nichrosia.arcanology.util.normalItemModel

open class ItemRegistrar : RegistryRegistrar<Item>(Registry.ITEM, "item") {
    override val languageGenerator: LanguageGenerator = BasicLanguageGenerator()

    val magicSettings: FabricItemSettings get() = FabricItemSettings().group(Registrar.itemGroup.magic)
    val techSettings: FabricItemSettings get() = FabricItemSettings().group(Registrar.itemGroup.tech)

    val altar by RegistryProperty("altar") { BlockItem(Registrar.block.altar, magicSettings.rarity(Rarity.EPIC)) }
    val pulverizer by RegistryProperty("pulverizer") { BlockItem(Registrar.block.pulverizer, techSettings) }
    val runeInfuser by RegistryProperty("rune_infuser") { BlockItem(Registrar.block.runeInfuser, magicSettings.rarity(Rarity.UNCOMMON)) }

    val wireCutter by RegistryProperty("wire_cutter") { Item(techSettings) }

    val arcaneAlmanac by RegistryProperty("arcane_almanac") { GuideBookItem(magicSettings, "arcane_almanac") }
    val componentCompendium by RegistryProperty("component_compendium") { GuideBookItem(techSettings, "component_compendium") }

    override fun <E : Item> register(key: Identifier, value: E): E {
        val registered = super.register(key, value)

        when(registered) {
            is BlockItem -> blockItemModel(registered)
            is MiningToolItem -> handheldItemModel(registered)
            else -> normalItemModel(registered)
        }

        return registered
    }
}