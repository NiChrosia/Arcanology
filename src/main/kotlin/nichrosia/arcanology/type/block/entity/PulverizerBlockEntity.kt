package nichrosia.arcanology.type.block.entity

import dev.technici4n.fasttransferlib.api.Simulation
import dev.technici4n.fasttransferlib.api.energy.EnergyApi
import dev.technici4n.fasttransferlib.api.energy.EnergyIo
import io.github.cottonmc.cotton.gui.PropertyDelegateHolder
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.ItemStack
import net.minecraft.screen.*
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import nichrosia.arcanology.content.ABlockEntityTypes
import nichrosia.arcanology.func.clamp
import nichrosia.arcanology.type.block.PulverizerBlock
import nichrosia.arcanology.type.block.entity.screen.handler.PulverizerScreenHandler
import nichrosia.arcanology.type.block.entity.type.AInventory

open class PulverizerBlockEntity(
    pos: BlockPos,
    state: BlockState
) : BlockEntity(
    ABlockEntityTypes.pulverizer,
    pos,
    state
), NamedScreenHandlerFactory, AInventory, PropertyDelegateHolder, EnergyIo {
    init {
        EnergyApi.SIDED.registerForBlockEntities({ blockEntity, _ ->
            blockEntity as? PulverizerBlockEntity
        }, type)
    }

    private val delegate = object : PropertyDelegate {
        override fun get(index: Int): Int {
            return when(index) {
                0 -> progress
                1 -> maxProgress
                2 -> energy.toInt()
                3 -> maxEnergy.toInt()
                else -> -1
            }
        }

        override fun set(index: Int, value: Int) {
            when(index) {
                0 -> {
                    progress = value
                }

                2 -> {
                    energy = value.toDouble()
                }
            }
        }

        override fun size(): Int {
            return 4
        }
    }

    private var energy = 0.0
    private val maxEnergy = (state.block as PulverizerBlock).tier.storage

    private var progress = 0
    private val maxProgress = 100

    override val inputSlots = intArrayOf(1)
    override val items: DefaultedList<ItemStack> = DefaultedList.ofSize(2, ItemStack.EMPTY)

    override fun getPropertyDelegate() = delegate
    override fun getEnergy() = energy
    override fun getEnergyCapacity() = energy

    override fun insert(amount: Double, simulation: Simulation?): Double {
        return when(simulation) {
            Simulation.ACT -> addEnergy(amount, false)
            Simulation.SIMULATE -> addEnergy(amount, true)
            else -> 0.0
        }
    }

    override fun extract(maxAmount: Double, simulation: Simulation?): Double {
        return when(simulation) {
            Simulation.ACT -> removeEnergy(maxAmount, false)
            Simulation.SIMULATE -> removeEnergy(maxAmount, true)
            else -> 0.0
        }
    }

    /** @return overflow */
    open fun addEnergy(amount: Double, simulated: Boolean): Double {
        return if (simulated) {
            if (energy + amount > energyCapacity) {
                energyCapacity - (energy + amount)
            } else 0.0
        } else {
            energy += amount

            if (energy > energyCapacity) {
                val prevEnergy = energy

                energy = energy.clamp(max = energyCapacity)

                energyCapacity - prevEnergy
            } else 0.0
        }
    }

    /** @return the amount of energy extracted */
    open fun removeEnergy(amount: Double, simulated: Boolean): Double {
        return if (simulated) {
            if (energy - amount < 0) energy else amount
        } else {
            energy -= amount
            
            if (energy < 0) {
                energy = energy.clamp()

                energy
            } else amount
        }
    }

    @Suppress("unused_parameter")
    fun tick(world: World, pos: BlockPos, state: BlockState) {

    }

    companion object {
        fun tick(world: World, pos: BlockPos, state: BlockState, entity: PulverizerBlockEntity) {
            entity.tick(world, pos, state)
        }
    }

    override fun createMenu(syncId: Int, inv: PlayerInventory, player: PlayerEntity): ScreenHandler? {
        return PulverizerScreenHandler(syncId, inv, ScreenHandlerContext.create(player.world, pos))
    }

    override fun getDisplayName(): Text {
        return TranslatableText(cachedState.block.translationKey)
    }
}