package nichrosia.arcanology.type.content.block

import net.devtech.arrp.json.loot.JLootTable
import nichrosia.common.identity.ID

interface LootableBlock {
    fun generateLootTable(ID: ID, itemID: ID = ID): Map<ID, JLootTable> {
        return generateDefaultLootTable(ID, itemID)
    }

    companion object {
        // item ID is assumed to be the same as the block's
        fun generateDefaultLootTable(ID: ID, itemID: ID = ID): Map<ID, JLootTable> {
            return mapOf(
                ID to JLootTable.loot("minecraft:block")
                    .pool(
                        JLootTable.pool()
                        .rolls(1)
                        .entry(
                            JLootTable.entry()
                            .type("minecraft:item")
                            .name(itemID.split())
                        )
                        .condition(JLootTable.predicate("minecraft:survives_explosion"))
                    )
            )
        }
    }
}