package nichrosia.arcanology.registry.impl.client

import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.client.render.block.entity.BlockEntityRenderer
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.registry.EmptyRegistrar
import nichrosia.arcanology.registry.category.ArcanologyCategory.arcanology
import nichrosia.arcanology.type.content.block.entity.renderer.AltarBlockEntityRenderer
import nichrosia.common.identity.ID
import nichrosia.registry.Registrar

open class BlockEntityRendererRegistrar : EmptyRegistrar() {
    val altar by memberOf(ID(Arcanology.modID, "altar")) { create(Registrar.arcanology.blockEntity.altar, ::AltarBlockEntityRenderer) }

    fun <T : BlockEntity> create(type: BlockEntityType<T>, renderer: (BlockEntityRendererFactory.Context) -> BlockEntityRenderer<T>) {
        BlockEntityRendererRegistry.register(type, renderer)
    }
}