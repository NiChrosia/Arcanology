package arcanology.type.common.world.item.machine

import arcanology.type.common.world.block.entity.MachineBlockEntity
import arcanology.type.common.world.block.machine.MachineUpgrade
import net.minecraft.item.Item
import net.minecraft.item.ItemUsageContext
import net.minecraft.util.ActionResult

abstract class MachineUpgradeItem(settings: Settings) : Item(settings), MachineUpgrade {
    override val speedMultiplier = 1L

    override fun useOnBlock(context: ItemUsageContext): ActionResult {
        if (context.world.isClient) return ActionResult.PASS

        context.apply {
            val entity = world.getBlockEntity(blockPos)

            if (entity != null && entity is MachineBlockEntity<*, *, *, *> && entity.compatible(this@MachineUpgradeItem)) {
                apply(entity)

                context.stack.decrement(1)

                return ActionResult.SUCCESS
            }
        }

        return super.useOnBlock(context)
    }
}