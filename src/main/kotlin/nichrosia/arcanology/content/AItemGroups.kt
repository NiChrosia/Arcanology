package nichrosia.arcanology.content

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack

object AItemGroups : Content() {
    lateinit var main: ItemGroup

    override fun load() {
        main = FabricItemGroupBuilder.create(identify("main"))
            .icon { ItemStack(AItems.arcaneHeart) }
            .appendItems {
                AItems.all.forEach { item ->
                    it.add(ItemStack(item))
                }
            }.build()
    }
}