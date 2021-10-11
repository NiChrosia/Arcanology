package nichrosia.arcanology.registry.impl.client

import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.client.render.block.entity.BlockEntityRenderer
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory
import nichrosia.arcanology.registry.EmptyRegistrar
import nichrosia.arcanology.registry.Registrar
import nichrosia.arcanology.registry.properties.RegistryProperty
import nichrosia.arcanology.type.content.block.entity.renderer.AltarBlockEntityRenderer

open class BlockEntityRendererRegistrar : EmptyRegistrar() {
    val altar by RegistryProperty("altar") { create(Registrar.blockEntity.altar, ::AltarBlockEntityRenderer) }

    fun <T : BlockEntity> create(type: BlockEntityType<T>, renderer: (BlockEntityRendererFactory.Context) -> BlockEntityRenderer<T>) {
        BlockEntityRendererRegistry.register(type, renderer)
    }
}