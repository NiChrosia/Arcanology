package arcanology.common.type.api.gui.widget

import io.github.cottonmc.cotton.gui.client.ScreenDrawing
import io.github.cottonmc.cotton.gui.widget.data.Texture
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.Identifier

open class WLayeredBar(
    background: Identifier,
    top: Identifier,
    full: Identifier,
    value: () -> Long,
    max: () -> Long,
    direction: Direction
) : WDynamicBar(background, full, value, max, direction) {
    open val top = Texture(top)

    override fun paint(matrices: MatrixStack, x: Int, y: Int, mouseX: Int, mouseY: Int) {
        super.paint(matrices, x, y, mouseX, mouseY)

        ScreenDrawing.texturedRect(matrices, x, y, width, height, top, -1)
    }
}