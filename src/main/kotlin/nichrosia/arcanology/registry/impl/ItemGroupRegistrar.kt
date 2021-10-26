package nichrosia.arcanology.registry.impl

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.registry.lang.LanguageGenerator
import nichrosia.arcanology.registry.lang.impl.ItemGroupLanguageGenerator
import nichrosia.common.identity.ID
import nichrosia.registry.BasicRegistrar

open class ItemGroupRegistrar : BasicRegistrar<ItemGroup>() {
    /** The language generator for translating the ID to formatted English. This should be used in the [registry]. */
    val languageGenerator: LanguageGenerator = ItemGroupLanguageGenerator()

    val magic by memberOf(Arcanology.identify("magic")) { create(it) { ItemStack.EMPTY } }
    val tech by memberOf(Arcanology.identify("tech")) { create(it) { ItemStack.EMPTY } }

    override fun <E : ItemGroup> register(location: ID, value: E): E {
        val registered = super.register(location, value)

        Arcanology.packManager.english.lang["itemGroup.${location.split(".")}"] = languageGenerator.generateLang(location)

        return registered
    }

    fun create(ID: ID, icon: () -> ItemStack): ItemGroup {
        return FabricItemGroupBuilder.create(ID)
            .icon(icon)
            .build()
    }
}