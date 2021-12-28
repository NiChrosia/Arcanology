package arcanology.common.content

import arcanology.common.Arcanology
import arcanology.common.type.impl.screen.ModularScreenHandler
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.screen.ScreenHandlerType
import net.minecraft.util.hit.BlockHitResult
import nucleus.common.builtin.division.content.ScreenHandlerRegistrar

open class AScreenHandlerRegistrar(root: Arcanology) : ScreenHandlerRegistrar<Arcanology>(root) {
    val modular by memberOf(root.identify("modular")) { contextualTypeOf(::ModularScreenHandler)}

    open fun <T : ScreenHandler> contextualTypeOf(constructor: (Int, PlayerInventory, ScreenHandlerContext) -> T): ScreenHandlerType<T> {
        return ScreenHandlerType { syncId, inventory ->
            val hit = inventory.player.raycast(5.0, 1f, false) as BlockHitResult

            val world = inventory.player.world
            val pos = hit.blockPos

            constructor(syncId, inventory, ScreenHandlerContext.create(world, pos))
        }
    }
}