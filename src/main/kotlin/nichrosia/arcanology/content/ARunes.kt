package nichrosia.arcanology.content

import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.content.AItems.magicSettings
import nichrosia.arcanology.content.type.RegisterableContent
import nichrosia.arcanology.data.DataGenerator
import nichrosia.arcanology.type.rune.RuneType

object ARunes : RegisterableContent<Item>(Registry.ITEM) {
    val runes = mutableMapOf<String, ItemStack>()

    lateinit var manabound: Item

    override fun load() {
        manabound = register("manabound")

        RuneType.types.forEach {
            register(it.name)
        }
    }

    fun register(runeName: String): Item {
        val rune = register("rune_$runeName", Item(magicSettings))

        DataGenerator.normalItemModel(rune)
        DataGenerator.lang.entry("rune.${Arcanology.modID}.$runeName", runeName.capitalize())

        runes.putIfAbsent(runeName, ItemStack(rune))

        return rune
    }

    override fun <T : Item> register(identifier: Identifier, content: T): T {
        val registeredContent = super.register(identifier, content)

        val id = Registry.ITEM.getId(registeredContent)
        DataGenerator.lang.item(id, generateLang(id.path))

        return registeredContent
    }

    override fun generateLang(name: String): String {
        return name.replace("_", " ").mapWordsIndexed { index, word ->
            if (index == 0) "${word.capitalize()}:" else word.capitalize()
        }
    }
}