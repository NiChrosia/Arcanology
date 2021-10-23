package nichrosia.arcanology.registry.impl

import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.registry.category.ArcanologyCategory.arcanology
import nichrosia.arcanology.type.rune.RuneType
import nichrosia.arcanology.type.rune.impl.ManaboundRuneType
import nichrosia.common.identity.ID
import nichrosia.registry.BasicRegistrar
import nichrosia.registry.Registrar

open class RuneRegistrar : BasicRegistrar<RuneType>() {
    val manabound by memberOf(ID(Arcanology.modID, "manabound")) { ManaboundRuneType() }

    @Suppress("UNUSED_EXPRESSION")
    override fun <E : RuneType> register(location: ID, value: E): E {
        val registered = super.register(location, value)

        val runeItem by Registrar.arcanology.item.memberOf(ID(Arcanology.modID, "rune_${location.path}")) { Item(Registrar.arcanology.item.magicSettings).apply {
            value.item = ItemStack(this)
        }}

        runeItem // get the property, and therefore create it

        return registered
    }
}