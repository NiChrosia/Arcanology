package arcanology.common.type.api.registrar

import arcanology.common.type.impl.world.storage.tier.FluidTier
import net.minecraft.util.Identifier
import nucleus.common.builtin.division.ModRoot
import nucleus.common.registrar.type.BinaryRegistrar

open class FluidTierRegistrar<R : ModRoot<R>>(root: R) : BinaryRegistrar<Identifier, FluidTier, R>(root)