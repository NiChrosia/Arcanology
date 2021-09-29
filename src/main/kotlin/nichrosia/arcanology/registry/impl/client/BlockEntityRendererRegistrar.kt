package nichrosia.arcanology.registry.impl.client

import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.client.render.block.entity.BlockEntityRenderer
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory
import nichrosia.arcanology.type.content.block.entity.renderer.AltarBlockEntityRenderer
import nichrosia.arcanology.registry.ClientRegistrar
import nichrosia.arcanology.registry.EmptyRegistrar
import nichrosia.arcanology.registry.Registrar
import nichrosia.arcanology.registry.properties.RegistryProperty
import nichrosia.arcanology.util.blockEntityRendererRegistry

open class BlockEntityRendererRegistrar : EmptyRegistrar(), ClientRegistrar {
    val altar by RegistryProperty("altar") { create(Registrar.blockEntity.altar, ::AltarBlockEntityRenderer) }

    fun <T : BlockEntity> create(type: BlockEntityType<T>, renderer: (BlockEntityRendererFactory.Context) -> BlockEntityRenderer<T>) {
        blockEntityRendererRegistry.register(type, renderer)
    }
}