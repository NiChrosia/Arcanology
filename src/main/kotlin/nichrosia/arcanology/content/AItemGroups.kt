package nichrosia.arcanology.content

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack

object AItemGroups : Content() {
    lateinit var magic: ItemGroup
    lateinit var tech: ItemGroup

    override fun load() {
        magic = FabricItemGroupBuilder.create(identify("magic"))
            .icon { ItemStack(AItems.arcaneHeart) }
            .build()

        tech = FabricItemGroupBuilder.create(identify("tech"))
            .icon { ItemStack(AItems.nickelZincBatteryItem) }
            .build()
    }
}