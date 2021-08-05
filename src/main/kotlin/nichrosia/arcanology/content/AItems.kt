package nichrosia.arcanology.content

import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.util.Identifier
import net.minecraft.util.Rarity
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.content.type.RegisterableLangContent
import nichrosia.arcanology.data.DataGenerator
import nichrosia.arcanology.data.DataGenerator.itemTagID
import nichrosia.arcanology.integration.patchouli.GuideBookItem

@Suppress("MemberVisibilityCanBePrivate")
object AItems : RegisterableLangContent<Item>(Registry.ITEM) {
    lateinit var altar: BlockItem

    lateinit var wireCutter: Item

    lateinit var arcaneAlmanac: GuideBookItem
    lateinit var componentCompendium: GuideBookItem

    val magicSettings: FabricItemSettings
        get() = FabricItemSettings().group(AItemGroups.magic)

    val techSettings: FabricItemSettings
        get() = FabricItemSettings().group(AItemGroups.tech)

    override fun load() {
        altar = register("altar", BlockItem(ABlocks.altar, magicSettings.rarity(Rarity.EPIC)))

        wireCutter = register("wire_cutter", Item(techSettings))

        DataGenerator.addTags(itemTagID("wire_cutters"), Registry.ITEM.getId(wireCutter))

        arcaneAlmanac = register("arcane_almanac", GuideBookItem(magicSettings, "arcane_almanac"))
        componentCompendium = register("component_compendium", GuideBookItem(techSettings, "component_compendium"))
    }

    override fun <T : Item> register(identifier: Identifier, content: T): T {
        val registeredContent = Registry.register(type, identifier, content)

        if (registeredContent is BlockItem) {
            DataGenerator.blockItemModel(registeredContent)
        } else {
            DataGenerator.normalItemModel(registeredContent)
        }

        val id = Registry.ITEM.getId(registeredContent)
        DataGenerator.lang.item(id, generateLang(id.path))

        all.add(registeredContent)

        return registeredContent
    }
}