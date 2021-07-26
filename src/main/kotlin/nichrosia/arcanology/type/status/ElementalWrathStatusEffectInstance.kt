package nichrosia.arcanology.type.status

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.util.math.BlockPos
import nichrosia.arcanology.content.StatusEffects

open class ElementalWrathStatusEffectInstance(
    duration: Int,
    private val originPos: BlockPos
) : StatusEffectInstance(StatusEffects.elementalWrath, duration) {
    override fun applyUpdateEffect(entity: LivingEntity) {
        val type = effectType

        if (type is ElementalWrathStatusEffect) {
            type.applyUpdateEffect(entity, amplifier, originPos)
        }
    }
}