package nichrosia.arcanology.type.content.block.entity.storage

import net.minecraft.block.entity.BlockEntity
import nichrosia.arcanology.type.energy.EnergyTier
import nichrosia.arcanology.type.nbt.NbtContainer
import nichrosia.arcanology.type.storage.energy.ExtensibleEnergyStorage

@Suppress("UnstableApiUsage")
open class BlockEntityEnergyStorage<E>(
    val entity: E,
    tier: EnergyTier,
    initial: Long = 0L
) : ExtensibleEnergyStorage(
    tier.storage,
    tier.maxInputSpeed,
    tier.maxOutputSpeed
) where E : BlockEntity, E : NbtContainer {
    // override it to declare it as an nbt field to allow dynamic serialization
    override var energyAmount by entity.nbtFieldOf(initial)

    override fun onFinalCommit() {
        entity.markDirty()
    }
}