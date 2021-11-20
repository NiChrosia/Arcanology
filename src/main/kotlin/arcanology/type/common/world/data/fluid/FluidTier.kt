@file:Suppress("UnstableApiUsage")

package arcanology.type.common.world.data.fluid

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants

open class FluidTier(
    open val name: String,
    open val capacity: Long,
    open val maxInsert: Long = FluidConstants.BUCKET / 4,
    open val maxExtract: Long = FluidConstants.BUCKET / 4,
) {
    companion object {
        val standard = FluidTier("standard", FluidConstants.BUCKET * 10)
    }
}