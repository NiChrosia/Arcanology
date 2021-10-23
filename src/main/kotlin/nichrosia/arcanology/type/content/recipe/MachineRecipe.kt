package nichrosia.arcanology.type.content.recipe

import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier
import nichrosia.arcanology.type.content.block.entity.MachineBlockEntity
import kotlin.reflect.KProperty0

abstract class MachineRecipe<I : MachineBlockEntity<*, *, *, *>, T : MachineRecipe<I, T>>(val input: ItemStack, override val result: ItemStack, types: KProperty0<MutableList<T>>) : SimpleRecipe<I, T>(types) {
    abstract override val recipeType: Type<I, T>
    abstract override val recipeSerializer: Serializer<I, T>

    override fun craft(inventory: I): ItemStack {
        inventory.decrementStack(inventory.inputSlots[0])

        return inventory.incrementStack(inventory.outputSlots[0], super.craft(inventory))
    }

    open class Type<I : MachineBlockEntity<*, *, *, *>, T : MachineRecipe<I, T>>(ID: Identifier) : SimpleRecipe.Type<I, T>(ID)
    abstract class Serializer<I : MachineBlockEntity<*, *, *, *>, T : MachineRecipe<I, T>>(ID: Identifier, types: KProperty0<MutableList<T>>) : SimpleRecipe.Serializer<I, T>(ID, types)
}