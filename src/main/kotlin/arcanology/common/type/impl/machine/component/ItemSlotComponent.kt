package arcanology.common.type.impl.machine.component

import arcanology.common.type.api.machine.component.ModuleComponent
import arcanology.common.type.impl.world.block.entity.MachineBlockEntity
import assemble.common.type.api.assembly.GradualAssembly
import assemble.common.type.impl.assembly.slot.item.ItemStorageInput
import io.github.cottonmc.cotton.gui.widget.WItemSlot
import net.minecraft.item.ItemStack

open class ItemSlotComponent<A : GradualAssembly<MachineBlockEntity>>(
    machine: MachineBlockEntity,
    assembly: A,
    val slot: Int,
    val slotsWide: Int = 1,
    val slotsHigh: Int = 1,
    val big: Boolean = false,
    val filter: (ItemStack) -> Boolean = { true },
    val allowInsertion: Boolean = true
) : ModuleComponent<A, WItemSlot>(
    machine,
    assembly,
    WItemSlot(machine, slot, slotsWide, slotsHigh, big)
        .setFilter(filter)
        .setInsertingAllowed(allowInsertion)
) {
    companion object {
        /** Returns an item slot component with input only allowed for ingredients matching the equivalent assembly slot. */
        fun <A : GradualAssembly<MachineBlockEntity>> ofInput(
            machine: MachineBlockEntity,
            assembly: A,
            slot: Int,
            slotsWide: Int = 1,
            slotsHigh: Int = 1,
            big: Boolean = false,
        ): ItemSlotComponent<A> {
            return ItemSlotComponent(machine, assembly, slot, slotsWide, slotsHigh, big, {
                val inputs = assembly.inputs.filterIsInstance<ItemStorageInput<MachineBlockEntity>>()
                val input = inputs.first { it.slot == slot }

                input.stack.type.test(it)
            })
        }

        /** Returns an item slot component with insertion disabled. */
        fun <A : GradualAssembly<MachineBlockEntity>> ofOutput(
            machine: MachineBlockEntity,
            assembly: A,
            slot: Int,
            slotsWide: Int = 1,
            slotsHigh: Int = 1,
            big: Boolean = false
        ): ItemSlotComponent<A> {
            return ItemSlotComponent(machine, assembly, slot, slotsWide, slotsHigh, big, allowInsertion = false)
        }
    }
}