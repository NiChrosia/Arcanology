package nichrosia.arcanology.type.gui.widget

import io.github.cottonmc.cotton.gui.widget.TooltipBuilder
import io.github.cottonmc.cotton.gui.widget.WBar
import net.minecraft.text.LiteralText
import net.minecraft.text.TranslatableText
import net.minecraft.util.Identifier
import nichrosia.arcanology.Arcanology

/** @author GabrielOlvH */
open class WProcessBar(
    direction: Direction = Direction.RIGHT,
    bg: Identifier = PROCESS_EMPTY,
    bar: Identifier = PROCESS_FULL,
    value: Int = 0,
    maxValue: Int = 3
) : WBar(bg, bar, value, maxValue, direction) {
    override fun addTooltip(information: TooltipBuilder) {
        val progress = properties[field]
        val max = properties[max]

        if (max <= 0) return

        val percentage = progress * 100 / max

        information.add(TranslatableText("arcanology.gui.widget.process", percentage).append(LiteralText("%")))
    }

    companion object {
        val PROCESS_EMPTY = Identifier(Arcanology.modID, "textures/gui/widget/processing_empty.png")
        val PROCESS_FULL = Identifier(Arcanology.modID, "textures/gui/widget/processing_full.png")
    }
}