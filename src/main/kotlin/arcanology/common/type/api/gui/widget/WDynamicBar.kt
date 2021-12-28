package arcanology.common.type.api.gui.widget

import io.github.cottonmc.cotton.gui.client.ScreenDrawing
import io.github.cottonmc.cotton.gui.widget.WBar
import io.github.cottonmc.cotton.gui.widget.data.Texture
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.screen.ScreenHandler
import net.minecraft.util.Identifier
import net.minecraft.util.math.MathHelper
import kotlin.math.roundToInt

/** A WBar that relies on custom lambdas to provide `value` and `max`, rather than the painfully inefficient
 * `PropertyDelegate`.
 *
 * @see ScreenHandler.checkSlotUpdates */
open class WDynamicBar(
    background: Identifier,
    full: Identifier,
    val value: () -> Long,
    val max: () -> Long,
    direction: Direction
) : WBar(background, full, 0, 0, direction) {
    val background = Texture(background)
    val full = Texture(full)

    @Suppress("DEPRECATED_SMARTCAST")
    override fun paint(matrices: MatrixStack, x: Int, y: Int, mouseX: Int, mouseY: Int) {
        ScreenDrawing.texturedRect(matrices, x, y, width, height, background, -1)

        direction!! // declared as non-nullable in constructor, so it cannot be null

        val maxPixels = when(direction) {
            Direction.UP -> height
            Direction.RIGHT -> width
            Direction.DOWN -> height
            Direction.LEFT -> width
        }

        val percent = value() / max().toFloat()
        val pixels = (percent * maxPixels).roundToInt()

        val topEdge = full.v1
        val rightEdge = full.u2
        val bottomEdge = full.v2
        val leftEdge = full.u1

        when(direction) {
            Direction.UP -> {
                val adjustedTop = MathHelper.lerp(percent, bottomEdge, topEdge)

                ScreenDrawing.texturedRect(matrices, x, y, width, pixels, full.image, leftEdge, adjustedTop, rightEdge, bottomEdge, -1)
            }

            Direction.RIGHT -> {
                val adjustedRight = MathHelper.lerp(percent, leftEdge, rightEdge)

                ScreenDrawing.texturedRect(matrices, x, y, width, pixels, full.image, leftEdge, topEdge, adjustedRight, bottomEdge, -1)
            }

            Direction.DOWN -> {
                val adjustedBottom = MathHelper.lerp(percent, topEdge, bottomEdge)

                ScreenDrawing.texturedRect(matrices, x, y, width, pixels, full.image, leftEdge, topEdge, rightEdge, adjustedBottom, -1)
            }

            Direction.LEFT -> {
                val adjustedLeft = MathHelper.lerp(percent, rightEdge, leftEdge)

                ScreenDrawing.texturedRect(matrices, x, y, width, pixels, full.image, adjustedLeft, topEdge, rightEdge, bottomEdge, -1)
            }
        }
    }
}