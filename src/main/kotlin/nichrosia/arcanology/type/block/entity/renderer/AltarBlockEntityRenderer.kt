package nichrosia.arcanology.type.block.entity.renderer

import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.WorldRenderer
import net.minecraft.client.render.block.entity.BlockEntityRenderer
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory
import net.minecraft.client.render.model.json.ModelTransformation
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.util.math.Vec3f
import nichrosia.arcanology.content.AItems
import nichrosia.arcanology.type.block.entity.AltarBlockEntity

@Suppress("MemberVisibilityCanBePrivate", "unused")
open class AltarBlockEntityRenderer(context: BlockEntityRendererFactory.Context) : BlockEntityRenderer<AltarBlockEntity> {
    protected val elementalHeartStack = ItemStack(AItems.arcaneHeart)

    override fun render(
        entity: AltarBlockEntity,
        tickDelta: Float,
        matrices: MatrixStack,
        vertexConsumers: VertexConsumerProvider,
        light: Int,
        overlay: Int
    ) {
        matrices.run {
            push()

            translate(0.5, 1.25, 0.5)
            // scale(1f, 1f, 1f)
            multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((entity.world!!.time + tickDelta) * 2))

            val lightAbove = WorldRenderer.getLightmapCoordinates(entity.world, entity.pos.up())
            MinecraftClient.getInstance().itemRenderer.renderItem(
                elementalHeartStack,
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