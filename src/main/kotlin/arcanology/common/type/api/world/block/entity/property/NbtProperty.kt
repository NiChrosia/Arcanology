package arcanology.common.type.api.world.block.entity.property

import dev.nathanpb.ktdatatag.serializer.DataSerializer
import net.minecraft.nbt.NbtCompound
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

open class NbtProperty<V>(
    val name: String,
    val serializer: DataSerializer<V>,
    initial: V
) : ReadWriteProperty<Any, V> {
    open var value = initial

    override fun getValue(thisRef: Any, property: KProperty<*>): V {
        return value
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: V) {
        this.value = value
    }

    open fun read(compound: NbtCompound) {
        value = serializer.read(compound, name)
    }

    open fun write(compound: NbtCompound) {
        serializer.write(compound, name, value)
    }
}