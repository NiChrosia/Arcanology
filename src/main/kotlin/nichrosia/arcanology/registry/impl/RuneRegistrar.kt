package nichrosia.arcanology.registry.impl

import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.registry.BasicRegistrar
import nichrosia.arcanology.registry.Registrar
import nichrosia.arcanology.registry.properties.RegistryProperty
import nichrosia.arcanology.type.rune.RuneType
import nichrosia.arcanology.type.rune.impl.ManaboundRuneType

open class RuneRegistrar : BasicRegistrar<RuneType>() {
    val manabound by RegistryProperty(Arcanology.idOf("manabound")) { ManaboundRuneType() }

    override fun <E : RuneType> register(key: Identifier, value: E): E {
        val registered = super.register(key, value)

        Registrar.item.create("rune_${key.path}", Item(Registrar.item.magicSettings)).apply {
            value.item = ItemStack(this)
        }

        return registered
    }
}