package arcanology.common.content

import arcanology.common.Arcanology
import arcanology.common.type.api.registrar.FluidTierRegistrar
import arcanology.common.type.impl.world.storage.tier.FluidTier

open class AFluidTierRegistrar(root: Arcanology) : FluidTierRegistrar<Arcanology>(root) {
    val blank by memberOf(root.identify("blank")) { FluidTier(0L) }

    val standard by memberOf(root.identify("standard")) { FluidTier(10_000L) }
    val industrial by memberOf(root.identify("industrial")) { FluidTier(500_000L) }
    val vanguard by memberOf(root.identify("vanguard")) { FluidTier(2_500_000L) }
}