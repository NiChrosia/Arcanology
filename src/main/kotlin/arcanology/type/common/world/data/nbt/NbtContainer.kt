package arcanology.type.common.world.data.nbt

import net.minecraft.nbt.NbtCompound

interface NbtContainer {
    val nbtObjects: MutableList<NbtObject>

    fun <T> nbtFieldOf(value: T, name: String? = null) = NbtField(this, value, name)

    fun writeNbtObjects(nbt: NbtCompound): NbtCompound {
        return nbt.apply {
            nbtObjects.forEach { it.writeNbt(this) }
        }
    }

    fun readNbtObjects(nbt: NbtCompound): NbtCompound {
        return nbt.apply {
            nbtObjects.forEach { it.readNbt(nbt) }
        }
    }
}