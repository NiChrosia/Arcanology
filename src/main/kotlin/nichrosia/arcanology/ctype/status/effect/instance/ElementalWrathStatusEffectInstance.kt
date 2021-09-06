package nichrosia.arcanology.ctype.status.effect.instance

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.util.math.BlockPos
import nichrosia.arcanology.content.AStatusEffects
import nichrosia.arcanology.ctype.status.effect.ElementalWrathStatusEffect

open class ElementalWrathStatusEffectInstance(
    duration: Int,
    private val originPos: BlockPos
) : StatusEffectInstance(AStatusEffects.elementalWrath, duration) {
    override fun applyUpdateEffect(entity: LivingEntity) {
        val type = effectType

        if (type is ElementalWrathStatusEffect) {
            type.applyUpdateEffect(entity, amplifier, originPos)
        }
    }
}