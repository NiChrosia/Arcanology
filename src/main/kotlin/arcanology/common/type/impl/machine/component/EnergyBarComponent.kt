package arcanology.common.type.impl.machine.component

import arcanology.common.type.api.machine.component.ModuleComponent
import arcanology.common.type.impl.gui.widget.WEnergyBar
import arcanology.common.type.impl.world.block.entity.MachineBlockEntity
import assemble.common.type.api.assembly.GradualAssembly

open class EnergyBarComponent<A : GradualAssembly<MachineBlockEntity>>(
    machine: MachineBlockEntity,
    assembly: A,
) : ModuleComponent<A, WEnergyBar>(
    machine,
    assembly,
    WEnergyBar(machine.energyStorage::getAmount, machine.energyStorage::getCapacity),
    -1
)