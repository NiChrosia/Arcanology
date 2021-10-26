package nichrosia.arcanology.registry.impl

import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
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

    val arcaneAlmanac by memberOf(Arcanology.identify("arcane_almanac")) { GuideBookItem(magicSettings, "arcane_almanac") }
    val componentCompendium by memberOf(Arcanology.identify("component_compendium")) { GuideBookItem(techSettings, "component_compendium") }

    override fun <E : Item> register(location: ID, value: E): E {
        return super.register(location, value).also {
            Registry.register(Registry.ITEM, location, it)
            Arcanology.packManager.english.lang["item.${location.split(".")}"] = languageGenerator.generateLang(location)

            when(it) {
                is BlockItem -> ModeledItem.generateBlockItemModel(location)
                is ModeledItem -> it.generateModel(location)
                else -> ModeledItem.generateDefaultModel(location)
            }.forEach { (ID, model) ->
                Arcanology.packManager.main.addModel(model, ID)
            }
        }
    }
}