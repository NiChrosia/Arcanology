package nichrosia.arcanology.registry.impl

import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.screen.ScreenHandlerType
import net.minecraft.util.Identifier
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.registry.BasicRegistrar
import nichrosia.arcanology.registry.properties.RegistrarProperty
import nichrosia.arcanology.type.content.gui.description.RuneInfuserGUIDescription
import nichrosia.arcanology.type.content.gui.description.SeparatorGUIDescription
import nichrosia.arcanology.util.capitalize

open class GUIDescriptionRegistrar : BasicRegistrar<ScreenHandlerType<*>>() {
    val separator by RegistrarProperty("separator") { create("pulverizer", ::SeparatorGUIDescription) }
    val runeInfuser by RegistrarProperty("rune_infuser") { create("rune_infuser", ::RuneInfuserGUIDescription) }

    override fun <E : ScreenHandlerType<*>> register(key: Identifier, value: E): E {
        Arcanology.packManager.englishLang.lang["${Arcanology.modID}.gui.title.${key.path}"] = key.path.capitalize()

        return super.register(key, value)
    }

    fun <T : ScreenHandler> create(key: String, screenHandler: (Int, PlayerInventory, ScreenHandlerContext) -> T): ScreenHandlerType<T> {
        return super.create(key, ScreenHandlerRegistry.registerSimple(Arcanology.idOf(key)) { syncId, inventory ->
            val guiDescription = screenHandler(syncId, inventory, ScreenHandlerContext.EMPTY)

            guiDescription
        })
    }
}