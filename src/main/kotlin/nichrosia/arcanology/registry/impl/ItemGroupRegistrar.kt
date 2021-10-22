package nichrosia.arcanology.registry.impl

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.registry.BasicRegistrar
import nichrosia.arcanology.registry.lang.LanguageGenerator
import nichrosia.arcanology.registry.lang.impl.ItemGroupLanguageGenerator
import nichrosia.arcanology.registry.properties.RegistrarProperty

open class ItemGroupRegistrar : BasicRegistrar<ItemGroup>() {
    /** The language generator for translating the ID to formatted English. This should be used in the [registry]. */
    val languageGenerator: LanguageGenerator = ItemGroupLanguageGenerator()

    val magic by RegistrarProperty("magic") { create(it) { ItemStack.EMPTY } }
    val tech by RegistrarProperty("tech") { create(it) { ItemStack.EMPTY } }

    override fun <E : ItemGroup> register(key: Identifier, value: E): E {
        val registered = super.register(key, value)

        Arcanology.packManager.englishLang.lang["itemGroup.${key.namespace}.${key.path}"] = languageGenerator.generateLang(key)

        return registered
    }

    fun create(ID: Identifier, icon: () -> ItemStack): ItemGroup {
        return super.create(ID, FabricItemGroupBuilder.create(ID)
            .icon(icon)
            .build())
    }
}