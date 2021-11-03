package nichrosia.arcanology.registry.impl

import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.screen.ScreenHandlerType
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.registry.lang.LanguageGenerator
import nichrosia.arcanology.registry.lang.impl.BasicLanguageGenerator
import nichrosia.arcanology.type.content.gui.description.SmelterGuiDescription
import nichrosia.common.identity.ID
import nichrosia.common.registry.type.basic.BasicContentRegistrar

open class GUIDescriptionRegistrar : BasicContentRegistrar<ScreenHandlerType<*>>() {
    open val languageGenerator: LanguageGenerator = BasicLanguageGenerator()

    val smelter by memberOf(Arcanology.identify("smelter")) { create(it, ::SmelterGuiDescription) }

    override fun <E : ScreenHandlerType<*>> register(input: ID, output: E): E {
        Arcanology.packManager.english.lang["${Arcanology.modID}.gui.title.${input.path}"] = languageGenerator.generateLang(input)

        return output
    }

    fun <T : ScreenHandler> create(location: ID, screenHandler: (Int, PlayerInventory, ScreenHandlerContext) -> T): ScreenHandlerType<T> {
        return ScreenHandlerRegistry.registerSimple(location) { syncId, inventory ->
            screenHandler(syncId, inventory, ScreenHandlerContext.EMPTY)
        }
    }
}