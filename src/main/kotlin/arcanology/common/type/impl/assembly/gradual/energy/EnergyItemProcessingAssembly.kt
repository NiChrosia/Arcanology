package arcanology.common.type.impl.assembly.gradual.energy

import assemble.common.type.api.assembly.GradualAssembly
import assemble.common.type.api.storage.*
import assemble.common.type.impl.assembly.slot.gradual.GradualEnergyInput
import assemble.common.type.impl.assembly.slot.item.ItemStorageInput
import assemble.common.type.impl.assembly.slot.item.ItemStorageOutput
import assemble.common.type.impl.stack.IngredientStack
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier

open class EnergyItemProcessingAssembly<C> @JvmOverloads constructor(
    id: Identifier,
    val ingredient: IngredientStack,

    val result: ItemStack,
    val slots: List<Int> = listOf(0, 1),
    val consumption: Long,
    speed: Long,
    end: Long,
) : GradualAssembly<C>(
    id,
    listOf(ItemStorageInput(slots[0], ingredient)),
    listOf(ItemStorageOutput(slots[1], result)),

    listOf(GradualEnergyInput(consumption)),
    listOf(),
    speed,
    end,
) where C : ItemStorageInventory, C : EnergyStorageInventory, C : Progressable