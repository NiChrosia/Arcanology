package nichrosia.arcanology.type.content.block

import net.devtech.arrp.json.blockstate.JState
import nichrosia.common.identity.ID

/** An interface for generating blockstates at runtime. */
interface BlockWithState {
    fun generateBlockState(ID: ID): Map<ID, JState> {
        return generateDefaultBlockState(ID)
    }

    companion object {
        fun generateDefaultBlockState(ID: ID): Map<ID, JState> {
            return mapOf(
                ID to JState.state(JState.variant(JState.model("${ID.namespace}:block/${ID.path}")))
            )
        }
    }
}