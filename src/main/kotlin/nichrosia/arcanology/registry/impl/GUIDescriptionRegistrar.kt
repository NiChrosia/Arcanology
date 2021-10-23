package nichrosia.arcanology.registry.impl

import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.screen.ScreenHandlerType
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.registry.lang.LanguageGenerator
import nichrosia.arcanology.registry.lang.impl.BasicLanguageGenerator
import nichrosia.arcanology.type.content.gui.description.RuneInfuserGUIDescription
import nichrosia.arcanology.type.content.gui.description.SeparatorGUIDescription
import nichrosia.common.identity.ID
import nichrosia.registry.BasicRegistrar

open class GUIDescriptionRegistrar : BasicRegistrar<ScreenHandlerType<*>>() {
    open val languageGenerator: LanguageGenerator = BasicLanguageGenerator()

    val separator by memberOf(ID(Arcanology.modID, "separator")) { create(it, ::SeparatorGUIDescription) }
    val runeInfuser by memberOf(ID(Arcanology.modID, "rune_infuser")) { create(it, ::RuneInfuserGUIDescription) }

    override fun <E : ScreenHandlerType<*>> register(location: ID, value: E): E {
        Arcanology.packManager.englishLang.lang["${Arcanology.modID}.gui.title.${location.path}"] = languageGenerator.generateLang(location)

        return value
    }

    fun <T : ScreenHandler> create(location: ID, screenHandler: (Int, PlayerInventory, ScreenHandlerContext) -> T): ScreenHandlerType<T> {
        return ScreenHandlerRegistry.registerSimple(location.asIdentifier) { syncId, inventory ->
            screenHandler(syncId, inventory, ScreenHandlerContext.EMPTY)
        }
    }
}