package arcanology.type.common.ui.widget

import io.github.cottonmc.cotton.gui.widget.TooltipBuilder
import io.github.cottonmc.cotton.gui.widget.WBar
import net.minecraft.text.LiteralText
import net.minecraft.text.TranslatableText
import net.minecraft.util.Identifier
import arcanology.Arcanology

/** @author GabrielOlvH */
open class WProcessBar(
    direction: Direction = Direction.RIGHT,
    bg: Identifier = empty,
    bar: Identifier = full,
    value: Int = 0,
    maxField: Int = 1,
) : WBar(bg, bar, value, maxField, direction) {
    override fun addTooltip(information: TooltipBuilder) {
        val progress = properties[field]
        val max = properties[max]

        if (max <= 0 || progress <= 0) return

        val percentage = progress * 100 / max

        information.add(TranslatableText("${Arcanology.modID}.gui.widget.process", percentage).append(LiteralText("%")))
    }

    companion object {
        val empty = Arcanology.identify("textures/gui/widget/processing_empty.png")
        val full = Arcanology.identify("textures/gui/widget/processing_full.png")
    }
}