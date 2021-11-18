package nichrosia.arcanology.registrar.impl

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.Arcanology.arcanology
import nichrosia.arcanology.registrar.lang.ItemGroupLangGenerator
import nichrosia.arcanology.type.registar.lang.LangRegistrar
import nichrosia.common.identity.ID
import nichrosia.common.record.registrar.Registrar

open class ItemGroupContentRegistrar : LangRegistrar.Basic<ItemGroup>("itemGroup") {
    override val langGenerator = ItemGroupLangGenerator()

    val main by memberOf(Arcanology.identify("main")) {
        create(it) { ItemStack(Registrar.arcanology.block.smelter.item) }
    }

    fun create(ID: ID, icon: () -> ItemStack = ItemStack::EMPTY): ItemGroup {
        return FabricItemGroupBuilder.create(ID)
            .icon(icon)
            .build()
    }
}