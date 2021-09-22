package nichrosia.arcanology.util

import io.github.cottonmc.cotton.gui.widget.WPlainPanel
import io.github.cottonmc.cotton.gui.widget.WWidget
import nichrosia.arcanology.type.math.Vec2

fun WPlainPanel.add(widget: WWidget, vec: Vec2, width: Int, height: Int) {
    add(widget, vec.x.toInt(), vec.y.toInt(), width, height)
}