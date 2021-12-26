package arcanology.common

import arcanology.common.content.ArcanologyContent
import net.fabricmc.api.ModInitializer
import nucleus.common.builtin.division.ModRoot

object Arcanology : ModRoot<Arcanology>("arcanology"), ModInitializer {
    override val instance = this
    override val content = ArcanologyContent(instance)

    override fun onInitialize() {
        launch()
    }
}