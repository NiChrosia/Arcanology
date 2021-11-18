package arcanology.util.graphics

import net.minecraft.client.texture.NativeImage
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import javax.imageio.ImageIO

/** Scales the specified image using tiling. */
fun BufferedImage.scale(newWidth: Int, newHeight: Int): BufferedImage {
    val image = BufferedImage(newWidth, newHeight, type)

    for (y in 0 until newHeight) {
        for (x in 0 until newWidth) {
            image.setRGB(x, y, getRGB(x % width, y % height))
        }
    }

    return image
}

/** Splits the specified image into tiles of the given size. */
fun BufferedImage.verticallyTile(tileHeight: Int): List<BufferedImage> {
    return (0 until (height / tileHeight)).map {
        getSubimage(0, it * tileHeight, width, tileHeight)
    }
}

fun BufferedImage.toStream(): InputStream {
    val outputStream = ByteArrayOutputStream()
    ImageIO.write(this, "png", outputStream)
    return ByteArrayInputStream(outputStream.toByteArray())
}

fun BufferedImage.toNative(): NativeImage {
    return NativeImage.read(toStream())
}

fun NativeImage.toBuffered(): BufferedImage {
    return ImageIO.read(ByteArrayInputStream(bytes))
}