package arcanology.common.content

import arcanology.common.Arcanology
import arcanology.common.type.api.registrar.NbtSerializerRegistrar

class ANbtSerializerRegistrar(root: Arcanology) : NbtSerializerRegistrar<Arcanology>(root) {
//    val moduleType by memberOf(root.identify("module_type")) { ModuleTypeSerializer() }
//    val modularItemStorage by memberOf(root.identify("modular_item_storage")) { ModularItemStorageSerializer() }
}