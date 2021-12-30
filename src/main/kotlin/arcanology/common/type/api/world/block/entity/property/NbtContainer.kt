package arcanology.common.type.api.world.block.entity.property

import dev.nathanpb.ktdatatag.serializer.DataSerializer
import net.minecraft.nbt.NbtCompound

/** A container for automating synchronized & serializable properties. */
interface NbtContainer {
    val properties: MutableMap<String, NbtProperty<*>>

    /** Returns a new NbtProperty for (de)serialization. */
    fun <T> registered(name: String, serializer: DataSerializer<T>, initial: T): NbtProperty<T> {
        return NbtProperty(name, serializer, initial).also {
            properties[name] = it
        }
    }

    fun writePropertyNbt(compound: NbtCompound) {
        for (property in properties.values) {
            property.write(compound)
        }
    }

    fun readPropertyNbt(compound: NbtCompound) {
        for (property in properties.values) {
            property.read(compound)
        }
    }
}