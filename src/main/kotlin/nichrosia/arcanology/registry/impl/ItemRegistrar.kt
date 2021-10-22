package nichrosia.arcanology.registry.impl

import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.MiningToolItem
import net.minecraft.util.Identifier
import net.minecraft.util.Rarity
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.registry.BasicRegistrar
import nichrosia.arcanology.registry.Registrar
import nichrosia.arcanology.registry.lang.LanguageGenerator
import nichrosia.arcanology.registry.lang.impl.BasicLanguageGenerator
import nichrosia.arcanology.registry.properties.RegistrarProperty
import nichrosia.arcanology.type.content.item.ModeledItem
import nichrosia.arcanology.type.content.item.guide.book.GuideBookItem
import nichrosia.arcanology.type.id.item.AbstractItem
import nichrosia.arcanology.type.id.item.IdentifiedBlockItem
import nichrosia.arcanology.type.id.item.IdentifiedItem

open class ItemRegistrar : BasicRegistrar<AbstractItem>() {
    open val languageGenerator: LanguageGenerator = BasicLanguageGenerator()

    val magicSettings: FabricItemSettings get() = FabricItemSettings().group(Registrar.itemGroup.magic)
    val techSettings: FabricItemSettings get() = FabricItemSettings().group(Registrar.itemGroup.tech)

    val altar by RegistrarProperty("altar") { IdentifiedBlockItem(Registrar.block.altar, magicSettings.rarity(Rarity.EPIC), it) }

    val separator by RegistrarProperty("separator") { IdentifiedBlockItem(Registrar.block.separator, techSettings, it) }

    val runeInfuser by RegistrarProperty("rune_infuser") { IdentifiedBlockItem(Registrar.block.runeInfuser, magicSettings.rarity(Rarity.UNCOMMON), it) }

    val wireCutter by RegistrarProperty("wire_cutter") { IdentifiedItem(techSettings, it) }

    val arcaneAlmanac by RegistrarProperty("arcane_almanac") { GuideBookItem(magicSettings, it, "arcane_almanac") }
    val componentCompendium by RegistrarProperty("component_compendium") { GuideBookItem(techSettings, it, "component_compendium") }

    open fun <E> register(key: Identifier, value: E): E where E : AbstractItem, E : Item {
        val registered = super.register(key, value)

        Registry.register(Registry.ITEM, key, registered)
        Arcanology.packManager.englishLang.lang["item.${key.namespace}.${key.path}"] = languageGenerator.generateLang(key)

        when(registered) {
            is BlockItem -> ModeledItem.generateBlockItemModel(registered, key)
            is MiningToolItem -> ModeledItem.generateHandheldModel(key)
            is ModeledItem -> registered.generateModel(key)
            else -> ModeledItem.generateDefaultModel(key)
        }

        return registered
    }

    open fun <E> create(key: Identifier, value: E): E where E : AbstractItem, E : Item {
        return super.create(key, value)
    }

    override fun <E : AbstractItem> register(key: Identifier, value: E): E {
        if (value !is Item) {
            throw IllegalArgumentException("Cannot register an AbstractItem that does not extend item.")
        } else {
            return register(key, value)
        }
    }

    override fun <E : AbstractItem> create(key: Identifier, value: E): E {
        if (value !is Item) {
            throw IllegalArgumentException("Cannot create an AbstractItem that does not extend item.")
        } else {
            return create(key, value)
        }
    }
}