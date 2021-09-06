package nichrosia.arcanology.gui

import io.github.cottonmc.cotton.gui.widget.TooltipBuilder
import io.github.cottonmc.cotton.gui.widget.WBar
import net.minecraft.text.TranslatableText
import net.minecraft.util.Identifier
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.func.formatted

open class WEnergyBar(
    direction: Direction = Direction.UP,
    empty: Identifier = this.empty,
    full: Identifier = this.full,
    value: Int = 2,
    maxValue: Int = 3,
) : WBar(
    empty,
    full,
    value,
    maxValue,
    direction
) {
    override fun addTooltip(information: TooltipBuilder) {
        val energy = properties[field]
        val max = properties[max]

        information.add(TranslatableText("arcanology.gui.widget.energy", "${energy.toDouble().formatted} / ${max.toDouble().formatted} LF"))
    }

    @Suppress("unused")
    companion object {
        val empty = Identifier(Arcanology.modID, "textures/gui/widget/energy_bar_empty.png")
        val full = Identifier(Arcanology.modID, "textures/gui/widget/energy_bar_empty.png")
    }
}