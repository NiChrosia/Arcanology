package nichrosia.arcanology.registry.impl

import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.registry.BasicRegistrar
import nichrosia.arcanology.registry.Registrar
import nichrosia.arcanology.registry.properties.ExternalRegistrarProperty
import nichrosia.arcanology.registry.properties.RegistrarProperty
import nichrosia.arcanology.type.id.item.IdentifiedItem
import nichrosia.arcanology.type.rune.RuneType
import nichrosia.arcanology.type.rune.impl.ManaboundRuneType

open class RuneRegistrar : BasicRegistrar<RuneType>() {
    val manabound by RegistrarProperty(Arcanology.idOf("manabound")) { ManaboundRuneType() }

    @Suppress("UNUSED_EXPRESSION")
    override fun <E : RuneType> register(key: Identifier, value: E): E {
        val registered = super.register(key, value)

        val runeItem by ExternalRegistrarProperty(Registrar.item, "rune_${key.path}") { IdentifiedItem(Registrar.item.magicSettings, it).apply {
            value.item = ItemStack(this)
        }}

        runeItem // get the property, and therefore create it

        return registered
    }
}