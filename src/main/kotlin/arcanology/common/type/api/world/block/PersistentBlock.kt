package arcanology.common.type.api.world.block

import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

/** A block that continues ticking after being unloaded. */
abstract class PersistentBlock<E : BlockEntity>(settings: Settings) : BlockWithEntity(settings) {
    abstract val type: () -> BlockEntityType<E>

    init {
        blocks.add(this)
        active[this] = mutableListOf()
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBreak(world: World, pos: BlockPos, state: BlockState, player: PlayerEntity) {
        super.onBreak(world, pos, state, player)

        // remove broken entities
        val entity = world.getBlockEntity(pos) as? E
        entity?.let(activeEntities()::remove)
    }

    @Suppress("UNCHECKED_CAST") // if the entities aren't of the correct type, something is severely wrong
    open fun activeEntities(): MutableList<E> {
        return active[this]?.let { it as? MutableList<E> } ?: throw IllegalStateException("Given block has invalid active entity data.")
    }

    companion object {
        val blocks = mutableListOf<PersistentBlock<out BlockEntity>>()
        val active = mutableMapOf<PersistentBlock<out BlockEntity>, MutableList<out BlockEntity>>()

        @JvmStatic
        fun tickPersistentBlocks(world: ServerWorld) {
            for (block in blocks) {
                tickPersistentBlock(world, block)
            }
        }

        @Suppress("UNCHECKED_CAST")
        fun <E : BlockEntity> tickPersistentBlock(world: ServerWorld, block: PersistentBlock<E>) {
            val entities = block.activeEntities()

            for (entity in entities) {
                tickPersistentBlockEntity(world, block, entity)
            }
        }

        fun <E : BlockEntity> tickPersistentBlockEntity(world: ServerWorld, block: PersistentBlock<E>, entity: E) {
            val pos = entity.pos
            val state = entity.cachedState

            if (state.hasRandomTicks()) {
                state.randomTick(world, pos, world.random)
            }

            if (state.fluidState.hasRandomTicks()) {
                state.fluidState.onRandomTick(world, pos, world.random)
            }

            val ticker = block.getTicker(world, state, block.type())
            ticker?.tick(world, pos, state, entity)
        }
    }
}