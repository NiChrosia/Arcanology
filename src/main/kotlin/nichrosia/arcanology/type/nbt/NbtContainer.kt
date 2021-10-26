package nichrosia.arcanology.type.nbt

interface NbtContainer {
    val nbtObjects: MutableList<NbtObject>

    fun <T> nbtFieldOf(value: T, name: String = "", usePropertyName: Boolean = true) = NbtField(this, value, name, usePropertyName)
}