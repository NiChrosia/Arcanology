package nichrosia.arcanology.registry.impl.client

import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerType
import net.minecraft.text.Text
import nichrosia.arcanology.type.content.gui.SeparatorGUI
import nichrosia.arcanology.type.content.gui.RuneInfuserGUI
import nichrosia.arcanology.registry.EmptyRegistrar
import nichrosia.arcanology.registry.Registrar
import nichrosia.arcanology.registry.properties.RegistrarProperty

open class ScreenRegistrar : EmptyRegistrar() {
    val pulverizer by RegistrarProperty("pulverizer") { create(Registrar.guiDescription.separator, ::SeparatorGUI) }
    val runeInfuser by RegistrarProperty("rune_infuser") { create(Registrar.guiDescription.runeInfuser, ::RuneInfuserGUI) }

    fun <T : ScreenHandler, S> create(type: ScreenHandlerType<T>, factory: (T, PlayerInventory, Text) -> S) where S : Screen, S : ScreenHandlerProvider<T> {
        ScreenRegistry.register(type, factory)
    }
}