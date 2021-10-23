package nichrosia.arcanology.type.content.block

import net.devtech.arrp.api.RuntimeResourcePack
import net.devtech.arrp.json.blockstate.JState
import net.minecraft.util.Identifier
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.type.data.RuntimeResourcePackManager

/** An interface for generating blockstates at runtime. */
interface StatedBlock {
    fun generateBlockState(ID: Identifier, packManager: RuntimeResourcePackManager = Arcanology.packManager) {
        packManager.main.addBlockState(
            JState.state(JState.variant(JState.model("${Arcanology.modID}:block/${ID.path}"))),
            RuntimeResourcePack.id("${Arcanology.modID}:${ID.path}")
        )
    }

    companion object {
        fun generateDefaultBlockState(ID: Identifier, packManager: RuntimeResourcePackManager = Arcanology.packManager) {
            packManager.main.addBlockState(
                JState.state(JState.variant(JState.model("${Arcanology.modID}:block/${ID.path}"))),
                RuntimeResourcePack.id("${Arcanology.modID}:${ID.path}")
            )
        }
    }
}