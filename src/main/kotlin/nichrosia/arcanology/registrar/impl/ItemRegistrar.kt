package nichrosia.arcanology.registrar.impl

import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.Arcanology.arcanology
import nichrosia.arcanology.registrar.lang.LanguageGenerator
import nichrosia.arcanology.registrar.lang.impl.BasicLanguageGenerator
import nichrosia.arcanology.type.content.item.ModeledItem
import nichrosia.arcanology.type.registar.registry.VanillaRegistrar
import nichrosia.common.identity.ID
import nichrosia.common.record.registrar.Registrar

open class ItemRegistrar : VanillaRegistrar.Basic<Item>(Registry.ITEM) {
    val languageGenerator: LanguageGenerator = BasicLanguageGenerator()

    val settings: FabricItemSettings
        get() = FabricItemSettings().group(Registrar.arcanology.itemGroup.main)

    override fun <E : Item> publish(key: ID, value: E): E {
        return super.publish(key, value).also {
            Arcanology.packManager.english.lang["item.${key.split(".")}"] = languageGenerator.generateLang(key)

            when(it) {
                is BlockItem -> ModeledItem.generateBlockItemModel(key)
                is ModeledItem -> it.generateModel(key)
                else -> ModeledItem.generateDefaultModel(key)
            }.forEach { (ID, model) ->
                Arcanology.packManager.main.addModel(model, ID)
            }
        }
    }
}