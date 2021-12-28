package arcanology.common.type.impl.machine.module

import arcanology.common.Arcanology
import arcanology.common.type.api.machine.module.MachineModule
import arcanology.common.type.api.machine.module.ModuleType
import arcanology.common.type.impl.machine.component.EnergyBarComponent
import arcanology.common.type.impl.machine.component.ItemSlotComponent
import arcanology.common.type.impl.world.block.entity.MachineBlockEntity
import assemble.common.Assemble
import assemble.common.type.impl.assembly.gradual.energy.EnergyItemProcessingAssembly

open class ItemProcessingModuleType : ModuleType<EnergyItemProcessingAssembly<MachineBlockEntity>>({
    val assembly = Assemble.matching(Arcanology.content.assemblyType.itemProcessing).first()
    val components = listOf(
        EnergyBarComponent(it, assembly),
        ItemSlotComponent.ofInput(it, assembly, 0),
        ItemSlotComponent.ofOutput(it, assembly, 1)
    )

    MachineModule(it, assembly, components)
})