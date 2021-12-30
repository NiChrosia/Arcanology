package arcanology.common.content

import arcanology.common.Arcanology
import arcanology.common.type.api.registrar.ModuleTypeRegistrar
import arcanology.common.type.impl.machine.module.BlankModuleType
import arcanology.common.type.impl.machine.module.ItemProcessingModuleType

open class AModuleTypeRegistrar(root: Arcanology) : ModuleTypeRegistrar<Arcanology>(root) {
    val blank by memberOf(root.identify("blank")) { BlankModuleType() }
    val itemProcessing by memberOf(root.identify("item_processing")) { ItemProcessingModuleType() }
}