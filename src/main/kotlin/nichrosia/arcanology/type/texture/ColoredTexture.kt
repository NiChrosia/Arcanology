package nichrosia.arcanology.type.texture

import io.github.cottonmc.cotton.gui.widget.data.Texture

data class ColoredTexture(val texture: Texture, val color: Int = 0xFF_FFFFFF.toInt())