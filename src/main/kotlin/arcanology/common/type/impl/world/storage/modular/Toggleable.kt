package arcanology.common.type.impl.world.storage.modular

interface Toggleable {
    var enabled: Boolean

    fun enable() {
        enabled = true
    }

    fun disable() {
        enabled = false
    }
}