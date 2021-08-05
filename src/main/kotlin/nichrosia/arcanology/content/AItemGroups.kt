package nichrosia.arcanology.content

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.Arcanology

object AItemGroups : Content() {
    lateinit var magic: ItemGroup
    lateinit var tech: ItemGroup

    override fun load() {
        magic = FabricItemGroupBuilder.create(identify("magic"))
            .icon { ItemStack(AItems.arcane.heart) }
            .build()

        tech = FabricItemGroupBuilder.create(identify("tech"))
            .icon { ItemStack(AItems.nickelZinc.batteryItem) }
            .build()
    }
}