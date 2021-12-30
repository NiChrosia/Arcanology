package arcanology.common.content

import arcanology.common.Arcanology
import net.fabricmc.fabric.api.lookup.v1.block.BlockApiLookup
import net.minecraft.block.Block
import net.minecraft.block.entity.BlockEntity
import nucleus.common.builtin.division.ModRoot
import nucleus.common.builtin.division.content.BlockRegistrar
import nucleus.common.builtin.member.content.BlockMember

open class ABlockRegistrar(root: Arcanology) : BlockRegistrar<Arcanology>(root) {
    @Suppress("UNCHECKED_CAST")
    open fun <B : Block, E : BlockEntity, R : ModRoot<R>, A, C> BlockMember<B, R>.api(api: BlockApiLookup<A, C>, transformer: (E) -> A) {
        root.dispatcher.datagen.listeners.add {
            api.registerForBlocks({ _, _, _, entity, _ ->
                val holder = entity as? E

                holder?.let(transformer)
            }, value)
        }
    }
}