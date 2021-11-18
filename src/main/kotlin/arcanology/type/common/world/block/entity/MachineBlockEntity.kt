@file:Suppress("DEPRECATION")

package arcanology.type.common.world.block.entity

import arcanology.Arcanology.arcanology
import arcanology.type.api.common.world.block.entity.*
import arcanology.type.api.common.world.block.inventory.SimpleInventory
import arcanology.type.common.world.block.MachineBlock
import arcanology.type.common.world.data.nbt.NbtContainer
import arcanology.type.common.world.data.nbt.NbtObject
import arcanology.type.common.world.recipe.SimpleRecipe
import arcanology.type.graphics.ui.gui.property.KPropertyDelegate
import arcanology.util.world.blocks.setState
import arcanology.util.world.isServer
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.nbt.NbtCompound
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World
import nichrosia.common.record.registrar.Registrar

/** A machine block entity with unified abstract methods for simplified usage. */
@Suppress("UnstableApiUsage", "DEPRECATION", "UNCHECKED_CAST")
abstract class MachineBlockEntity<B : MachineBlock<B, S, T>, S : ScreenHandler, R : SimpleRecipe<T, R>, T : MachineBlockEntity<B, S, R, T>>(
    type: BlockEntityType<T>,
    pos: BlockPos,
    state: BlockState,
    override val inputSlots: Array<Int>,
    override val outputSlots: Array<Int>,
    override val block: B,
    override val handlerConstructor: (Int, PlayerInventory, ScreenHandlerContext) -> S,
    override val title: Text = TranslatableText(block.translationKey),
    override val recipeType: SimpleRecipe.Type<R>,
    override val inputSides: Array<Direction> = Direction.values(),
    override val outputSides: Array<Direction> = Direction.values(),
) : BlockEntity(type, pos, state), NbtContainer, SimpleInventory, BlockEntityWithBlock<B>, EnergyBlockEntity,
    ScreenBlockEntity<S>, LoopingSoundBlockEntity, EnergyRecipeBlockEntity<T, R, SimpleRecipe.Type<R>> {
    override val nbtObjects = mutableListOf<NbtObject>()
    override val blockPos = pos
    override val tier = block.tier
    override val items = emptyInventoryOf(slotSize)
    override val energyStorage = energyStorageOf(tier)
    override val delegate = KPropertyDelegate(this::progress, tier::maxProgress, energyStorage::energyAmount, energyStorage::energyCapacity)
    override val sound = Registrar.arcanology.sound.machinery

    override var soundProgress = 0L
    override var progress by nbtFieldOf(0L)

    override fun writeNbt(nbt: NbtCompound): NbtCompound {
        return super<BlockEntity>.writeNbt(nbt.apply {
            super<SimpleInventory>.writeNbt(this)
            writeNbtObjects(this)
        })
    }

    override fun readNbt(nbt: NbtCompound) {
        super<BlockEntity>.readNbt(nbt.apply {
            super<SimpleInventory>.readNbt(this)
            readNbtObjects(this)
        })
    }

    open fun tick(world: World, pos: BlockPos, state: BlockState) {
        if (world.isServer) {
            var dirty = false

            if (canProgress(this as T, world)) {
                progress += tier.progressionSpeed

                setState(world, pos, state, MachineBlock.active, true)
                changeEnergyBy(tier.consumptionSpeed)
                progressSound(world, pos)

                dirty = true
            } else {
                setState(world, pos, state, MachineBlock.active, false)
            }

            if (progress >= tier.maxProgress) {
                onRecipeCompletion(this, world, pos, state)

                dirty = true
            }

            if (soundProgress >= sound.length) {
                onSoundCompletion(world, pos)

                dirty = true
            }

            if (dirty) markDirty()
        }
    }
}