package nichrosia.arcanology

import net.fabricmc.fabric.api.gametest.v1.FabricGameTest
import net.minecraft.test.GameTest
import net.minecraft.test.TestContext
import org.apache.logging.log4j.LogManager

@Suppress("unused", "unused_parameter")
open class ArcanologyTest {
    val log = LogManager.getLogger("ArcanologyTest")

    @GameTest(structureName = FabricGameTest.EMPTY_STRUCTURE)
    fun test(helper: TestContext) {
        helper.complete()

        log.info("Arcanology test completed successfully.")
    }
}