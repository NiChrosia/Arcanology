package arcanology

import arcanology.registrar.category.ArcanologyCategory
import arcanology.type.common.datagen.RuntimeResourcePackManager
import arcanology.type.common.mod.IdentifiedMod
import net.fabricmc.api.ModInitializer
import nichrosia.common.record.registrar.Registrar
import org.apache.logging.log4j.LogManager

object Arcanology : IdentifiedMod, ModInitializer {
    override val modID = "arcanology"
    override val category = ArcanologyCategory()
    override val log = LogManager.getLogger() // defaults to class name

    val packManager = RuntimeResourcePackManager(modID)

    val Registrar.Companion.arcanology: ArcanologyCategory
        get() = category

    override fun onInitialize() {
        category.initialize()
        packManager.load()

        log.info("Mod loaded successfully.")
    }
}