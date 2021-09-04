package nichrosia.arcanology.type.block.entity

import net.minecraft.item.ItemStack
import nichrosia.arcanology.func.decrement
import nichrosia.arcanology.type.block.entity.type.AInventory
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

@Suppress("MemberVisibilityCanBePrivate")
open class ItemSlot<R : AInventory>(val slotID: Int) : ReadWriteProperty<R, ItemStack> {
    override fun getValue(thisRef: R, property: KProperty<*>): ItemStack {
        return thisRef.items[slotID]
    }

    override fun setValue(thisRef: R, property: KProperty<*>, value: ItemStack) {
        thisRef.setStack(slotID, value)
    }
}