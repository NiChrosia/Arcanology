package nichrosia.arcanology.type.content.gui.widget

import io.github.cottonmc.cotton.gui.client.ScreenDrawing
import io.github.cottonmc.cotton.gui.widget.WBar
import io.github.cottonmc.cotton.gui.widget.data.Texture
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry
import net.minecraft.client.MinecraftClient
import net.minecraft.client.texture.NativeImageBackedTexture
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.fluid.Fluid
import net.minecraft.item.Items
import net.minecraft.util.math.MathHelper
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.util.clamp
import nichrosia.arcanology.util.scale
import nichrosia.arcanology.util.toBuffered
import nichrosia.arcanology.util.toNative
import kotlin.math.roundToInt

@Suppress("UnstableApiUsage")
open class WFluidBar(
    minField: Int = 4,
    maxField: Int = 5,
    direction: Direction = Direction.UP,
    val fluidProvider: () -> Fluid,
) : WBar(background, background, minField, maxField, direction) {
    val barTexture = { Texture(fluids[fluidProvider()]?.first ?: emptyFluid) }
    val fluidColor = { fluids[fluidProvider()]?.second }

    override fun paint(matrices: MatrixStack?, x: Int, y: Int, mouseX: Int, mouseY: Int) {
		val percent = clamp(properties.get(field) / properties[max].toFloat(), 0f, 1f).let { (it * height).toInt() / height.toFloat() }
		val barSize = (height * percent).roundToInt()//.also { if (it <= 0) return }

        ScreenDrawing.texturedRect(matrices, x, (y + getHeight()) - barSize, getWidth(), barSize, barTexture().image(), barTexture().u1(), MathHelper.lerp(percent, barTexture().v2(), barTexture().v1()), barTexture().u2(), barTexture().v2(), fluidColor() ?: 0xFF_FFFFFF.toInt())
        ScreenDrawing.texturedRect(matrices, x, y, getWidth(), getHeight(), bg, 0xFF_FFFFFF.toInt())
    }
    
    companion object {
        val background = Arcanology.identify("textures/gui/widget/liquid_bar_empty.png")
        val emptyFluid = Arcanology.identify("textures/gui/widget/liquid_empty.png")

        val fluids = mutableMapOf(
            *Registry.FLUID.toList().filter { it.bucketItem != Items.AIR }.map { fluid ->
                fluid!! to FluidRenderHandlerRegistry.INSTANCE.get(fluid).let {
                    it.getFluidSprites(null, null, fluid.defaultState)[0].let { sprite ->
                        sprite.images[0].toBuffered().scale(18, 84)
                    } to it.getFluidColor(null, null, fluid.defaultState)
                }
            }.map {
                MinecraftClient.getInstance().textureManager.run {
                    registerTexture(Registry.FLUID.getId(it.first), NativeImageBackedTexture(it.second.first.toNative()))
                    it.first to (Registry.FLUID.getId(it.first) to it.second.second)
                }
            }.toTypedArray()
        )
    }
}