package nichrosia.arcanology.content

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.content.type.Content
import nichrosia.arcanology.data.DataGenerator

object AItemGroups : Content {
    lateinit var magic: ItemGroup
    lateinit var tech: ItemGroup

    override fun load() {
        magic = FabricItemGroupBuilder.create(identify("magic"))
            .icon { ItemStack(AMaterials.arcane.heart) }
            .build()

        tech = FabricItemGroupBuilder.create(identify("tech"))
            .icon { ItemStack(AMaterials.nickelZinc.battery) }
            .build()

        DataGenerator.lang.apply {
            arrayOf("magic", "tech").forEach {
                itemGroup(Arcanology.idOf(it), generateLang(it))
            }
        }
    }

    override fun generateLang(name: String): String {
        return "${Arcanology.modID.capitalize()}: ${name.capitalize()}"
    }
}