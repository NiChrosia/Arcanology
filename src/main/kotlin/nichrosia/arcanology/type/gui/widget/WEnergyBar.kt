package nichrosia.arcanology.type.gui.widget

import io.github.cottonmc.cotton.gui.widget.TooltipBuilder
import io.github.cottonmc.cotton.gui.widget.WBar
import net.minecraft.text.TranslatableText
import net.minecraft.util.Identifier
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.util.formatted

open class WEnergyBar(
    direction: Direction = Direction.UP,
    empty: Identifier = Companion.empty,
    full: Identifier = Companion.full,
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
        val empty = Arcanology.idOf("textures/gui/widget/energy_bar_empty.png")
        val full = Arcanology.idOf("textures/gui/widget/energy_bar_full.png")
    }
}