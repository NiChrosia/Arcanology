package arcanology.registrar.impl

import arcanology.Arcanology
import arcanology.Arcanology.arcanology
import arcanology.registrar.lang.ItemGroupLangGenerator
import arcanology.type.common.registar.lang.LangRegistrar
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
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