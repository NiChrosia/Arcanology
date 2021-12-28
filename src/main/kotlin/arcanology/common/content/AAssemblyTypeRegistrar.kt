package arcanology.common.content

import arcanology.common.Arcanology
import arcanology.common.type.impl.world.block.entity.MachineBlockEntity
import assemble.common.type.api.registrar.AssemblyTypeRegistrar
import assemble.common.type.impl.assembly.energy.type.EnergyItemProcessingType

open class AAssemblyTypeRegistrar(root: Arcanology) : AssemblyTypeRegistrar<Arcanology>(root) {
    // Total consumption: 10k, duration: 5 seconds, consumption per tick: 100
    val itemProcessing by memberOf(root.identify("item_processing")) { EnergyItemProcessingType<MachineBlockEntity>(it, listOf(0, 1), 1L, 100L, 100L) }
}