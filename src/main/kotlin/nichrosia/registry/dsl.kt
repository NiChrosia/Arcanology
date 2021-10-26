package nichrosia.registry

import net.minecraft.block.Block
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import nichrosia.arcanology.registry.category.ArcanologyCategory.arcanology
import nichrosia.registry.delegates.RegistrarMember

val RegistrarMember<Block, out Block>.item: RegistrarMember<Item, BlockItem>
    get() = Registrar.arcanology.item.memberOf(location) { BlockItem(content, config.blockItemSettings) }