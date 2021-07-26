@file:Suppress("unused")

package nichrosia.arcanology.io

import net.minecraft.nbt.NbtCompound
import java.util.*

fun NbtCompound.writeUUID(key: String, uuid: UUID?) {
    putBoolean("${key}IsNull", uuid == null)
    uuid?.let {
        putUuid(key, it)
    }

    if (uuid == null) putString(key, "nonexistentUUID")
}

fun NbtCompound.readUUID(key: String): UUID? {
    return if (!getBoolean("${key}IsNull")) {
        getUuid(key)
    } else {
        get(key)
        null
    }
}