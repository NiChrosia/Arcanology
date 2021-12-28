package arcanology.common.type.api.world.nbt.type

import arcanology.common.type.impl.world.nbt.type.LongNbtType
import net.minecraft.nbt.NbtElement
import kotlin.reflect.KClass

abstract class NbtType<T> {
    abstract fun fromNbt(element: NbtElement): T
    abstract fun toNbt(value: T): NbtElement

    companion object {
        val types = mutableMapOf<KClass<*>, NbtType<*>>()

        init {
            types[Long::class] = LongNbtType()
        }

        @Suppress("UNCHECKED_CAST")
        fun <T> of(value: T): NbtType<T> {
            val type = types[value!!::class] as? NbtType<T>

            type ?: throw IllegalArgumentException("Given value does not have a type.")

            return type
        }
    }
}