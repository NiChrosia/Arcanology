package arcanology.common.type.impl.world.storage.modular

import arcanology.common.type.impl.world.block.entity.MachineBlockEntity
import arcanology.common.type.impl.world.storage.tier.EnergyTier
import arcanology.common.type.impl.world.storage.tier.FluidTier
import assemble.common.type.api.storage.EnergyInventory
import assemble.common.type.api.storage.ItemInventory
import assemble.common.type.api.storage.fluid.FluidInventory
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack

interface ModularInventory : EnergyInventory, FluidInventory, ItemInventory, Inventory {
    override var energyStorage: ModularEnergyStorage
    override var fluidStorage: ModularFluidStorage
    override var itemStorage: ModularItemStorage

    override fun getStack(slot: Int): ItemStack {
        return itemStorage.getStack(slot)
    }

    override fun setStack(slot: Int, stack: ItemStack) {
        itemStorage.setStack(slot, stack)
    }

    override fun removeStack(slot: Int): ItemStack {
        return itemStorage.removeStack(slot)
    }

    override fun removeStack(slot: Int, amount: Int): ItemStack {
        return itemStorage.removeStack(slot, amount)
    }

    override fun clear() {
        itemStorage.clear()
    }

    override fun isEmpty(): Boolean {
        return itemStorage.isEmpty
    }

    override fun size(): Int {
        return itemStorage.size()
    }

    override fun canPlayerUse(player: PlayerEntity): Boolean {
        return itemStorage.canPlayerUse(player)
    }

    fun MachineBlockEntity.energyStorageOf(tier: EnergyTier): ModularEnergyStorage {
        return ModularEnergyStorage(tier.capacity, tier.maxInsert, tier.maxExtract)
    }

    fun MachineBlockEntity.fluidStorageOf(tier: FluidTier, size: Int): ModularFluidStorage {
        return ModularFluidStorage(0, tier.capacity)
    }

    fun MachineBlockEntity.itemStorageOf(size: Int): ModularItemStorage {
        return ModularItemStorage(size)
    }
}