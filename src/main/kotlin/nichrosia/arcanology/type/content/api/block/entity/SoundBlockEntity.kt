package nichrosia.arcanology.type.content.api.block.entity

import net.minecraft.sound.SoundCategory
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import nichrosia.arcanology.type.sound.DurativeSoundEvent

interface SoundBlockEntity {
    val sound: DurativeSoundEvent

    var soundProgress: Long

    fun progressSound(world: World, pos: BlockPos) {
        if (soundProgress == 0L) {
            world.playSound(null, pos, sound, SoundCategory.BLOCKS, 1f, 1f)
        }

        soundProgress++
    }

    fun onSoundCompletion(world: World, pos: BlockPos) {
        soundProgress = 0L

        world.playSound(null, pos, sound, SoundCategory.BLOCKS, 1f, 1f)
    }
}