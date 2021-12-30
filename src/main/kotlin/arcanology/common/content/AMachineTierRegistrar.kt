package arcanology.common.content

import arcanology.common.Arcanology
import arcanology.common.type.api.registrar.MachineTierRegistrar
import arcanology.common.type.impl.world.storage.tier.MachineTier

open class AMachineTierRegistrar(root: Arcanology) : MachineTierRegistrar<Arcanology>(root) {
    val standard by memberOf(root.identify("standard")) { MachineTier(root.content.energyTier.standard, root.content.fluidTier.standard) }
    val industrial by memberOf(root.identify("industrial")) { MachineTier(root.content.energyTier.industrial, root.content.fluidTier.industrial) }
    val vanguard by memberOf(root.identify("vanguard")) { MachineTier(root.content.energyTier.vanguard, root.content.fluidTier.vanguard) }
}