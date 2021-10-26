package nichrosia.arcanology.registry.impl.client

import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.client.render.block.entity.BlockEntityRenderer
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory
import nichrosia.registry.BasicRegistrar

open class BlockEntityRendererRegistrar : BasicRegistrar<Unit>() {
    fun <T : BlockEntity> create(type: BlockEntityType<T>, renderer: (BlockEntityRendererFactory.Context) -> BlockEntityRenderer<T>) {
        BlockEntityRendererRegistry.register(type, renderer)
    }
}