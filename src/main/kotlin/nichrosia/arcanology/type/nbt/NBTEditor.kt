package nichrosia.arcanology.type.nbt

import net.minecraft.nbt.NbtCompound
import nichrosia.arcanology.type.block.entity.MachineBlockEntity
import kotlin.properties.PropertyDelegateProvider
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

@Suppress("UNCHECKED_CAST")
abstract class NBTEditor<T, R : NBTEditor<T, R>>(val nbtName: String, val getter: R.() -> T, val setter: R.(T) -> Unit) : ReadWriteProperty<MachineBlockEntity<*, *, *, *>, T>, PropertyDelegateProvider<MachineBlockEntity<*, *, *, *>, NBTEditor<T, R>> {
    override fun provideDelegate(thisRef: MachineBlockEntity<*, *, *, *>, property: KProperty<*>): NBTEditor<T, R> {
        return apply { thisRef.nbtEditors.add(this) }
    }

    override fun getValue(thisRef: MachineBlockEntity<*, *, *, *>, property: KProperty<*>): T {
        return getter(this as R)
    }

    override fun setValue(thisRef: MachineBlockEntity<*, *, *, *>, property: KProperty<*>, value: T) {
        setter(this as R, value)
    }

    abstract fun write(nbt: NbtCompound): NbtCompound
    abstract fun read(nbt: NbtCompound)
}