package arcanology.common.content

import arcanology.common.Arcanology
import arcanology.common.type.impl.assembly.type.gradual.energy.EnergyItemProcessingType
import arcanology.common.type.impl.world.entity.block.ItemProcessingMachine
import assemble.common.type.api.registrar.AssemblyTypeRegistrar

class AAssemblyTypeRegistrar(root: Arcanology) : AssemblyTypeRegistrar<Arcanology>(root) {
    // Total consumption: 10k, duration: 5 seconds, consumption per tick: 100
    val itemProcessing by memberOf(root.identify("item_processing")) {
        EnergyItemProcessingType<ItemProcessingMachine>(it, listOf(0, 1), 1L, 100L, 100L)
    }
}