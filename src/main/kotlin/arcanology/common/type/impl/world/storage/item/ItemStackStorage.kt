package arcanology.common.type.impl.world.storage.item

import net.fabricmc.fabric.api.transfer.v1.item.base.SingleStackStorage
import net.minecraft.item.ItemStack

class ItemStackStorage(
    @JvmField
    var stack: ItemStack
) : SingleStackStorage() {
    override fun getStack(): ItemStack {
        return stack
    }

    override fun setStack(stack: ItemStack) {
        this.stack = stack
    }
}