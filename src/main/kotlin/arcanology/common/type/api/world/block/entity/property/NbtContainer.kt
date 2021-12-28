package arcanology.common.type.api.world.block.entity.property

import arcanology.common.type.api.world.nbt.type.NbtType
import net.minecraft.nbt.NbtCompound

/** A container for automating synchronized & serializable properties. */
interface NbtContainer {
    val properties: MutableMap<String, NbtProperty<*>>

    /** Returns a new NbtProperty for serialization & server -> client syncing. */
    fun <T> registered(name: String, initial: T): NbtProperty<T> {
        return NbtProperty(name, NbtType.of(initial), initial).also {
            properties[name] = it
        }
    }

    fun writePropertyNbt(compound: NbtCompound) {
        for ((name, property) in properties) {
            compound.put(name, property.toNbt())
        }
    }

    fun readPropertyNbt(compound: NbtCompound) {
        for ((name, property) in properties) {
            property.fromNbt(compound[name]!!)
        }
    }
}