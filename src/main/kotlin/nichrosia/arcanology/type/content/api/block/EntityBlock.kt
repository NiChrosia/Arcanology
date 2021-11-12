package nichrosia.arcanology.type.content.api.block

import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType

interface EntityBlock<E : BlockEntity> {
    val type: BlockEntityType<E>
}