@file:Suppress("DEPRECATION", "UnstableApiUsage")

package nichrosia.arcanology.type.stack

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants
import net.minecraft.fluid.Fluid

data class FluidStack(val fluid: Fluid, var amount: Long, val capacity: Long = FluidConstants.BUCKET * 10L)