package nichrosia.arcanology.content

import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.type.status.ElementalWrathStatusEffect

open class StatusEffects : Loadable {
    override fun load() {
        elementalWrath = Registry.register(
            Registry.STATUS_EFFECT,
            Identifier("arcanology", "elemental_wrath"),
            ElementalWrathStatusEffect()
        )
    }

    companion object {
        lateinit var elementalWrath: ElementalWrathStatusEffect
    }
}