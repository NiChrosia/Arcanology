package nichrosia.arcanology.type.nbt

import net.minecraft.nbt.NbtCompound
import kotlin.reflect.KMutableProperty0

open class PropertyNBTEditor<T>(
    nbtName: String,
    val writer: (NbtCompound, String, T) -> Unit,
    val reader: (NbtCompound, String) -> T,
    val property: KMutableProperty0<T>
) : NBTEditor<T, PropertyNBTEditor<T>>(nbtName, { property.get() }, { property.set(it) }) {
    override fun write(nbt: NbtCompound): NbtCompound {
        writer(nbt, nbtName, property.get())
        return nbt
    }

    override fun read(nbt: NbtCompound) {
        property.set(reader(nbt, nbtName))
    }
}