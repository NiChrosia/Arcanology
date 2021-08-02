package nichrosia.arcanology.content

import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.util.Rarity
import net.minecraft.util.registry.Registry

@Suppress("MemberVisibilityCanBePrivate")
object AItems : ArcanologyContent() {
    lateinit var velosiumOre: BlockItem
    lateinit var aegiriteOre: BlockItem
    lateinit var xenothiteOre: BlockItem

    lateinit var aegirite: Item
    lateinit var velosiumRawOre: Item

    lateinit var velosiumIngot: Item

    override fun load() {
        // Ores
        velosiumOre = Registry.register(
            Registry.ITEM,
            getIdentifier("velosium_ore_item"),
            BlockItem(ABlocks.velosiumOre, FabricItemSettings().rarity(Rarity.COMMON))
        )

        aegiriteOre = Registry.register(
            Registry.ITEM,
            getIdentifier("aegirite_ore_item"),
            BlockItem(ABlocks.aegiriteOre, FabricItemSettings().rarity(Rarity.COMMON))
        )

        xenothiteOre = Registry.register(
            Registry.ITEM,
            getIdentifier("xenothite_ore_item"),
            BlockItem(ABlocks.xenothiteOre, FabricItemSettings().rarity(Rarity.UNCOMMON))
        )

        // Crystals / Raw ore
        aegirite = Registry.register(
            Registry.ITEM,
            getIdentifier("aegirite"),
            Item(FabricItemSettings().rarity(Rarity.COMMON))
        )

        // Ingots
    }

    override fun getAll(): Array<Any> {
        return arrayOf(
            velosiumOre,
            aegiriteOre,
            xenothiteOre,

            aegirite,
            velosiumRawOre,

            velosiumIngot,
        )
    }
}