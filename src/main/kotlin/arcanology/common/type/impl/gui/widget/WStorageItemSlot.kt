package arcanology.common.type.impl.gui.widget

import arcanology.common.type.impl.world.storage.item.ItemStackStorage
import arcanology.common.type.impl.world.storage.item.StorageInventory
import io.github.cottonmc.cotton.gui.widget.WItemSlot
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant
import net.fabricmc.fabric.api.transfer.v1.storage.base.CombinedStorage

open class WStorageItemSlot(
    storage: CombinedStorage<ItemVariant, ItemStackStorage>,
    startIndex: Int,
    slotsWide: Int = 1,
    slotsHigh: Int = 1,
    big: Boolean = false
) : WItemSlot(StorageInventory(storage), startIndex, slotsWide, slotsHigh, big)