package nichrosia.arcanology.type.content.block

import net.devtech.arrp.json.blockstate.JState
import nichrosia.common.identity.ID

/** An interface for generating blockstates at runtime. */
interface MultistateBlock {
    fun generateBlockstate(ID: ID): Map<ID, JState> {
        return generateDefaultBlockstate(ID)
    }

    companion object {
        fun generateDefaultBlockstate(ID: ID): Map<ID, JState> {
            return mapOf(
                ID to JState.state(JState.variant(JState.model("${ID.namespace}:block/${ID.path}")))
            )
        }
    }
}