package arcanology.type.common.world.block.machine.progressor

import arcanology.type.common.sound.DurativeSoundEvent
import net.minecraft.block.BlockState
import net.minecraft.sound.SoundCategory
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

// recipe progressor needed to avoid duplicating valid() code
open class SoundProgressor(val recipe: RecipeProgressor<*, *>, val sound: DurativeSoundEvent) : Progressor {
    override val maxProgress = sound.length
    override var incrementAmount = 1L
    override var progress = 0L

    override fun valid(world: World, pos: BlockPos, state: BlockState): Boolean {
        return super.valid(world, pos, state) && recipe.valid(world, pos, state)
    }

    override fun progress(world: World, pos: BlockPos, state: BlockState) {
        if (progress == 0L) play(world, pos)

        super.progress(world, pos, state)
    }

    override fun complete(world: World, pos: BlockPos, state: BlockState) {
        super.complete(world, pos, state)

        play(world, pos)
    }

    open fun play(world: World, pos: BlockPos) {
        world.playSound(null, pos, sound, SoundCategory.BLOCKS, 1f, 1f)
    }
}