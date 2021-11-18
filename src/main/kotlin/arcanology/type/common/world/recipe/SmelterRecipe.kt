package arcanology.type.common.world.recipe

import com.google.gson.JsonObject
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketByteBuf
import net.minecraft.util.Identifier
import arcanology.Arcanology
import arcanology.type.common.world.block.entity.SmelterBlockEntity
import arcanology.type.common.world.data.storage.fluid.FluidStack
import arcanology.type.common.world.recipe.SimpleRecipe.Companion.deserializeItemStack
import arcanology.type.common.world.recipe.fluid.FluidRecipe.Companion.deserializeFluid
import arcanology.type.common.world.recipe.fluid.Item2FluidRecipe
import arcanology.util.networking.readFluidStack
import arcanology.util.networking.writeFluidStack

open class SmelterRecipe(input: ItemStack, output: FluidStack) : Item2FluidRecipe<SmelterBlockEntity, SmelterRecipe>(input, output) {
    override val type = Type
    override val serializer = Serializer

    object Type : SimpleRecipe.Type.Basic<SmelterRecipe>(Arcanology.identify("smelting"))

    object Serializer : SimpleRecipe.Serializer<SmelterRecipe> {
        override fun write(buf: PacketByteBuf, recipe: SmelterRecipe) {
            buf.writeItemStack(recipe.input)
            buf.writeFluidStack(recipe.output)
        }

        override fun read(id: Identifier, buf: PacketByteBuf): SmelterRecipe {
            val input = buf.readItemStack()
            val output = buf.readFluidStack()

            return SmelterRecipe(input, output)
        }

        override fun read(id: Identifier, json: JsonObject): SmelterRecipe {
            val input = json.deserializeItemStack("input")
            val output = json.deserializeFluid("output")

            return SmelterRecipe(input, output)
        }
    }
}