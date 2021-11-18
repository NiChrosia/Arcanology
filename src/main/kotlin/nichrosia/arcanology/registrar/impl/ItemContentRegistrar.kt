package nichrosia.arcanology.registrar.impl

import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.Arcanology.arcanology
import nichrosia.arcanology.type.registar.lang.VanillaLangRegistrar
import nichrosia.arcanology.type.world.item.ModeledItem
import nichrosia.common.identity.ID
import nichrosia.common.record.registrar.Registrar

open class ItemContentRegistrar : VanillaLangRegistrar.Basic<Item>(Registry.ITEM, "item") {
    val settings: FabricItemSettings
        get() = FabricItemSettings().group(Registrar.arcanology.itemGroup.main)

    // crafting ingredients
    val silicon by memberOf(Arcanology.identify("silicon")) { Item(settings) }

    val machineCore by memberOf(Arcanology.identify("machine_core")) { Item(settings) }

    // metals
    val palladiumIngot by memberOf(Arcanology.identify("palladium_ingot")) { Item(settings) }
    val palladiumNugget by memberOf(Arcanology.identify("palladium_nugget")) { Item(settings) }

    override fun <E : Item> publish(key: ID, value: E): E {
        return super.publish(key, value).also {
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