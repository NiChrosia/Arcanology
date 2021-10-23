package nichrosia.arcanology.registry.impl

import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.util.math.BlockPos
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.registry.category.ArcanologyCategory.arcanology
import nichrosia.arcanology.type.content.block.entity.BlockEntityWithBlock
import nichrosia.arcanology.type.content.block.entity.AltarBlockEntity
import nichrosia.arcanology.type.content.block.entity.ReactiveBlockEntity
import nichrosia.arcanology.type.content.block.entity.RuneInfuserBlockEntity
import nichrosia.arcanology.type.content.block.entity.SeparatorBlockEntity
import nichrosia.common.identity.ID
import nichrosia.registry.BasicRegistrar
import nichrosia.registry.Registrar

open class BlockEntityRegistrar : BasicRegistrar<BlockEntityType<*>>() {
    //val languageGenerator: LanguageGenerator = BasicLanguageGenerator()

    val reactiveBlock by memberOf(ID(Arcanology.modID, "reactive_block_entity")) { create(::ReactiveBlockEntity, Registrar.arcanology.block.reactiveBlock) }
    val altar by memberOf(ID(Arcanology.modID, "altar_block_entity")) { create(::AltarBlockEntity, Registrar.arcanology.block.altar) }
    val separator by memberOf(ID(Arcanology.modID, "separator_block_entity")) { create(::SeparatorBlockEntity, Registrar.arcanology.block.separator) }
    val runeInfuser by memberOf(ID(Arcanology.modID, "rune_infuser_block_entity")) { create(::RuneInfuserBlockEntity, Registrar.arcanology.block.runeInfuser) }

    open fun <B, E> create(entityFactory: (BlockPos, BlockState, B) -> E, block: B): BlockEntityType<E> where B : BlockWithEntity, E : BlockEntity, E : BlockEntityWithBlock<B> {
        return FabricBlockEntityTypeBuilder.create({ pos, state ->
            entityFactory(pos, state, block)
        }, block).build(null)
    }
}