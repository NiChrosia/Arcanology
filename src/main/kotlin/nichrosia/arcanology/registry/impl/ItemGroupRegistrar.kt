package nichrosia.arcanology.registry.impl

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.registry.BasicRegistrar
import nichrosia.arcanology.registry.lang.LanguageGenerator
import nichrosia.arcanology.registry.lang.impl.ItemGroupLanguageGenerator
import nichrosia.arcanology.registry.properties.RegistryProperty

open class ItemGroupRegistrar : BasicRegistrar<ItemGroup>() {
    /** The language generator for translating the ID to formatted English. This should be used in the [registry]. */
    val languageGenerator: LanguageGenerator = ItemGroupLanguageGenerator()

    val magic by RegistryProperty("magic") { create(it) { ItemStack.EMPTY } }
    val tech by RegistryProperty("tech") { create(it) { ItemStack.EMPTY } }

    override fun <E : ItemGroup> register(key: Identifier, value: E): E {
        val registered = super.register(key, value)

        Arcanology.packManager.englishLang.lang["itemGroup.${key.namespace}.${key.path}"] = languageGenerator.generateLang(key)

        return registered
    }

    fun create(name: String, icon: () -> ItemStack): ItemGroup {
        return super.create(name, FabricItemGroupBuilder.create(Arcanology.idOf(name))
            .icon(icon)
            .build())
    }
}