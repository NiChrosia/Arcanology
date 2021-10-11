package nichrosia.arcanology.type.nbt.impl

import net.minecraft.nbt.NbtCompound
import nichrosia.arcanology.type.nbt.PropertyNBTEditor
import kotlin.reflect.KMutableProperty0

open class LongPropertyNBTEditor(nbtName: String, property: KMutableProperty0<Long>) : PropertyNBTEditor<Long>(nbtName, NbtCompound::putLong, NbtCompound::getLong, property)