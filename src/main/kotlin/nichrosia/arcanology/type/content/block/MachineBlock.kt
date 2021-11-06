@file:Suppress("UnstableApiUsage", "deprecation")

package nichrosia.arcanology.type.content.block

import net.devtech.arrp.json.blockstate.JState
import net.devtech.arrp.json.models.JModel
import net.minecraft.block.*
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.SidedInventory
import net.minecraft.item.BlockItem
import net.minecraft.item.ItemPlacementContext
import net.minecraft.screen.ScreenHandler
import net.minecraft.state.StateManager
import net.minecraft.state.property.BooleanProperty
import net.minecraft.state.property.Properties
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.ItemScatterer
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.registry.Registry
import net.minecraft.world.World
import net.minecraft.world.WorldAccess
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.Arcanology.arcanology
import nichrosia.arcanology.type.content.block.entity.MachineBlockEntity
import nichrosia.arcanology.type.content.block.settings.ArcanologyBlockSettings
import nichrosia.arcanology.type.data.runtimeresource.RuntimeResourcePackManager
import nichrosia.arcanology.type.data.runtimeresource.tag.BlockTag
import nichrosia.arcanology.type.data.runtimeresource.tag.ContentTag
import nichrosia.arcanology.type.energy.EnergyTier
import nichrosia.arcanology.util.addProperties
import nichrosia.common.identity.ID
import nichrosia.common.record.registrar.Registrar
import team.reborn.energy.api.EnergyStorage

@Suppress("UNCHECKED_CAST")
abstract class MachineBlock<B : MachineBlock<B, S, E>, S : ScreenHandler, E : MachineBlockEntity<B, S, *, E>>(
    settings: ArcanologyBlockSettings,
    val entityConstructor: (BlockPos, BlockState, B) -> E,
    val type: () -> BlockEntityType<E>,
    val tier: EnergyTier
) : BlockWithEntity(settings), InventoryProvider, ModeledBlock, MultistateBlock, TaggedBlock, ItemBlock, LootableBlock {
    override val tags: List<ContentTag<Block>> = listOf(
        BlockTag.Basic(BlockTag.pickaxeMineable, mutableListOf(this)),
        BlockTag.Basic(BlockTag.needsToolLevelX(settings.miningLevel), mutableListOf(this))
    )

    override val item: BlockItem = BlockItem(this, Registrar.arcanology.item.settings)

    init {
        defaultState = stateManager.defaultState.with(Properties.HORIZONTAL_FACING, Direction.NORTH).with(active, false)

        EnergyStorage.SIDED.registerForBlocks({ _, _, _, blockEntity, _ ->
            (blockEntity as? MachineBlockEntity<*, *, *, *>)?.energyStorage
        }, this)
    }

    override fun createBlockEntity(pos: BlockPos, state: BlockState): E {
        return entityConstructor(pos, state, this as B)
    }

    override fun getRenderType(state: BlockState): BlockRenderType {
        return BlockRenderType.MODEL
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        builder.add(Properties.HORIZONTAL_FACING, active)
    }

    override fun getPlacementState(ctx: ItemPlacementContext): BlockState {
        return defaultState.with(Properties.HORIZONTAL_FACING, ctx.playerFacing.opposite).with(active, false) as BlockState
    }

    override fun <T : BlockEntity> getTicker(
        world: World,
        state: BlockState,
        type: BlockEntityType<T>
    ): BlockEntityTicker<T>? {
        return checkType(type, type()) { checkedWorld, checkedPos, checkedState, machine ->
            (machine as? MachineBlockEntity<*, *, *, *>)?.tick(checkedWorld, checkedPos, checkedState)
        }
    }

    override fun onUse(
        state: BlockState,
        world: World,
        pos: BlockPos,
        player: PlayerEntity,
        hand: Hand,
        hit: BlockHitResult
    ): ActionResult {
        player.openHandledScreen(state.createScreenHandlerFactory(world, pos))

        return ActionResult.SUCCESS
    }

    override fun onStateReplaced(
        state: BlockState,
        world: World,
        pos: BlockPos,
        newState: BlockState,
        moved: Boolean
    ) {
        if (state.block !== newState.block) {
            val blockEntity = world.getBlockEntity(pos)

            if (blockEntity is MachineBlockEntity<*, *, *, *>) {
                ItemScatterer.spawn(world, pos, blockEntity)

                world.updateComparators(pos, this)
            }

            super.onStateReplaced(state, world, pos, newState, moved)
        }
    }

    override fun hasComparatorOutput(state: BlockState?): Boolean {
        return true
    }

    override fun getComparatorOutput(state: BlockState, world: World, pos: BlockPos): Int {
        return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos))
    }

    override fun getInventory(state: BlockState, world: WorldAccess, pos: BlockPos): SidedInventory {
        return world.getBlockEntity(pos) as MachineBlockEntity<*, *, *, *>
    }

    override fun generateBlockstate(ID: ID): Map<ID, JState> {
        val name = Registry.BLOCK.getId(this).path

        val variant = JState.variant()
        mapOf("north" to 0, "east" to 90, "south" to 180, "west" to 270).forEach { (direction, rotation) ->
            arrayOf(true, false).forEach { active ->
                variant.put(
                    "facing=${direction},active=${active}",
                    JState.model(
                        "${Arcanology.modID}:block/$name".run { if (active) this + "_active" else this }
                    ).run { if (rotation > 0) y(rotation) else this }
                )
            }
        }

        return mapOf(ID to JState.state(variant))
    }

    override fun generateModel(ID: ID, packManager: RuntimeResourcePackManager): Map<ID, JModel> {
        val side = "${Arcanology.modID}:block/${ID.path}_side"

        return mapOf(
            packManager.blockModelID(ID) to JModel.model("minecraft:block/cube")
                .textures(
                    JModel.textures()
                        .particle("${Arcanology.modID}:block/${ID.path}_top")
                        .addProperties(
                            "east" to side,
                            "west" to side,
                            "north" to "${Arcanology.modID}:block/${ID.path}_front",
                            "south" to side,
                            "down" to "${Arcanology.modID}:block/${ID.path}_bottom",
                            "up" to "${Arcanology.modID}:block/${ID.path}_top"
                        )
                ),
            packManager.blockModelID(ID.path { it + "_active" }) to JModel.model("minecraft:block/cube")
                .textures(
                    JModel.textures()
                        .particle("${Arcanology.modID}:block/${ID.path}_top")
                        .addProperties(
                            "east" to side,
                            "west" to side,
                            "north" to "${Arcanology.modID}:block/${ID.path}_front_active",
                            "south" to side,
                            "down" to "${Arcanology.modID}:block/${ID.path}_bottom",
                            "up" to "${Arcanology.modID}:block/${ID.path}_top"
                        )
                )
        )
    }

    companion object {
        val active = BooleanProperty.of("active")
    }
}