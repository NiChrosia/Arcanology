package nichrosia.arcanology.type.content.gui.widget

import io.github.cottonmc.cotton.gui.widget.TooltipBuilder
import io.github.cottonmc.cotton.gui.widget.WBar
import net.minecraft.text.TranslatableText
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.util.formatted
import nichrosia.common.identity.ID

open class WEnergyBar(
    direction: Direction = Direction.UP,
    empty: ID = Companion.empty,
    full: ID = Companion.full,
    field: Int = 2,
    max: Int = 3,
) : WBar(
    empty,
    full,
    field,
    max,
    direction
) {
    override fun addTooltip(information: TooltipBuilder) {
        val energy = properties[field]
        val max = properties[max]

        information.add(TranslatableText("${Arcanology.modID}.gui.widget.energy", "${energy.toLong().formatted} / ${max.toLong().formatted} LF"))
    }

    companion object {
        val empty = Arcanology.identify("textures/gui/widget/energy_bar_empty.png")
        val full = Arcanology.identify("textures/gui/widget/energy_bar_full.png")
    }
}