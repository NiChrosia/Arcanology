package arcanology.common.type.impl.world.nbt.type

import arcanology.common.type.api.world.nbt.type.NbtType
import net.minecraft.nbt.NbtElement
import net.minecraft.nbt.NbtLong

open class LongNbtType : NbtType<Long>() {
    override fun fromNbt(element: NbtElement): Long {
        return (element as NbtLong).longValue()
    }

    override fun toNbt(value: Long): NbtLong {
        return NbtLong.of(value)
    }
}