package arcanology.common.type.api.registrar

import arcanology.common.type.impl.world.storage.tier.EnergyTier
import net.minecraft.util.Identifier
import nucleus.common.builtin.division.ModRoot
import nucleus.common.registrar.type.BinaryRegistrar

open class EnergyTierRegistrar<R : ModRoot<R>>(root: R) : BinaryRegistrar<Identifier, EnergyTier, R>(root)