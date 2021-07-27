package nichrosia.arcanology.type.blockentity

import net.minecraft.SharedConstants
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import nichrosia.arcanology.content.BlockEntityTypes
import nichrosia.arcanology.content.StatusEffects
import nichrosia.arcanology.type.status.ElementalWrathStatusEffectInstance
import java.util.*

open class ReactiveBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(BlockEntityTypes.reactiveBlockEntity, pos, state) {
    lateinit var owner: PlayerEntity

    override fun writeNbt(nbt: NbtCompound): NbtCompound {
        nbt.putBoolean("ownerHasUUID", this::owner.isInitialized)
        nbt.putUuid("ownerUUID", if (this::owner.isInitialized) owner.uuid else UUID.randomUUID())

        return super.writeNbt(nbt)
    }

    override fun readNbt(nbt: NbtCompound) {
        super.readNbt(nbt)

        val hasUUID = nbt.getBoolean("ownerHasUUID")
        val uuid = nbt.getUuid("ownerUUID")

        if (!hasUUID) return

        world?.getPlayerByUuid(uuid)?.let {
            owner = it
        }
    }

    companion object {
        fun onBreak(world: World, pos: BlockPos, playerEntity: PlayerEntity) {
            val blockEntity = (world.getBlockEntity(pos) as ReactiveBlockEntity)

            if (blockEntity::owner.isInitialized && blockEntity.owner.name.asString() == playerEntity.name.asString()) return

            playerEntity.removeStatusEffect(StatusEffects.elementalWrath)
            playerEntity.addStatusEffect(ElementalWrathStatusEffectInstance(SharedConstants.TICKS_PER_SECOND * 30, pos))
        }
    }
}