package nichrosia.arcanology.type.texture

import io.github.cottonmc.cotton.gui.widget.data.Texture
import net.minecraft.client.MinecraftClient
import net.minecraft.client.texture.NativeImageBackedTexture
import nichrosia.arcanology.util.toNative
import nichrosia.common.identity.ID
import java.awt.image.BufferedImage

open class RawTexture(open val image: BufferedImage, open val textureID: ID) {
    val nativeTexture: NativeImageBackedTexture
        get() = NativeImageBackedTexture(image.toNative())

    val libGuiTexture: Texture
        get() = Texture(textureID)

    open fun register(ID: ID = textureID): ID {
        return ID.also {
            MinecraftClient.getInstance().textureManager.registerTexture(it, nativeTexture)
        }
    }
}