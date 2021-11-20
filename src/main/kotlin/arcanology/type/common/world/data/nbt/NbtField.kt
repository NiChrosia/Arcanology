package arcanology.type.common.world.data.nbt

import net.minecraft.nbt.NbtCompound
import kotlin.properties.PropertyDelegateProvider
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/** An NBT delegate for easier serialization. */
open class NbtField<T>(
    open val container: NbtContainer,
    open var value: T,
    open var name: String? = null
) : ReadWriteProperty<Any, T>, PropertyDelegateProvider<Any, NbtField<T>>, NbtObject {
    override fun getValue(thisRef: Any, property: KProperty<*>) = value
    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) { this.value = value }

    override fun provideDelegate(thisRef: Any, property: KProperty<*>): NbtField<T> {
        return apply {
            container.nbtObjects.add(this)

            if (name == null) name = property.name
        }
    }

    override fun writeNbt(nbt: NbtCompound): NbtCompound {
        name!!

        return nbt.apply {
            when(value) {
                is Short -> nbt.putShort(name, value as Short)
                is Int -> nbt.putInt(name, value as Int)
                is Float -> nbt.putFloat(name, value as Float)
                is Double -> nbt.putDouble(name, value as Double)
                is Long -> nbt.putLong(name, value as Long)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun readNbt(nbt: NbtCompound) {
        name!!

        nbt.apply {
            when(value) {
                is Short -> value = nbt.getShort(name) as T
                is Int -> value = nbt.getInt(name) as T
                is Float -> value = nbt.getFloat(name) as T
                is Double -> value = nbt.getDouble(name) as T
                is Long -> value = nbt.getLong(name) as T
            }
        }
    }
}