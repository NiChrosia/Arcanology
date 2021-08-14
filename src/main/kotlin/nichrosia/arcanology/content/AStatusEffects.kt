package nichrosia.arcanology.content

import net.minecraft.entity.effect.StatusEffect
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.content.type.RegisterableContent
import nichrosia.arcanology.type.status.ElementalWrathStatusEffect

object AStatusEffects : RegisterableContent<StatusEffect>(Registry.STATUS_EFFECT) {
    lateinit var elementalWrath: ElementalWrathStatusEffect

    override fun load() {
        elementalWrath = register(
            "elemental_wrath",
            ElementalWrathStatusEffect()
        )
    }
}