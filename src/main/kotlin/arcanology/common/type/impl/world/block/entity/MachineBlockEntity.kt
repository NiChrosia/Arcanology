@file:Suppress("UnstableApiUsage")

package arcanology.common.type.impl.world.block.entity

import arcanology.common.Arcanology
import arcanology.common.type.api.world.block.entity.inventory.ItemInventory
import arcanology.common.type.impl.screen.ModularScreenHandler
import assemble.common.type.api.storage.fluid.EnergyInventory
import assemble.common.type.api.storage.fluid.ProgressInventory
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.NamedScreenHandlerFactory
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.text.LiteralText
import net.minecraft.text.Text
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

open class MachineBlockEntity(
    pos: BlockPos,
    state: BlockState,
) : BlockEntity(Arcanology.content.blockEntity.machine, pos, state), ItemInventory, ProgressInventory, EnergyInventory, NamedScreenHandlerFactory {
    override val items = emptyInventory(2)
    override var progress = 0L

    override var energy = 0L
    override val capacity = 50000L

    open val module = Arcanology.content.moduleType.itemProcessing.provider(this)

    override fun createMenu(syncId: Int, inv: PlayerInventory, player: PlayerEntity): ScreenHandler {
        return ModularScreenHandler(syncId, inv, ScreenHandlerContext.create(world, pos))
    }

    override fun getDisplayName(): Text {
        return LiteralText("")
    }

    open fun tick(world: World, pos: BlockPos, state: BlockState) {
        if (module.assembly.matches(this)) {
            module.assembly.tick(this)
        }
    }
}