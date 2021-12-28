package arcanology.common.type.api.gui.widget

import io.github.cottonmc.cotton.gui.client.ScreenDrawing
import io.github.cottonmc.cotton.gui.widget.WBar
import io.github.cottonmc.cotton.gui.widget.data.Texture
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.Identifier
import net.minecraft.util.math.MathHelper
import kotlin.math.roundToInt

@Suppress("UNCHECKED_CAST")
open class WLayeredBar(
    background: Identifier,
    top: Identifier,
    full: Identifier,
    open val value: () -> Long,
    open val max: () -> Long
) : WBar(background, full, 0, 0, Direction.UP) {
    open val background = Texture(background)
    open val top = Texture(top)
    open val full = Texture(full)

    override fun paint(matrices: MatrixStack, x: Int, y: Int, mouseX: Int, mouseY: Int) {
        ScreenDrawing.texturedRect(matrices, x, y, width, height, background, -1)

        val percent = value() / max().toFloat()
        val pixels = (percent * height).roundToInt()

        val offsetY = y + height - pixels
        val topEdge = MathHelper.lerp(percent, full.v2, full.v1)

        ScreenDrawing.texturedRect(matrices, x, offsetY, width, pixels, full.image, full.u1, topEdge, full.u2, full.v2, -1)

        ScreenDrawing.texturedRect(matrices, x, y, width, height, top, -1)
    }
}