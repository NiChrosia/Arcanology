package nichrosia.arcanology.registry.impl

import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.util.math.BlockPos
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.registry.Registrar
import nichrosia.arcanology.registry.RegistryRegistrar
import nichrosia.arcanology.registry.lang.LanguageGenerator
import nichrosia.arcanology.registry.lang.impl.BasicLanguageGenerator
import nichrosia.arcanology.registry.properties.RegistryProperty
import nichrosia.arcanology.type.block.entity.BlockEntityWithBlock
import nichrosia.arcanology.ctype.block.entity.*

open class BlockEntityRegistrar : RegistryRegistrar<BlockEntityType<*>>(Registry.BLOCK_ENTITY_TYPE, "block_entity") {
    override val languageGenerator: LanguageGenerator = BasicLanguageGenerator()

    fun <B : Block, E> create(name: String, blockEntity: (BlockPos, BlockState, B) -> E, block: B): BlockEntityType<E>
    where E : BlockEntity, E : BlockEntityWithBlock<B> {
        return super.create(name, FabricBlockEntityTypeBuilder.create({ pos: BlockPos, state: BlockState ->
            blockEntity(pos, state, block)
        }, block).build(null))
    }

    val reactiveBlock by RegistryProperty("reactive_block_entity") { create(it, ::ReactiveBlockEntity, Registrar.block.reactiveBlock) }
    val altar by RegistryProperty("altar_block_entity") { create(it, ::AltarBlockEntity, Registrar.block.altar) }
    val pulverizer by RegistryProperty("pulverizer_block_entity") { create(it, ::PulverizerBlockEntity, Registrar.block.pulverizer) }
    val runeInfuser by RegistryProperty("rune_infuser_block_entity") { create(it, ::RuneInfuserBlockEntity, Registrar.block.runeInfuser) }
}