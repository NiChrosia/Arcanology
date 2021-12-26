package arcanology.common.type.impl.screen

import arcanology.common.Arcanology
import arcanology.common.type.api.machine.module.MachineModule
import arcanology.common.type.impl.world.block.entity.MachineBlockEntity
import assemble.common.type.api.assembly.GradualAssembly
import io.github.cottonmc.cotton.gui.SyncedGuiDescription
import io.github.cottonmc.cotton.gui.widget.WPlainPanel
import io.github.cottonmc.cotton.gui.widget.data.Insets
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.ScreenHandlerContext
import kotlin.math.max

open class ModularScreenHandler(syncId: Int, inventory: PlayerInventory, context: ScreenHandlerContext) : SyncedGuiDescription(
    Arcanology.content.screenHandler.modular,
    syncId,
    inventory,
    getBlockInventory(context),
    getBlockPropertyDelegate(context)
) {
    val module = getModule(context)

    init {
        val root = WPlainPanel().also {
            it.insets = Insets.ROOT_PANEL

            val (width, height) = getSize(module)
            it.setSize(width, height)

            setRootPanel(it)
        }

        for (index in module.components.indices) {
            val component = module.components[index]

            val beforeIndex = module.components.filterIndexed { otherIndex, _ -> otherIndex < index }

            val x = beforeIndex.fold(0) { total, c ->
                total + c.widget.width + componentSpacing
            }

            val center = (root.height - playerInventoryHeight) / 2
            val y = center - component.widget.height / 2

            root.add(component.widget, x, y)
        }

        root.add(createPlayerInventoryPanel(), 0, root.height - playerInventoryHeight)
    }

    companion object {
        const val componentSpacing = 18 / 3

        const val playerInventoryHeight = (18 * 3) + 4 + 18 // inventory + spacing + hotbar
        const val playerInventoryWidth = 18 * 7

        fun getModule(context: ScreenHandlerContext): MachineModule<out GradualAssembly<MachineBlockEntity>> {
            return context.get { world, pos ->
                val entity = world.getBlockEntity(pos) as? MachineBlockEntity

                entity?.module
            }.get()
        }

        fun getSize(module: MachineModule<out GradualAssembly<MachineBlockEntity>>): Pair<Int, Int> {
            val padding = max(module.components.size - 1, 0) * componentSpacing

            val width = max(module.components.sumOf { it.widget.width } + padding, playerInventoryWidth)
            val height = (if (module.components.isNotEmpty()) module.components.maxOf { it.widget.height } else 0) + playerInventoryHeight

            return width to height
        }
    }
}