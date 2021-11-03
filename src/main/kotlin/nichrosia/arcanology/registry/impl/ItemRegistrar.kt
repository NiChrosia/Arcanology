package nichrosia.arcanology.registry.impl

import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.Arcanology.Category.arcanology
import nichrosia.arcanology.registry.lang.LanguageGenerator
import nichrosia.arcanology.registry.lang.impl.BasicLanguageGenerator
import nichrosia.arcanology.type.content.item.ModeledItem
import nichrosia.common.identity.ID
import nichrosia.common.registry.type.Registrar
import nichrosia.common.registry.type.basic.BasicContentRegistrar

open class ItemRegistrar : BasicContentRegistrar<Item>() {
    val languageGenerator: LanguageGenerator = BasicLanguageGenerator()

    val techSettings: FabricItemSettings
        get() = FabricItemSettings().group(Registrar.arcanology.itemGroup.tech)

    val smelter by memberOf(Arcanology.identify("smelter")) { BlockItem(Registrar.arcanology.block.smelter, techSettings) }

    override fun <E : Item> register(input: ID, output: E): E {
        return super.register(input, output).also {
            Registry.register(Registry.ITEM, input, it)
            Arcanology.packManager.english.lang["item.${input.split(".")}"] = languageGenerator.generateLang(input)

            when(it) {
                is BlockItem -> ModeledItem.generateBlockItemModel(input)
                is ModeledItem -> it.generateModel(input)
                else -> ModeledItem.generateDefaultModel(input)
            }.forEach { (ID, model) ->
                Arcanology.packManager.main.addModel(model, ID)
            }
        }
    }
}