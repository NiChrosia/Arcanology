package nichrosia.arcanology.type.content.status.effect.instance

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.util.math.BlockPos
import nichrosia.arcanology.type.content.status.effect.ElementalWrathStatusEffect
import nichrosia.arcanology.registry.Registrar

open class ElementalWrathStatusEffectInstance(
    duration: Int,
    private val originPos: BlockPos
) : StatusEffectInstance(Registrar.statusEffect.elementalWrath, duration) {
    override fun applyUpdateEffect(entity: LivingEntity) {
        val type = effectType

        if (type is ElementalWrathStatusEffect) {
            type.applyUpdateEffect(entity, amplifier, originPos)
        }
    }
}