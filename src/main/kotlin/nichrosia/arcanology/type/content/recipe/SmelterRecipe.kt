package nichrosia.arcanology.type.content.recipe

import com.google.gson.JsonObject
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketByteBuf
import net.minecraft.util.Identifier
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.type.content.block.entity.SmelterBlockEntity
import nichrosia.arcanology.type.content.recipe.SimpleRecipe.Companion.deserializeItemStack
import nichrosia.arcanology.type.content.recipe.fluid.FluidRecipe.Companion.deserializeFluid
import nichrosia.arcanology.type.content.recipe.fluid.Item2FluidRecipe
import nichrosia.arcanology.type.stack.FluidStack
import nichrosia.arcanology.util.readFluidStack
import nichrosia.arcanology.util.writeFluidStack
import nichrosia.common.identity.ID

open class SmelterRecipe(input: ItemStack, output: FluidStack) : Item2FluidRecipe<SmelterBlockEntity, SmelterRecipe>(input, output) {
    override val type: SimpleRecipe.Type<SmelterRecipe> = Type
    override val serializer: SimpleRecipe.Serializer<SmelterRecipe> = Serializer

    object Type : SimpleRecipe.Type<SmelterRecipe> {
        override val ID: ID = Arcanology.identify("smelting")
    }

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