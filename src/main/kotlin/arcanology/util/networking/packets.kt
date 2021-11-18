package arcanology.util.networking

import net.minecraft.network.PacketByteBuf
import net.minecraft.util.registry.Registry
import arcanology.type.common.world.data.storage.fluid.FluidStack
import nichrosia.common.identity.ID

fun PacketByteBuf.writeFluidStack(stack: FluidStack): PacketByteBuf {
    return apply {
        val fluidID = ID(Registry.FLUID.getId(stack.fluid))

        writeString(fluidID.split())
        writeLong(stack.amount)
    }
}

fun PacketByteBuf.readFluidStack(): FluidStack {
    val fluidID = ID.deserialize(readString())
    val fluid = Registry.FLUID.get(fluidID)

    val amount = readLong()

    return FluidStack(fluid, amount)
}