package nichrosia.arcanology.type.recipe

import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier
import nichrosia.arcanology.type.block.entity.MachineBlockEntity
import kotlin.reflect.KProperty0

abstract class MachineRecipe<I : MachineBlockEntity<*, *, *, *>, T : MachineRecipe<I, T>>(val input: ItemStack, override val result: ItemStack, types: KProperty0<MutableList<T>>) : SimpleRecipe<I, T>(types) {
    override fun craft(inventory: I): ItemStack {
        inventory.decrementStack(inventory.inputSlots[0])

        return inventory.incrementStack(inventory.outputSlots[0], super.craft(inventory))
    }

    companion object {
        open class Type<I : MachineBlockEntity<*, *, *, *>, T : MachineRecipe<I, T>>(ID: Identifier) : SimpleRecipe.Companion.Type<I, T>(ID)
        abstract class Serializer<I : MachineBlockEntity<*, *, *, *>, T : MachineRecipe<I, T>>(ID: Identifier, types: KProperty0<MutableList<T>>) : SimpleRecipe.Companion.Serializer<I, T>(ID, types)
    }
}