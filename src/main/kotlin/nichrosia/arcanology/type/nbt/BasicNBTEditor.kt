package nichrosia.arcanology.type.nbt

import net.minecraft.nbt.NbtCompound

open class BasicNBTEditor<T>(
    nbtName: String,
    var value: T,
    val writer: (NbtCompound, String, T) -> Unit,
    val reader: (NbtCompound, String) -> T
) : NBTEditor<T, BasicNBTEditor<T>>(nbtName, { value }, { this.value = it }) {
    override fun write(nbt: NbtCompound): NbtCompound {
        writer(nbt, nbtName, value)
        return nbt
    }

    override fun read(nbt: NbtCompound) {
        value = reader(nbt, nbtName)
    }
}