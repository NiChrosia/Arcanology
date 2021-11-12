package nichrosia.arcanology.type.content.gui.property

import net.minecraft.screen.PropertyDelegate
import kotlin.reflect.KMutableProperty0
import kotlin.reflect.KProperty0

open class KPropertyDelegate(vararg properties: KProperty0<Number>) : PropertyDelegate {
    open val properties = mutableListOf(*properties)

    override operator fun get(index: Int): Int {
        return properties[index]().toInt()
    }

    override operator fun set(index: Int, value: Int) {
        properties[index].also {
            if (it is KMutableProperty0<Number>) it.set(value)
        }
    }

    override fun size(): Int {
        return properties.size
    }
}