package nichrosia.arcanology.ctype.status.effect

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.attribute.AttributeContainer
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectType
import net.minecraft.particle.ParticleTypes
import net.minecraft.util.math.BlockPos
import nichrosia.arcanology.func.clamp

open class ElementalWrathStatusEffect : StatusEffect(StatusEffectType.HARMFUL, 0xBD00DE) {
    private var damageCounter = 0
    private val ticksPerDamage = 5

    override fun canApplyUpdateEffect(duration: Int, amplifier: Int): Boolean {
        return true
    }

    override fun onRemoved(entity: LivingEntity, attributes: AttributeContainer, amplifier: Int) {
        super.onRemoved(entity, attributes, amplifier)
    }

    open fun applyUpdateEffect(entity: LivingEntity, amplifier: Int, origin: BlockPos) {
        super.applyUpdateEffect(entity, amplifier)

        val multiplier = ((15.0 - entity.squaredDistanceTo(
            origin.x.toDouble(),
            origin.y.toDouble(),
            origin.z.toDouble()
        )) / 5.0).clamp(0.0, 3.0)

        damageCounter++

        if (damageCounter >= ticksPerDamage) {
            entity.damage(DamageSource.MAGIC, (1 shl amplifier).toFloat() * multiplier.toFloat())

            damageCounter = 0
        }

        entity.world.addParticle(
            ParticleTypes.ENCHANTED_HIT,
            false,
            origin.x.toDouble(),
            origin.y.toDouble(),
            origin.z.toDouble(),
            0.0,
            0.0,
            0.0
        )
    }
}