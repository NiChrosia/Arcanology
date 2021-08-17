package nichrosia.arcanology.content

import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.content.AItems.magicSettings
import nichrosia.arcanology.content.type.RegisterableContent
import nichrosia.arcanology.data.DataGenerator
import nichrosia.arcanology.type.rune.base.RuneType

object ARunes : RegisterableContent<Item>(Registry.ITEM) {
    val runes = mutableMapOf<String, ItemStack>()

    override fun load() {
        register("manabound")

        RuneType.types.forEach {
            register(it.name)
        }
    }

    fun register(runeName: String) {
        val rune = register("rune_$runeName", Item(magicSettings))

        DataGenerator.normalItemModel(rune)

        runes.putIfAbsent(runeName, ItemStack(rune))
    }
}