package nichrosia.arcanology.registry.impl.client

import io.github.cottonmc.cotton.gui.widget.data.Texture
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry
import net.minecraft.client.MinecraftClient
import net.minecraft.client.texture.NativeImageBackedTexture
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.Fluids
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.type.content.gui.widget.WFluidBar
import nichrosia.arcanology.type.texture.ColoredTexture
import nichrosia.arcanology.util.scale
import nichrosia.arcanology.util.toBuffered
import nichrosia.arcanology.util.toNative
import nichrosia.common.identity.ID
import nichrosia.common.registry.type.lazy.BasicLazyRegistrar

open class FluidBarTextureRegistrar : BasicLazyRegistrar<Fluid, ColoredTexture>() {
    override fun convert(input: Fluid): ColoredTexture? {
        if (input == Fluids.EMPTY) return null

        val handler = FluidRenderHandlerRegistry.INSTANCE.get(input)

        val sprites = handler.getFluidSprites(null, null, input.defaultState)
        val bufferedImage = sprites[0].images[0].toBuffered()
        val scaled = bufferedImage.scale(WFluidBar.width, WFluidBar.height) // size of fluid bar widgets
        val texture = NativeImageBackedTexture(scaled.toNative())

        val color = handler.getFluidColor(null, null, input.defaultState)
        val ID = ID(Registry.FLUID.getId(input))

        MinecraftClient.getInstance().textureManager.registerTexture(ID, texture)

        return ColoredTexture(Texture(ID), color)
    }
}