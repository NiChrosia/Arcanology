package nichrosia.arcanology.registry.impl

import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.screen.ScreenHandlerType
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.ctype.gui.description.PulverizerGUIDescription
import nichrosia.arcanology.ctype.gui.description.RuneInfuserGUIDescription
import nichrosia.arcanology.registry.BasicRegistrar
import nichrosia.arcanology.registry.properties.RegistryProperty

open class GUIDescriptionRegistrar : BasicRegistrar<ScreenHandlerType<*>>() {
    val pulverizer by RegistryProperty("pulverizer") { create("pulverizer", ::PulverizerGUIDescription) }
    val runeInfuser by RegistryProperty("rune_infuser") { create("rune_infuser", ::RuneInfuserGUIDescription) }

    fun <T : ScreenHandler> create(key: String, screenHandler: (Int, PlayerInventory, ScreenHandlerContext) -> T): ScreenHandlerType<T> {
        return super.create(key, ScreenHandlerRegistry.registerSimple(Arcanology.idOf(key)) { syncId, inventory ->
            screenHandler(syncId, inventory, ScreenHandlerContext.EMPTY)
        })
    }
}