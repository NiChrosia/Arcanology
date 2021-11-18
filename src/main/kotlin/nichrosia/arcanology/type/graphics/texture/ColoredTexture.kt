package nichrosia.arcanology.type.graphics.texture

import io.github.cottonmc.cotton.gui.widget.data.Texture

open class ColoredTexture(open val texture: Texture, open val color: Int = 0xFF_FFFFFF.toInt())