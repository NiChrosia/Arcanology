package nichrosia.arcanology

import net.devtech.arrp.api.RuntimeResourcePack
import net.fabricmc.api.ModInitializer
import nichrosia.arcanology.content.*
import nichrosia.arcanology.type.content.LoadableContent
import nichrosia.arcanology.data.DataGenerator
import nichrosia.arcanology.registry.Registrar
import nichrosia.arcanology.type.mod.IdentifiedModInit
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

@Suppress("unused")
object Arcanology : IdentifiedModInit, ModInitializer {
    override val modID = "arcanology"

    internal val content = arrayOf(
        AMaterials,
        ARecipes,

        DataGenerator
    )

    internal val registrars = Registrar.Companion::class.memberProperties.filterIsInstance<KProperty1<Registrar.Companion, Registrar<*>>>().map { it.get(Registrar) }

    internal val resourcePack = RuntimeResourcePack.create("arcanology:main", 1)
    internal val commonResourcePack = RuntimeResourcePack.create("c:arcanology", 1)

    override fun onInitialize() {
        DataGenerator.createLang()

        // Registrar.all.forEach { it.createAll(); it.registerAll() }

        registrars.forEach { it.createAll(); it.registerAll() }

        content.forEach(LoadableContent::load)
    }
}