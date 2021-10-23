package nichrosia.arcanology.type.content.item

import net.devtech.arrp.json.models.JModel
import net.minecraft.item.BlockItem
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.type.data.RuntimeResourcePackManager
import nichrosia.common.identity.ID

interface ModeledItem {
    fun generateModel(ID: ID, packManager: RuntimeResourcePackManager = Arcanology.packManager) {
        packManager.main.addModel(
            JModel.model("item/generated")
                .textures(JModel.textures().layer0("${Arcanology.modID}:item/${ID.path}")),
            Arcanology.packManager.itemModelID(ID.path)
        )
    }

    companion object {
        fun generateDefaultModel(ID: ID, packManager: RuntimeResourcePackManager = Arcanology.packManager) {
            packManager.main.addModel(
                JModel.model("item/generated")
                    .textures(JModel.textures().layer0("${Arcanology.modID}:item/${ID.path}")),
                Arcanology.packManager.itemModelID(ID.path)
            )
        }

        fun generateBlockItemModel(item: BlockItem, ID: ID, packManager: RuntimeResourcePackManager = Arcanology.packManager) {
            val blockID = Registry.BLOCK.getId(item.block)

            packManager.main.addModel(
                JModel.model("${Arcanology.modID}:block/${blockID.path}"),
                packManager.itemModelID(ID.path)
            )
        }

        fun generateHandheldModel(ID: ID, packManager: RuntimeResourcePackManager = Arcanology.packManager) {
            packManager.main.addModel(
                JModel.model("item/handheld")
                    .textures(JModel.textures().layer0("${Arcanology.modID}:item/${ID.path}")),
                packManager.itemModelID(ID.path)
            )
        }
    }
}