package arcanology.common.type.impl.machine.module

import arcanology.common.Arcanology
import arcanology.common.type.api.machine.module.MachineModule
import arcanology.common.type.api.machine.module.ModuleType
import arcanology.common.type.impl.assembly.gradual.energy.EnergyItemProcessingAssembly
import arcanology.common.type.impl.machine.component.EnergyBarComponent
import arcanology.common.type.impl.machine.component.ItemSlotComponent
import arcanology.common.type.impl.machine.component.ProgressBarComponent
import arcanology.common.type.impl.world.block.entity.MachineBlockEntity
import arcanology.common.type.impl.world.storage.modular.ModularItemStorage
import assemble.common.Assemble

open class ItemProcessingModuleType : ModuleType<EnergyItemProcessingAssembly<MachineBlockEntity>>({
    val assembly = Assemble.matching(Arcanology.content.assemblyType.itemProcessing).first()
    val components = listOf(
        EnergyBarComponent(it, assembly),
        ItemSlotComponent.ofInput(it, assembly, 0),
        ProgressBarComponent(it, assembly),
        ItemSlotComponent.ofOutput(it, assembly, 1)
    )

    MachineModule(it, assembly, components)
}, {
    itemStorage = ModularItemStorage(2)
    itemStorage.enable()
})