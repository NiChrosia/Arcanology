@file:Suppress("UnstableApiUsage")

package arcanology.common.type.impl.world.block.entity

import arcanology.common.Arcanology
import arcanology.common.type.api.machine.module.ModuleType
import arcanology.common.type.api.world.block.entity.property.NbtContainer
import arcanology.common.type.api.world.block.entity.property.NbtProperty
import arcanology.common.type.impl.nbt.serializer.ModularEnergyStorageSerializer
import arcanology.common.type.impl.nbt.serializer.ModularFluidStorageSerializer
import arcanology.common.type.impl.screen.ModularScreenHandler
import arcanology.common.type.impl.world.storage.modular.ModularInventory
import assemble.common.type.api.assembly.GradualAssembly
import assemble.common.type.api.storage.ProgressInventory
import dev.nathanpb.ktdatatag.serializer.Serializers
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

open class MachineBlockEntity(
    pos: BlockPos,
    state: BlockState,
) : BlockEntity(Arcanology.content.blockEntity.machine, pos, state), ModularInventory, ProgressInventory, NamedScreenHandlerFactory, NbtContainer {
    override val properties = mutableMapOf<String, NbtProperty<*>>()

    override var energyStorage by registered("EnergyStorage", ModularEnergyStorageSerializer(Arcanology.content.energyTier.standard), energyStorageOf(Arcanology.content.energyTier.standard))
    override var fluidStorage by registered("FluidStorage", ModularFluidStorageSerializer(Arcanology.content.fluidTier.standard), fluidStorageOf(Arcanology.content.fluidTier.standard, 0))
    override var itemStorage by registered("ItemStorage", Arcanology.content.nbtSerializer.modularItemStorage, itemStorageOf(0))

    override var progress by registered("Progress", Serializers.LONG, 0L)
    open var moduleType by registered("ModuleType", Arcanology.content.nbtSerializer.moduleType, Arcanology.content.moduleType.blank)

    open var module = moduleType.of(this)
        protected set

    init {
        setModule(Arcanology.content.moduleType.itemProcessing)

        energyStorage.amount = energyStorage.capacity
    }

    override fun getDisplayName() = LiteralText("")

    override fun createMenu(syncId: Int, inv: PlayerInventory, player: PlayerEntity): ScreenHandler {
        return ModularScreenHandler(syncId, inv, ScreenHandlerContext.create(world, pos)).also {
            if (world?.isClient == false) {
                world?.updateListeners(pos, cachedState, cachedState, Block.NOTIFY_ALL)
            }
        }
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

    open fun tick(world: World, pos: BlockPos, state: BlockState) {
        if (module.assembly.matches(this)) {
            module.assembly.tick(this)
        }
    }

    open fun setModule(type: ModuleType<out GradualAssembly<MachineBlockEntity>>) {
        moduleType = type
        module = type.of(this)
    }
}