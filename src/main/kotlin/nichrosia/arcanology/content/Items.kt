package nichrosia.arcanology.content

import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.util.Identifier
import net.minecraft.util.Rarity
import net.minecraft.util.registry.Registry

open class Items : Loadable {
    override fun load() {
        velosiumOre = Registry.register(
            Registry.ITEM,
            Identifier("arcanology", "velosium_ore_item"),
            BlockItem(
                Blocks.velosiumOre,
                FabricItemSettings().rarity(Rarity.UNCOMMON)
            )
        )

        darknessCrystalOre = Registry.register(
            Registry.ITEM,
            Identifier("arcanology", "darkness_crystal_ore_item"),
            BlockItem(
                Blocks.darknessCrystalOre,
                FabricItemSettings().rarity(Rarity.UNCOMMON)
            )
        )

        darknessCrystal = Registry.register(
            Registry.ITEM,
            Identifier("arcanology", "darkness_crystal"),
            Item(
                FabricItemSettings().rarity(Rarity.UNCOMMON)
            )
        )
    }

    companion object {
        lateinit var velosiumOre: BlockItem

        lateinit var velosiumIngot: Item

        lateinit var darknessCrystalOre: BlockItem
        lateinit var darknessCrystal: Item
    }
}