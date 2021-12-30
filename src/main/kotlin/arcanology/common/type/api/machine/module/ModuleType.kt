package arcanology.common.type.api.machine.module

import arcanology.common.Arcanology
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
open class ModuleType<A : GradualAssembly<MachineBlockEntity>>(protected val provider: (MachineBlockEntity) -> MachineModule<A>, val configurator: MachineBlockEntity.() -> Unit) {
    open fun of(machine: MachineBlockEntity): MachineModule<A> {
        return provider(machine).also {
            machine.energyStorage = machine.run { energyStorageOf(Arcanology.content.energyTier.blank) }
            machine.fluidStorage = machine.run { fluidStorageOf(Arcanology.content.fluidTier.blank, 0) }
            machine.itemStorage = machine.run { itemStorageOf(0) }

            machine.energyStorage.disable()
            machine.fluidStorage.disable()
            machine.itemStorage.disable()

            configurator(machine)
        }
    }
}