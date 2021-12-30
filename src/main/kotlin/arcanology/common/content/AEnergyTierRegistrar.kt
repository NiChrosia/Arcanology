package arcanology.common.content

import arcanology.common.Arcanology
import arcanology.common.type.api.registrar.EnergyTierRegistrar
import arcanology.common.type.impl.world.storage.tier.EnergyTier

open class AEnergyTierRegistrar(root: Arcanology) : EnergyTierRegistrar<Arcanology>(root) {
    val blank by memberOf(root.identify("blank")) { EnergyTier(0L, 0L, 0L) }

    val standard by memberOf(root.identify("standard")) { EnergyTier(1_000L, 1_000L, 50_000L) }
    val industrial by memberOf(root.identify("industrial")) { EnergyTier(25_000L, 25_000L, 1_000_000L) }
    val vanguard by memberOf(root.identify("vanguard")) { EnergyTier(500_000L, 500_000L, 20_000_000L) }
}