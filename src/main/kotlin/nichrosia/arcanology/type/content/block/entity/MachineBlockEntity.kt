@file:Suppress("DEPRECATION")

package nichrosia.arcanology.type.content.block.entity

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
import nichrosia.arcanology.Arcanology.arcanology
import nichrosia.arcanology.type.content.api.block.entity.*
import nichrosia.arcanology.type.content.api.block.entity.inventory.SimpleInventory
import nichrosia.arcanology.type.content.block.MachineBlock
import nichrosia.arcanology.type.content.gui.property.KPropertyDelegate
import nichrosia.arcanology.type.content.recipe.SimpleRecipe
import nichrosia.arcanology.type.nbt.NbtContainer
import nichrosia.arcanology.type.nbt.NbtObject
import nichrosia.arcanology.util.isServer
import nichrosia.arcanology.util.setState
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
) : BlockEntity(type, pos, state), NbtContainer, SimpleInventory, BlockEntityWithBlock<B>, EnergyBlockEntity, ScreenBlockEntity<S>, SoundBlockEntity, EnergyRecipeBlockEntity<T, R, SimpleRecipe.Type<R>> {
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