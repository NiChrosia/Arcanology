package nichrosia.arcanology.registry.impl

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.Arcanology.Category.arcanology
import nichrosia.arcanology.registry.lang.LanguageGenerator
import nichrosia.arcanology.registry.lang.impl.ItemGroupLanguageGenerator
import nichrosia.common.identity.ID
import nichrosia.common.registry.type.Registrar
import nichrosia.common.registry.type.basic.BasicContentRegistrar

open class ItemGroupRegistrar : BasicContentRegistrar<ItemGroup>() {
    /** The language generator for translating the ID to formatted English. This should be used in the [registry]. */
    val languageGenerator: LanguageGenerator = ItemGroupLanguageGenerator()

    val tech by memberOf(Arcanology.identify("tech")) {
        create(it) { ItemStack(Registrar.arcanology.item.smelter) }
    }

    override fun <E : ItemGroup> register(input: ID, output: E): E {
        return super.register(input, output).also {
            Arcanology.packManager.english.lang["itemGroup.${input.split(".")}"] = languageGenerator.generateLang(input)
        }
    }

    fun create(ID: ID, icon: () -> ItemStack): ItemGroup {
        return FabricItemGroupBuilder.create(ID)
            .icon(icon)
            .build()
    }
}