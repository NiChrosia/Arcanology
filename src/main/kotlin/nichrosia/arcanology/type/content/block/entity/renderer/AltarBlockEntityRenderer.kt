package nichrosia.arcanology.type.content.block.entity.renderer

import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.WorldRenderer
import net.minecraft.client.render.block.entity.BlockEntityRenderer
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory
import net.minecraft.client.render.model.json.ModelTransformation
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.item.ItemStack
import net.minecraft.util.math.Vec3f
import nichrosia.arcanology.util.minecraftClient
import nichrosia.arcanology.type.content.block.entity.AltarBlockEntity

@Suppress("MemberVisibilityCanBePrivate", "unused", "unused_parameter")
open class AltarBlockEntityRenderer(context: BlockEntityRendererFactory.Context) : BlockEntityRenderer<AltarBlockEntity> {
    override fun render(
        entity: AltarBlockEntity,
        tickDelta: Float,
        matrices: MatrixStack,
        vertexConsumers: VertexConsumerProvider,
        light: Int,
        overlay: Int
    ) {
        matrices.apply {
            push()

            translate(0.5, 1.25, 0.5)
            // scale(1f, 1f, 1f)
            multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((entity.world!!.time + tickDelta) * 2))

            val lightAbove = WorldRenderer.getLightmapCoordinates(entity.world, entity.pos.up())
            minecraftClient.itemRenderer.renderItem(
                entity.let { if (it.heartInitialized) ItemStack(it.heart) else ItemStack.EMPTY },
                ModelTransformation.Mode.GROUND,
                lightAbove,
                overlay,
                matrices,
                vertexConsumers,
                entity.pos.asLong().toInt()
            )

            pop()
        }
    }
}