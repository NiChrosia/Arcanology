package nichrosia.arcanology

import net.fabricmc.fabric.api.gametest.v1.FabricGameTest
import net.minecraft.test.GameTest
import org.apache.logging.log4j.LogManager

@Suppress("unused")
open class ArcanologyTest {
    val log = LogManager.getLogger("ArcanologyTest")

    @GameTest(structureName = FabricGameTest.EMPTY_STRUCTURE)
    fun test() {
        log.info("Arcanology test loaded successfully.")
    }
}