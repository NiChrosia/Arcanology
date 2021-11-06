@file:Suppress("UnstableApiUsage", "DEPRECATION")

package nichrosia.arcanology.type.content.gui.widget

import io.github.cottonmc.cotton.gui.widget.TooltipBuilder
import io.github.cottonmc.cotton.gui.widget.data.Texture
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants
import net.minecraft.block.Blocks
import net.minecraft.fluid.Fluid
import net.minecraft.text.LiteralText
import net.minecraft.text.TranslatableText
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.Arcanology.arcanology
import nichrosia.arcanology.ArcanologyClient.client
import nichrosia.arcanology.type.texture.AnimatedTexture
import nichrosia.arcanology.util.formatted
import nichrosia.common.record.registrar.Registrar

open class WFluidBar(
    minField: Int = 4,
    maxField: Int = 5,
    val fluidProvider: () -> Fluid,
) : WTriLayerBar(minField, maxField, bottom, overlay, { fluidProvider().texture }, { fluidProvider().color }) {
    override fun tick() {
        fluidProvider().animated?.tick()
    }

    override fun addTooltip(information: TooltipBuilder) {
        val formattedValue = (fieldValue.toLong() / (FluidConstants.BUCKET / 1000)).formatted
        val formattedMax = (maxFieldValue.toLong() / (FluidConstants.BUCKET / 1000)).formatted

        val fluid = fluidProvider()

        val bucketKey = fluid.bucketItem.translationKey.also {
            if (it == Blocks.AIR.translationKey) return // air is empty, and therefore should not be displayed
        }

        val blockKey = bucketKey.replace("item.", "")
            .replace("block.", "")
            .replace("_bucket", "")
            .let { "block.$it" }

        val translatedName = TranslatableText(blockKey)
        val coloredName = translatedName.let {
            it.setStyle(it.style.withColor(fluid.color))
        }

        val quantity = LiteralText("$formattedValue / $formattedMax mB")

        information.add(coloredName)
        information.add(quantity)
    }

    companion object {
        const val width = 27
        const val height = 54

        val bottom = Texture(Arcanology.identify("textures/gui/widget/liquid_bottom.png"))
        val overlay = Texture(Arcanology.identify("textures/gui/widget/liquid_overlay.png"))
        val empty = Texture(Arcanology.identify("textures/gui/widget/liquid_empty.png"))

        val Fluid.animated: AnimatedTexture?
            get() = Registrar.arcanology.client.fluidTexture.find(this, width, height)

        val Fluid.texture: Texture
            get() = animated?.texture ?: empty

        val Fluid.color: Int
            get() = animated?.color ?: 0xFF_FFFFFF.toInt()
    }
}