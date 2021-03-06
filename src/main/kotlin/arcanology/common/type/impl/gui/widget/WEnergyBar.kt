package arcanology.common.type.impl.gui.widget

import arcanology.common.Arcanology
import arcanology.common.type.api.gui.widget.WLayeredBar

open class WEnergyBar(energy: () -> Long, capacity: () -> Long) : WLayeredBar(background, top, full, energy, capacity, Direction.UP) {
    override fun canResize() = false

    init {
        width = 16
        height = 84
    }

    companion object {
        val background = Arcanology.identify("textures/gui/widget/energy_bottom.png")
        val full = Arcanology.identify("textures/gui/widget/energy_full.png")
        val top = Arcanology.identify("textures/gui/widget/energy_overlay.png")
    }
}