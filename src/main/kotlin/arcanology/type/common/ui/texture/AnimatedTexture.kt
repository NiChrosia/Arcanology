package arcanology.type.common.ui.texture

import io.github.cottonmc.cotton.gui.widget.data.Texture
import arcanology.Arcanology
import arcanology.util.collections.iterables.next
import arcanology.util.collections.iterables.previous

open class AnimatedTexture(open val frames: List<AnimationFrame>, open val loopBackwards: Boolean = false) : ColoredTexture(Texture(Arcanology.identify(""))) {
    override val texture: Texture
        get() = frame.coloredTexture.texture

    override val color: Int
        get() = frame.coloredTexture.color

    open var frame = frames.first()
    open var tick = 0

    open var reverting = false
        get() {
            if (frame == frames.last() && loopBackwards) {
                field = true
            }

            if (frame == frames.first() && loopBackwards && field) {
                field = false
            }

            return field
        }

    open fun tick() {
        tick++

        if (tick == frame.length) {
            frame = if (reverting) {
                frames.previous(frame)
            } else {
                frames.next(frame)
            }

            tick = 0
        }
    }

    open class AnimationFrame(open val coloredTexture: ColoredTexture, open val length: Int = 1)
}