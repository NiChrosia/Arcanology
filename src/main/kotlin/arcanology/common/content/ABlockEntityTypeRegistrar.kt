package arcanology.common.content

import arcanology.common.Arcanology
import nucleus.common.builtin.division.content.BlockEntityTypeRegistrar

open class ABlockEntityTypeRegistrar(root: Arcanology) : BlockEntityTypeRegistrar<Arcanology>(root) {
//    val machine by memberOf(root.identify("machine")) { typeOf(::MachineBlockEntity, root.content.block.machine) }
}