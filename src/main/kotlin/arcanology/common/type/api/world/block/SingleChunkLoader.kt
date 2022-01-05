package arcanology.common.type.api.world.block

import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.ChunkSectionPos

/** A block that automatically forces chunk loading on placing, and removes it on breaking. */
interface SingleChunkLoader {
    fun forceLoad(world: ServerWorld, pos: BlockPos) {
        val chunkPos = ChunkSectionPos.from(pos)

        world.setChunkForced(chunkPos.x, chunkPos.z, true)

        loaders.computeIfAbsent(chunkPos) { mutableListOf() }
        loaders[chunkPos]!!.add(this)
    }

    fun unloadForced(world: ServerWorld, pos: BlockPos) {
        val chunkPos = ChunkSectionPos.from(pos)

        // don't unload chunk if there's another chunkloader inside
        if (loaders[chunkPos]?.let { it.size > 1 } == true) {
            loaders[chunkPos]?.remove(this)

            return
        }

        world.setChunkForced(chunkPos.x, chunkPos.z, false)
        loaders[chunkPos]?.remove(this)
    }

    companion object {
        val loaders = mutableMapOf<ChunkSectionPos, MutableList<SingleChunkLoader>>()
    }
}