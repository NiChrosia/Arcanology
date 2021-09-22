package nichrosia.arcanology.type.properties.block.entity.inventory

import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

@Suppress("MemberVisibilityCanBePrivate")
open class ItemSlot<R : Inventory>(val slotID: Int) : ReadWriteProperty<R, ItemStack> {
    override fun getValue(thisRef: R, property: KProperty<*>): ItemStack {
        return thisRef.getStack(slotID)
    }

    override fun setValue(thisRef: R, property: KProperty<*>, value: ItemStack) {
        thisRef.setStack(slotID, value)
    }
}