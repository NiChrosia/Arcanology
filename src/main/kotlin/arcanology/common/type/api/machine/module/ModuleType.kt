package arcanology.common.type.api.machine.module

import arcanology.common.type.impl.world.block.entity.MachineBlockEntity
import assemble.common.type.api.assembly.GradualAssembly

/* prototype deserialization
{
    "assembly": "arcanology:item_processing",
    "components": [
        {
            "type": "arcanology:item_slot",
            "slot": 0, // custom deserialization in ItemSlot component type
            "mode": "input"
        },
        {
            "type" "arcanology:item_slot",
            "slot": 1,
            "mode": "output"
        }
    ]
}

to

ModuleType({ machine: MachineBlockEntity ->
    val assembly = Assemble.matching(Arcanology.content.assemblyType.itemProcessing).first()
    val components = listOf(
        ItemSlotComponent.inputOf(machine, assembly, 0),
        ItemSlotComponent.outputOf(machine, assembly, 1)
    )

    MachineModule(machine, assembly, components)
})
*/
open class ModuleType<A : GradualAssembly<MachineBlockEntity>>(val provider: (MachineBlockEntity) -> MachineModule<A>)