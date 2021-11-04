package nichrosia.arcanology.registry.impl

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.Arcanology.Category.arcanology
import nichrosia.arcanology.registry.lang.impl.BasicLanguageGenerator
import nichrosia.arcanology.util.capitalize
import nichrosia.common.identity.ID
import nichrosia.common.registry.type.Registrar
import nichrosia.common.registry.type.basic.BasicContentRegistrar

open class ItemGroupRegistrar : BasicContentRegistrar<ItemGroup>() {
    val tech by memberOf(Arcanology.identify("main")) {
        create(it) { ItemStack(Registrar.arcanology.item.smelter) }
    }

    override fun <E : ItemGroup> register(input: ID, output: E): E {
        return super.register(input, output).also {
            Arcanology.packManager.english.lang["itemGroup.${input.split(".")}"] = Arcanology.modID.capitalize()
        }
    }

    fun create(ID: ID, icon: () -> ItemStack): ItemGroup {
        return FabricItemGroupBuilder.create(ID)
            .icon(icon)
            .build()
    }
}