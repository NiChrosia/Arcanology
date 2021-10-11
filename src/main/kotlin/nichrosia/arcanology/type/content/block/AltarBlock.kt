package nichrosia.arcanology.type.content.block

import net.devtech.arrp.json.models.JModel
import net.minecraft.block.BlockRenderType
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.registry.Registrar
import nichrosia.arcanology.type.block.ModeledBlock
import nichrosia.arcanology.type.content.block.entity.AltarBlockEntity
import nichrosia.arcanology.type.data.RuntimeResourcePackManager
import nichrosia.arcanology.util.variable

open class AltarBlock(settings: Settings) : BlockWithEntity(settings), ModeledBlock {
    override fun createBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return AltarBlockEntity(pos, state, this)
    }

    override fun getRenderType(state: BlockState): BlockRenderType {
        return BlockRenderType.MODEL
    }

    override fun <T : BlockEntity> getTicker(
        world: World,
        state: BlockState,
        type: BlockEntityType<T>
    ): BlockEntityTicker<T>? {
        return checkType(type, Registrar.blockEntity.altar) { world1: World, pos: BlockPos, state1: BlockState, be: BlockEntity ->
            AltarBlockEntity.tick(world1, pos, state1, be as AltarBlockEntity)
        }
    }

    override fun onBreak(world: World, pos: BlockPos, state: BlockState, player: PlayerEntity) {
        world.playSound(
            player,
            pos,
            SoundEvents.BLOCK_AMETHYST_BLOCK_BREAK,
            SoundCategory.BLOCKS,
            1f,
            1f
        )

        super.onBreak(world, pos, state, player)
    }

    override fun generateModel(ID: Identifier, packManager: RuntimeResourcePackManager) {
        packManager.main.addModel(
            JModel.model("minecraft:block/cube")
                .textures(
                    JModel.textures()
                        .particle("${Arcanology.modID}:block/altar_top")
                        .variable(
                            "east" to "${Arcanology.modID}:block/altar_side",
                            "west" to "${Arcanology.modID}:block/altar_side",
                            "north" to "${Arcanology.modID}:block/altar_side",
                            "south" to "${Arcanology.modID}:block/altar_side",
                            "down" to "${Arcanology.modID}:block/altar_bottom",
                            "up" to "${Arcanology.modID}:block/altar_top"
                        )
                ),
            packManager.blockModelID(ID.path)
        )
    }
}