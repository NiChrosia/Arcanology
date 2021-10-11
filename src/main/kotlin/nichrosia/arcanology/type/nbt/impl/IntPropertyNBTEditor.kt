package nichrosia.arcanology.type.nbt.impl

import net.minecraft.nbt.NbtCompound
import nichrosia.arcanology.type.nbt.PropertyNBTEditor
import kotlin.reflect.KMutableProperty0

open class IntPropertyNBTEditor(nbtName: String, property: KMutableProperty0<Int>) : PropertyNBTEditor<Int>(nbtName, NbtCompound::putInt, NbtCompound::getInt, property)