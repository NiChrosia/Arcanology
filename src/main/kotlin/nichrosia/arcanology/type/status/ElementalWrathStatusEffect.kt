package nichrosia.arcanology.type.status

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectType
import net.minecraft.util.math.BlockPos
import nichrosia.arcanology.math.clamp

open class ElementalWrathStatusEffect : StatusEffect(StatusEffectType.HARMFUL, 0xBD00DE) {
    private var damageCounter = 0
    private val ticksPerDamage = 5

    override fun canApplyUpdateEffect(duration: Int, amplifier: Int): Boolean {
        return true
    }

    open fun applyUpdateEffect(entity: LivingEntity, amplifier: Int, origin: BlockPos) {
        super.applyUpdateEffect(entity, amplifier)

        val multiplier = clamp(
            (10.0 - entity.squaredDistanceTo(origin.x.toDouble(), origin.y.toDouble(), origin.z.toDouble())) / 5.0,
            0.0,
            2.0
        )

        damageCounter++

        if (damageCounter >= ticksPerDamage) {
            entity.damage(DamageSource.MAGIC, (1 shl amplifier).toFloat() * multiplier.toFloat())

            damageCounter = 0
        }
    }
}