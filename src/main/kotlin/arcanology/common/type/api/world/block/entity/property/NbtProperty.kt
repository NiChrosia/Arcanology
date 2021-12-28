package arcanology.common.type.api.world.block.entity.property

import arcanology.common.type.api.world.nbt.type.NbtType
import net.minecraft.nbt.NbtElement
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

open class NbtProperty<V>(
    val name: String,
    val type: NbtType<V>,
    initial: V
) : ReadWriteProperty<Any, V> {
    open var value = initial

    override fun getValue(thisRef: Any, property: KProperty<*>): V {
        return value
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: V) {
        this.value = value
    }

    open fun toNbt(): NbtElement {
        return type.toNbt(value)
    }

    open fun fromNbt(element: NbtElement) {
        value = type.fromNbt(element)
    }
}