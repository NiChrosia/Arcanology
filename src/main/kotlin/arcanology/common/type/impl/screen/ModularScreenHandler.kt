package arcanology.common.type.impl.screen

import arcanology.common.Arcanology
import arcanology.common.type.api.machine.component.ModuleComponent
import arcanology.common.type.api.machine.module.MachineModule
import arcanology.common.type.impl.world.block.entity.MachineBlockEntity
import assemble.common.type.api.assembly.GradualAssembly
import io.github.cottonmc.cotton.gui.SyncedGuiDescription
import io.github.cottonmc.cotton.gui.widget.WPlainPanel
import io.github.cottonmc.cotton.gui.widget.WWidget
import io.github.cottonmc.cotton.gui.widget.data.Insets
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.ScreenHandlerContext
import kotlin.math.max

open class ModularScreenHandler(
    syncId: Int,
    inventory: PlayerInventory,
    context: ScreenHandlerContext,
) : SyncedGuiDescription(
    Arcanology.content.screenHandler.modular,
    syncId,
    inventory,
    getBlockInventory(context),
    getBlockPropertyDelegate(context)
) {
    val machine = getMachine(context)
    val module = getModule(context)
    
    val root = WPlainPanel().also {
        it.insets = Insets.ROOT_PANEL

        it.setSize(module.width, module.height)

        setRootPanel(it)
    }
    
    val MachineModule<out GradualAssembly<MachineBlockEntity>>.width: Int
        get() {
            val componentSpacing = (components.size - 1) * componentSpacing
            val componentWidth = components.sumOf { it.widget.width } + componentSpacing

            val alignmentSpacing = alignmentSpacing * 2 // gaps between 3 different types

            return max(componentWidth, playerInventoryWidth) + alignmentSpacing
        }

    val MachineModule<out GradualAssembly<MachineBlockEntity>>.height: Int
        get() {
            if (components.isEmpty()) return playerInventoryHeight

            val componentHeight = components.maxOf { it.widget.height }

            return componentHeight + playerInventoryHeight
        }

    init {
        addLeftAligned()
        addCenterAligned()
        addRightAligned()
        
        root.add(createPlayerInventoryPanel(), 0, root.height - playerInventoryHeight + 6)

        root.validate(this)
    }
    
    open fun addLeftAligned() {
        val leftAligned = module.components.filter { it.alignment < 0 }

        if (leftAligned.isEmpty()) return
        
        for (index in leftAligned.indices) {
            val component = leftAligned[index]

            val x = previousWidth(leftAligned, index)
            val y = centeredY(component)

            addComponent(component, x, y)
        }
    }

    open fun addCenterAligned() {
        val centerAligned = module.components.filter { it.alignment == 0 }

        if (centerAligned.isEmpty()) return

        val center = root.width / 2

        val componentWidth = centerAligned.sumOf { it.widget.width }
        val componentSpacing = (centerAligned.size - 1) * componentSpacing
        val centerWidth = componentWidth + componentSpacing
        
        val centerOffset = center - centerWidth / 2
        
        for (index in centerAligned.indices) {
            val component = centerAligned[index]
            
            val x = centerOffset + previousWidth(centerAligned, index)
            val y = centeredY(component)
            
            addComponent(component, x, y)
        }
    }
    
    open fun addRightAligned() {
        val rightAligned = module.components.filter { it.alignment > 0 }

        if (rightAligned.isEmpty()) return
        
        for (index in rightAligned.indices) {
            val component = rightAligned[index]
            
            val x = root.width - previousWidth(rightAligned, index)
            val y = centeredY(component)
            
            addComponent(component, x, y)
        }
    }

    open fun previousWidth(components: List<ModuleComponent<*, out WWidget>>, index: Int): Int {
        if (components.isEmpty() || index == 0) return 0
        
        val previous = components.filterIndexed { componentIndex, _ -> componentIndex < index }
        val spacing = previous.size * componentSpacing

        return previous.sumOf { it.widget.width } + spacing
    }
    
    open fun centeredY(component: ModuleComponent<*, out WWidget>): Int {
        return (root.height - playerInventoryHeight) / 2 - component.widget.height / 2
    }
    
    /** Adds the component using it's width & height. Exists because it's annoying to write out. */
    open fun addComponent(component: ModuleComponent<*, out WWidget>, x: Int, y: Int) {
        root.add(component.widget, x, y, component.widget.width, component.widget.height)
    }

    companion object {
        const val componentSpacing = 18 / 3
        const val alignmentSpacing = 24

        const val playerInventoryHeight = (18 * 3) + 4 + (18) // inventory + spacing + hotbar
        const val playerInventoryWidth = (18 * 7)

        fun getModule(context: ScreenHandlerContext): MachineModule<out GradualAssembly<MachineBlockEntity>> {
            return context.get { world, pos ->
                val entity = world.getBlockEntity(pos) as? MachineBlockEntity

                entity?.module
            }.get()
        }

        fun getMachine(context: ScreenHandlerContext): MachineBlockEntity {
            return context.get { world, pos ->
                world.getBlockEntity(pos) as? MachineBlockEntity
            }.get()
        }
    }
}