package nichrosia.arcanology.type.nbt

import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtElement
import nichrosia.arcanology.type.property.MutableProperty
import kotlin.reflect.KMutableProperty0

@Suppress("UNCHECKED_CAST")
open class NBTEditor<T, N : NbtElement>(val name: String, val property: KMutableProperty0<T>, toBack: (N) -> T, toFront: (T) -> N) : MutableProperty<T, N>(property::get, property::set, toFront, toBack) {
    open fun write(nbt: NbtCompound) = nbt.put(name, toFront(property()))
    open fun read(nbt: NbtCompound) = nbt.get(name)?.let { property.set(toBack(it as? N ?: throw IllegalStateException("Tried to access NBT value of name $name but received incorrect type."))) }
}