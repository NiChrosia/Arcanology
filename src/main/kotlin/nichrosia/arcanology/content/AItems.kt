package nichrosia.arcanology.content

import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.util.Rarity
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.type.element.ElementalHeart
import nichrosia.arcanology.type.item.HeartItem

@Suppress("MemberVisibilityCanBePrivate")
object AItems : RegisterableContent<Item>(Registry.ITEM) {
    lateinit var velosiumOre: BlockItem
    lateinit var aegiriteOre: BlockItem
    lateinit var xenothiteOre: BlockItem

    lateinit var aegirite: Item
    lateinit var velosiumRawOre: Item

    lateinit var velosiumIngot: Item

    lateinit var altar: BlockItem

    lateinit var prismaticHeart: HeartItem
    lateinit var desolateHeart: HeartItem
    lateinit var moltenHeart: HeartItem
    lateinit var tidalHeart: HeartItem
    lateinit var terreneHeart: HeartItem
    lateinit var celestialHeart: HeartItem
    lateinit var arcaneHeart: HeartItem

    override fun load() {
        // Ores
        velosiumOre = register("velosium_ore_item", BlockItem(ABlocks.velosiumOre, settings.rarity(Rarity.COMMON)))
        aegiriteOre = register("aegirite_ore_item", BlockItem(ABlocks.aegiriteOre, settings.rarity(Rarity.COMMON)))
        xenothiteOre = register("xenothite_ore_item", BlockItem(ABlocks.xenothiteOre, settings.rarity(Rarity.UNCOMMON)))

        // Crystals / Raw ore
        aegirite = register("aegirite", Item(settings.rarity(Rarity.COMMON)))

        // Ingots

        // Elemental hearts
        prismaticHeart = register("prismatic_heart", HeartItem(settings.rarity(Rarity.RARE), ElementalHeart.Prismatic))
        desolateHeart = register("desolate_heart", HeartItem(settings.rarity(Rarity.RARE), ElementalHeart.Desolate))
        moltenHeart = register("molten_heart", HeartItem(settings.rarity(Rarity.RARE), ElementalHeart.Molten))
        tidalHeart = register("tidal_heart", HeartItem(settings.rarity(Rarity.RARE), ElementalHeart.Tidal))
        terreneHeart = register("terrene_heart", HeartItem(settings.rarity(Rarity.RARE), ElementalHeart.Terrene))
        celestialHeart = register("celestial_heart", HeartItem(settings.rarity(Rarity.RARE), ElementalHeart.Celestial))
        arcaneHeart = register("arcane_heart", HeartItem(settings.rarity(Rarity.EPIC), ElementalHeart.Arcane))

        // Misc
        altar = register("altar_item", BlockItem(ABlocks.altar, settings.rarity(Rarity.EPIC)))
    }

    val settings
      get() = FabricItemSettings()
}