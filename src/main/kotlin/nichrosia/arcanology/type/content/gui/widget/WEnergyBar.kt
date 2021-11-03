package nichrosia.arcanology.type.content.gui.widget

import io.github.cottonmc.cotton.gui.widget.TooltipBuilder
import io.github.cottonmc.cotton.gui.widget.data.Texture
import net.minecraft.text.TranslatableText
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.util.energyName
import nichrosia.arcanology.util.formatted

open class WEnergyBar(
    field: Int = 2,
    maxField: Int = 3,
) : WTriLayerBar(
    field, maxField, bottom, overlay, ::full
) {
    override fun addTooltip(information: TooltipBuilder) {
        val translationKey = "${Arcanology.modID}.gui.widget.energy"
        val formattedValue = fieldValue.toLong().formatted
        val formattedMax = maxFieldValue.toLong().formatted

        information.add(TranslatableText(
            translationKey,
            "$formattedValue / $formattedMax $energyName"
        ))
    }

    companion object {
        val bottom = Texture(Arcanology.identify("textures/gui/widget/energy_bottom.png"))
        val full = Texture(Arcanology.identify("textures/gui/widget/energy_full.png"))
        val overlay = Texture(Arcanology.identify("textures/gui/widget/energy_overlay.png"))
    }
}