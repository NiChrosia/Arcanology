package nichrosia.arcanology.registrar.impl

import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.screen.ScreenHandlerType
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.registrar.lang.impl.BasicLanguageGenerator
import nichrosia.arcanology.type.content.gui.description.SmelterGuiDescription
import nichrosia.common.identity.ID
import nichrosia.common.record.registrar.content.ContentRegistrar

open class GUIDescriptionRegistrar : ContentRegistrar.Basic<ScreenHandlerType<*>>() {
    open val languageGenerator = BasicLanguageGenerator()

    val smelter by memberOf(Arcanology.identify("smelter")) { create(it, ::SmelterGuiDescription) }

    override fun <E : ScreenHandlerType<*>> publish(key: ID, value: E, registerIfAbsent: Boolean): E {
        return super.publish(key, value, registerIfAbsent).also {
            Arcanology.packManager.english.lang["${Arcanology.modID}.gui.title.${key.path}"] = languageGenerator.generateLang(key)
        }
    }

    fun <T : ScreenHandler> create(location: ID, screenHandler: (Int, PlayerInventory, ScreenHandlerContext) -> T): ScreenHandlerType<T> {
        return ScreenHandlerRegistry.registerSimple(location) { syncId, inventory ->
            screenHandler(syncId, inventory, ScreenHandlerContext.EMPTY)
        }
    }
}