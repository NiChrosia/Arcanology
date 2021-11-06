package nichrosia.arcanology.registrar.impl

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.Arcanology.arcanology
import nichrosia.arcanology.util.capitalize
import nichrosia.common.identity.ID
import nichrosia.common.record.registrar.Registrar
import nichrosia.common.record.registrar.content.ContentRegistrar

open class ItemGroupRegistrar : ContentRegistrar.Basic<ItemGroup>() {
    val main by memberOf(Arcanology.identify("main")) {
        create(it) { ItemStack(Registrar.arcanology.block.smelter.item) }
    }

    override fun <E : ItemGroup> publish(key: ID, value: E, registerIfAbsent: Boolean): E {
        return super.publish(key, value, registerIfAbsent).also {
            Arcanology.packManager.english.lang["itemGroup.${key.split(".")}"] = Arcanology.modID.capitalize()
        }
    }

    fun create(ID: ID, icon: () -> ItemStack): ItemGroup {
        return FabricItemGroupBuilder.create(ID)
            .icon(icon)
            .build()
    }
}