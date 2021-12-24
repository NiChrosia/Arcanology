@file:Suppress("UnstableApiUsage")

package arcanology.type.impl.world.block.entity

import arcanology.Arcanology
import arcanology.type.api.world.block.entity.inventory.ItemInventory
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

open class MachineBlockEntity(
    pos: BlockPos,
    state: BlockState,
) : BlockEntity(Arcanology.content.blockEntity.machine, pos, state), ItemInventory {
    override val items = emptyInventory()

    open fun tick(world: World, pos: BlockPos, state: BlockState) {

    }
}