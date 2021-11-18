package nichrosia.arcanology.type.world.data.nbt

import net.minecraft.nbt.NbtCompound

/** A simple interface to represent an object that can be (de)serialized to NBT. */
interface NbtObject {
    fun writeNbt(nbt: NbtCompound): NbtCompound
    fun readNbt(nbt: NbtCompound)
}