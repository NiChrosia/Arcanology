package arcanology.common.type.impl.world.entity.block

import arcanology.common.Arcanology
import arcanology.common.type.api.world.entity.block.AssemblyMachineEntity
import arcanology.common.type.impl.assembly.gradual.energy.EnergyItemProcessingAssembly
import arcanology.common.type.impl.assembly.type.gradual.energy.EnergyItemProcessingType
import arcanology.common.type.impl.gui.description.ItemProcessingDescription
import assemble.common.type.api.storage.EnergyStorageInventory
import assemble.common.type.api.storage.ItemStorageInventory
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.nbt.NbtCompound
import net.minecraft.screen.NamedScreenHandlerFactory
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.math.BlockPos

open class ItemProcessingMachine(
    pos: BlockPos,
    state: BlockState
) : AssemblyMachineEntity<ItemProcessingMachine, EnergyItemProcessingAssembly<ItemProcessingMachine>, EnergyItemProcessingType<ItemProcessingMachine>>(
    Arcanology.content.blockEntity.itemProcessingMachine,
    Arcanology.content.assemblyType.itemProcessing,
    pos,
    state
), EnergyStorageInventory, ItemStorageInventory, NamedScreenHandlerFactory {
    override val energyStorage = Arcanology.content.energyTier.standard.fullStorageOf()
    override val itemStorage = itemStorageOf(2)

    override fun createMenu(syncId: Int, inv: PlayerInventory, player: PlayerEntity): ItemProcessingDescription {
        return ItemProcessingDescription(syncId, inv, ScreenHandlerContext.create(world, pos))
    }

    override fun getDisplayName(): Text {
        return TranslatableText(cachedState.block.translationKey)
    }

    override fun writeNbt(nbt: NbtCompound) {
        super.writeNbt(nbt)

        nbt.putItemStorage("Items", itemStorage)
        nbt.putLong("Energy", energyStorage.amount)
    }

    override fun readNbt(nbt: NbtCompound) {
        super.readNbt(nbt)

        nbt.getItemStorage("Items", itemStorage)
        energyStorage.amount = nbt.getLong("Energy")
    }
}