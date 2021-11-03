package nichrosia.arcanology.registry.impl.client

import io.github.cottonmc.cotton.gui.widget.data.Texture
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry
import net.minecraft.client.MinecraftClient
import net.minecraft.client.texture.NativeImageBackedTexture
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.Fluids
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.type.content.gui.widget.WFluidBar
import nichrosia.arcanology.type.texture.AnimatedTexture
import nichrosia.arcanology.type.texture.ColoredTexture
import nichrosia.arcanology.util.scale
import nichrosia.arcanology.util.toBuffered
import nichrosia.arcanology.util.toNative
import nichrosia.arcanology.util.verticalTiles
import nichrosia.common.identity.ID
import nichrosia.common.registry.type.lazy.BasicLazyRegistrar

open class FluidBarTextureRegistrar : BasicLazyRegistrar<Fluid, AnimatedTexture>() {
    override fun convert(input: Fluid): AnimatedTexture? {
        if (input == Fluids.EMPTY) return null

        val handler = FluidRenderHandlerRegistry.INSTANCE.get(input)
        val color = handler.getFluidColor(null, null, input.defaultState)

        val sprites = handler.getFluidSprites(null, null, input.defaultState)
        val sprite = sprites[0]
        val image = sprite.images[0].toBuffered()
        val tiles = image.verticalTiles(sprite.height)

        val frames = tiles.mapIndexed { index, tile ->
            val animation = sprite.animation!!
            val frame = animation.frames[index]

            val scaled = tile.scale(WFluidBar.width, WFluidBar.height)

            val texture = NativeImageBackedTexture(scaled.toNative())
            val vanillaID = Registry.FLUID.getId(input)
            val ID = ID(vanillaID).path { "${it}_frame_$index" }

            MinecraftClient.getInstance().textureManager.registerTexture(ID, texture)

            AnimatedTexture.AnimationFrame(ColoredTexture(Texture(ID), color), frame.time)
        }

        return AnimatedTexture(frames, true)
    }
}