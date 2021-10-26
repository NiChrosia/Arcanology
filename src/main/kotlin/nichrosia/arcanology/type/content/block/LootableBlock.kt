package nichrosia.arcanology.type.content.block

import net.devtech.arrp.json.loot.JLootTable
import nichrosia.common.identity.ID

// TODO: make this implementation somehow work with the registrar system in an elegant way
interface LootableBlock {
    fun generateLootTable(ID: ID, itemID: ID): Map<ID, JLootTable> {
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

    companion object {
        fun generateDefaultLootTable(ID: ID, itemID: ID): Map<ID, JLootTable> {
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