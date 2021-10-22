package nichrosia.arcanology.type.recipe

import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier
import net.minecraft.world.World
import nichrosia.arcanology.type.block.entity.MachineBlockEntity
import kotlin.reflect.KProperty0

abstract class FluidRecipe<I : MachineBlockEntity<*, *, *, *>, T : FluidRecipe<I, T>>(types: KProperty0<MutableList<T>>) : SimpleRecipe<I, T>(types) {
    abstract override val recipeType: Type<I, T>
    abstract override val recipeSerializer: Serializer<I, T>

    override fun matches(inventory: I, world: World): Boolean {
        return true
    }

    override fun craft(inventory: I): ItemStack {
        return ItemStack.EMPTY
    }

    open class Type<I : MachineBlockEntity<*, *, *, *>, T : FluidRecipe<I, T>>(ID: Identifier) : SimpleRecipe.Type<I, T>(ID)
    abstract class Serializer<I : MachineBlockEntity<*, *, *, *>, T : FluidRecipe<I, T>>(ID: Identifier, types: KProperty0<MutableList<T>>) : SimpleRecipe.Serializer<I, T>(ID, types)
}