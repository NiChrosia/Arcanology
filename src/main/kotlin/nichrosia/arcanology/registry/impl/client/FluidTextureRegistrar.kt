package nichrosia.arcanology.registry.impl.client

import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry
import net.minecraft.fluid.Fluid
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.type.registar.TextureRegistrar
import nichrosia.arcanology.type.texture.AnimatedTexture
import nichrosia.arcanology.type.texture.ColoredTexture
import nichrosia.arcanology.type.texture.RawTexture
import nichrosia.arcanology.util.scale
import nichrosia.arcanology.util.toBuffered
import nichrosia.arcanology.util.verticallyTile
import nichrosia.common.identity.ID

open class FluidTextureRegistrar : TextureRegistrar<Fluid, AnimatedTexture>() {
    override fun convert(input: Fluid, width: Int, height: Int): AnimatedTexture? {
        val handler = FluidRenderHandlerRegistry.INSTANCE.get(input) ?: return null

        val color = handler.getFluidColor(null, null, input.defaultState)

        val sprites = handler.getFluidSprites(null, null, input.defaultState)
        val sprite = sprites[0]

        val image = sprite.images[0].toBuffered()
        val tiles = image.verticallyTile(sprite.height)

        val frames = tiles.mapIndexed { index, tile ->
            val animation = sprite.animation!!
            val frame = animation.frames[index]

            val scaled = tile.let {
                if (width != defaultWidth && height != defaultHeight) {
                    it.scale(width, height)
                } else it
            }

            val vanillaID = Registry.FLUID.getId(input)
            val ID = ID(vanillaID).path { "${it}_frame_${index}_size_${width}x${height}" }

            val texture = RawTexture(scaled, ID).also {
                it.register()
            }

            AnimatedTexture.AnimationFrame(ColoredTexture(texture.libGuiTexture, color), frame.time)
        }

        return AnimatedTexture(frames, true)
    }
}