package arcanology.common.type.impl.gui.widget

import arcanology.common.Arcanology
import arcanology.common.type.api.gui.widget.WDynamicBar

open class WProcessingBar(value: () -> Long, max: () -> Long) : WDynamicBar(empty, full, value, max, Direction.RIGHT) {
    companion object {
        val empty = Arcanology.identify("textures/gui/widget/processing_empty.png")
        val full = Arcanology.identify("textures/gui/widget/processing_full.png")
    }
}