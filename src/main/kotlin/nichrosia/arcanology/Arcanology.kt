package nichrosia.arcanology

import net.fabricmc.api.ModInitializer
import nichrosia.arcanology.content.*
import nichrosia.arcanology.data.RuntimeResourcePackManager
import nichrosia.arcanology.registry.Registrar
import nichrosia.arcanology.type.content.LoadableContent
import nichrosia.arcanology.type.mod.IdentifiedMod
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

@Suppress("unused")
object Arcanology : IdentifiedMod, ModInitializer {
    override val modID = "arcanology"

    val registrars = Registrar.Companion::class.memberProperties.filterIsInstance<KProperty1<Registrar.Companion, Registrar<*>>>().map { it.get(Registrar) }
    val resourceManager = RuntimeResourcePackManager("arcanology:main", "c:arcanology")
    val content = arrayOf(AMaterials, ARecipes)

    override fun onInitialize() {
        registrars.forEach(Registrar<*>::createAll)
        content.forEach(LoadableContent::load)
        registrars.forEach(Registrar<*>::registerAll)

        resourceManager.load()
    }
}