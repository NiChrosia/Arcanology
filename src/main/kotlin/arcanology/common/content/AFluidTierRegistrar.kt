package arcanology.common.content

import arcanology.common.Arcanology
import arcanology.common.type.api.registrar.FluidTierRegistrar
import arcanology.common.type.impl.world.storage.tier.FluidTier

open class AFluidTierRegistrar(root: Arcanology) : FluidTierRegistrar<Arcanology>(root) {
    val blank by memberOf(root.identify("blank")) { FluidTier(0L) }
    val standard by memberOf(root.identify("standard")) { FluidTier(10_000L) }
}