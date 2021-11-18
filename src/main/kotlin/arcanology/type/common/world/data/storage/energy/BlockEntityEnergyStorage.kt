package arcanology.type.common.world.data.storage.energy

import net.minecraft.block.entity.BlockEntity
import arcanology.type.common.world.data.nbt.NbtContainer
import arcanology.type.common.world.data.energy.EnergyTier

@Suppress("UnstableApiUsage")
open class BlockEntityEnergyStorage<E>(
    protected val entity: E,
    tier: EnergyTier,
    initial: Long = 0L
) : ExtensibleEnergyStorage(tier, initial) where E : BlockEntity, E : NbtContainer {
    // override it to declare it as an nbt field to allow dynamic serialization
    override var energyAmount by entity.nbtFieldOf(initial)

    override fun onFinalCommit() {
        entity.markDirty()
    }
}