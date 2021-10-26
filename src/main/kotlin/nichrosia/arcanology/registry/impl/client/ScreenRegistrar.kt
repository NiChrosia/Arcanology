package nichrosia.arcanology.registry.impl.client

import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerType
import net.minecraft.text.Text
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.registry.category.ArcanologyCategory.arcanology
import nichrosia.arcanology.type.content.gui.SeparatorGui
import nichrosia.registry.BasicRegistrar
import nichrosia.registry.Registrar

open class ScreenRegistrar : BasicRegistrar<Unit>() {
    val pulverizer by memberOf(Arcanology.identify("pulverizer")) { create(Registrar.arcanology.guiDescription.separator, ::SeparatorGui) }

    fun <T : ScreenHandler, S> create(type: ScreenHandlerType<T>, factory: (T, PlayerInventory, Text) -> S) where S : Screen, S : ScreenHandlerProvider<T> {
        ScreenRegistry.register(type, factory)
    }
}