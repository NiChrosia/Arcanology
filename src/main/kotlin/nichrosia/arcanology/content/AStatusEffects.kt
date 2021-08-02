package nichrosia.arcanology.content

import net.minecraft.util.registry.Registry
import nichrosia.arcanology.type.status.ElementalWrathStatusEffect

object AStatusEffects : ArcanologyContent() {
    lateinit var elementalWrath: ElementalWrathStatusEffect

    override fun load() {
        elementalWrath = Registry.register(
            Registry.STATUS_EFFECT,
            getIdentifier("elemental_wrath"),
            ElementalWrathStatusEffect()
        )
    }

    override fun getAll(): Array<Any> {
        return arrayOf(
            elementalWrath
        )
    }
}