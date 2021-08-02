package nichrosia.arcanology.type.tooltip.energy

import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.font.TextRenderer
import net.minecraft.client.gui.DrawableHelper
import net.minecraft.client.gui.tooltip.TooltipComponent
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.item.ItemRenderer
import net.minecraft.client.texture.TextureManager
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.text.LiteralText
import net.minecraft.util.Formatting
import net.minecraft.util.Identifier
import net.minecraft.util.math.Matrix4f
import nichrosia.arcanology.util.getEnergyString

class EnergyTooltipComponent(val data: EnergyTooltipData) : TooltipComponent {
    override fun getHeight(): Int = 18

    override fun getWidth(textRenderer: TextRenderer?): Int = 18

    override fun drawItems(
        textRenderer: TextRenderer,
        x: Int,
        y: Int,
        matrices: MatrixStack,
        itemRenderer: ItemRenderer,
        z: Int,
        textureManager: TextureManager
    ) {
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f)
        RenderSystem.setShaderTexture(0, Identifier("arcanology", "textures/gui/energy_icon.png"))
        DrawableHelper.drawTexture(matrices, x, y, z, 0f, 0f, 18, 18, 18, 18)
    }

    override fun drawText(
        textRenderer: TextRenderer,
        x: Int,
        y: Int,
        matrix4f: Matrix4f,
        immediate: VertexConsumerProvider.Immediate
    ) {
        val percentage = data.energy * 100 / data.maxEnergy
        val text =
            LiteralText("${getEnergyString(data.energy)} LF (${percentage.toInt()}%)").formatted(Formatting.GRAY)
        textRenderer.draw(text, x.toFloat() + 19, (y.toFloat() + 9) - textRenderer.fontHeight / 2, -1, true, matrix4f, immediate, false, 0, 15728880)
    }
}