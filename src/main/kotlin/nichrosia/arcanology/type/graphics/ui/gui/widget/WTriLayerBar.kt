package nichrosia.arcanology.type.graphics.ui.gui.widget

import io.github.cottonmc.cotton.gui.client.ScreenDrawing
import io.github.cottonmc.cotton.gui.widget.WBar
import io.github.cottonmc.cotton.gui.widget.data.Texture
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.math.MathHelper
import nichrosia.arcanology.util.clamp
import kotlin.math.roundToInt

abstract class WTriLayerBar(
    field: Int,
    maxField: Int,
    val bottom: Texture,
    val overlay: Texture,
    val textureProvider: () -> Texture,
    val colorProvider: () -> Int = { 0xFF_FFFFFF.toInt() },
    val widthProvider: WTriLayerBar.() -> Int = { width },
    val heightProvider: WTriLayerBar.() -> Int = { height }
) : WBar(bottom, overlay, field, maxField, Direction.UP) {
    val fieldValue: Int
        get() = properties[this.field]

    val maxFieldValue: Int
        get() = properties[this.max]

    override fun paint(matrices: MatrixStack?, x: Int, y: Int, mouseX: Int, mouseY: Int) {
        val percent = clamp(fieldValue / maxFieldValue.toFloat(), 0f, 1f)
		val barSize = (height * percent).roundToInt()

        val texture = textureProvider()
        val color = colorProvider()
        val width = widthProvider()
        val height = heightProvider()

        ScreenDrawing.texturedRect(matrices, x, y, width, height, bottom, 0xFF_FFFFFF.toInt())

        if (barSize > 0) ScreenDrawing.texturedRect(
            matrices,
            x,
            (y + height) - barSize,
            width,
            barSize,
            texture.image,
            texture.u1,
            MathHelper.lerp(percent, texture.v2, texture.v1),
            texture.u2,
            texture.v2,
            color
        )

        ScreenDrawing.texturedRect(matrices, x, y, width, height, overlay, 0xFF_FFFFFF.toInt())
    }
}