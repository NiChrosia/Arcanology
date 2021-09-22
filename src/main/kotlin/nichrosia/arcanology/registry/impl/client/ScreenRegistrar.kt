package nichrosia.arcanology.registry.impl.client

import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerType
import net.minecraft.text.Text
import nichrosia.arcanology.ctype.gui.PulverizerGUI
import nichrosia.arcanology.ctype.gui.RuneInfuserGUI
import nichrosia.arcanology.registry.BasicRegistrar
import nichrosia.arcanology.registry.Registrar
import nichrosia.arcanology.registry.properties.RegistryProperty

open class ScreenRegistrar : BasicRegistrar<Unit>() {
    fun <T : ScreenHandler, S> create(type: ScreenHandlerType<T>, factory: (T, PlayerInventory, Text) -> S) where S : Screen, S : ScreenHandlerProvider<T> {
        ScreenRegistry.register(type, factory)
    }

    val pulverizer by RegistryProperty("pulverizer") { create(Registrar.guiDescription.pulverizer, ::PulverizerGUI) }
    val runeInfuser by RegistryProperty("rune_infuser") { create(Registrar.guiDescription.runeInfuser, ::RuneInfuserGUI) }
}