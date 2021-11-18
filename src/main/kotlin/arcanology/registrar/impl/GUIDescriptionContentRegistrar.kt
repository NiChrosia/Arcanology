package arcanology.registrar.impl

import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.screen.ScreenHandlerType
import arcanology.Arcanology
import arcanology.type.common.registar.lang.LangRegistrar
import arcanology.type.common.ui.descriptor.SmelterGuiDescriptor
import nichrosia.common.identity.ID

open class GUIDescriptionContentRegistrar : LangRegistrar.Basic<ScreenHandlerType<*>>() {
    val smelter by memberOf(Arcanology.identify("smelter")) { create(it, ::SmelterGuiDescriptor) }

    override fun translationKeyOf(key: ID): String {
        return "${key.namespace}.gui.title.${key.path}"
    }

    fun <T : ScreenHandler> create(location: ID, constructor: (Int, PlayerInventory, ScreenHandlerContext) -> T): ScreenHandlerType<T> {
        return ScreenHandlerRegistry.registerSimple(location) { syncID, inventory ->
            constructor(syncID, inventory, ScreenHandlerContext.EMPTY)
        }
    }
}