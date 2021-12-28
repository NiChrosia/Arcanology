@file:Suppress("UnstableApiUsage")

package arcanology.common.type.impl.world.block.entity

import arcanology.common.Arcanology
import arcanology.common.type.api.world.block.entity.inventory.ItemInventory
import arcanology.common.type.api.world.block.entity.property.NbtContainer
import arcanology.common.type.api.world.block.entity.property.NbtProperty
import arcanology.common.type.impl.screen.ModularScreenHandler
import assemble.common.type.api.storage.EnergyInventory
import assemble.common.type.api.storage.ProgressInventory
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.nbt.NbtCompound
import net.minecraft.network.Packet
import net.minecraft.network.listener.ClientPlayPacketListener
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket
import net.minecraft.screen.NamedScreenHandlerFactory
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.text.LiteralText
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import team.reborn.energy.api.base.SimpleEnergyStorage

open class MachineBlockEntity(
    pos: BlockPos,
    state: BlockState,
) : BlockEntity(Arcanology.content.blockEntity.machine, pos, state), ItemInventory, ProgressInventory, EnergyInventory, NamedScreenHandlerFactory, NbtContainer {
    override val items = emptyInventory(2)
    override val properties = mutableMapOf<String, NbtProperty<*>>()

    override val energyStorage = SimpleEnergyStorage(50000L, 1000L, 1000L)
    override var progress by registered("Progress", 0L)

    open var module = Arcanology.content.moduleType.itemProcessing.provider(this)

    override fun getDisplayName() = LiteralText("")

    override fun createMenu(syncId: Int, inv: PlayerInventory, player: PlayerEntity): ScreenHandler {
        return ModularScreenHandler(syncId, inv, ScreenHandlerContext.create(world, pos))
    }

    override fun toUpdatePacket(): Packet<ClientPlayPacketListener> {
        return BlockEntityUpdateS2CPacket.create(this) {
            NbtCompound().also(this::writeNbt)
        }
    }

    override fun writeNbt(compound: NbtCompound) {
        writePropertyNbt(compound)
    }

    override fun readNbt(compound: NbtCompound) {
        readPropertyNbt(compound)
    }

    override fun markDirty() {
        super.markDirty()
    }

    override fun onOpen(player: PlayerEntity) {
        super.onOpen(player)

        if (world?.isClient == false) {
            world?.updateListeners(pos, cachedState, cachedState, Block.NOTIFY_ALL)
        }
    }

    open fun tick(world: World, pos: BlockPos, state: BlockState) {
        if (module.assembly.matches(this)) {
            module.assembly.tick(this)
        }
    }
}