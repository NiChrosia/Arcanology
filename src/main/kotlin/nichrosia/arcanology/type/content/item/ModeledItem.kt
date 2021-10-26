package nichrosia.arcanology.type.content.item

import net.devtech.arrp.json.models.JModel
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.type.data.RuntimeResourcePackManager
import nichrosia.common.identity.ID

interface ModeledItem {
    fun generateModel(ID: ID, packManager: RuntimeResourcePackManager = Arcanology.packManager): Map<ID, JModel> {
        return generateDefaultModel(ID, packManager)
    }

    companion object {
        fun generateDefaultModel(ID: ID, packManager: RuntimeResourcePackManager = Arcanology.packManager): Map<ID, JModel> {
            return mapOf(packManager.itemModelID(ID) to JModel.model("item/generated")
                .textures(JModel.textures().layer0(packManager.itemModelID(ID).split())))
        }

        fun generateBlockItemModel(ID: ID, packManager: RuntimeResourcePackManager = Arcanology.packManager): Map<ID, JModel> {
            return mapOf(packManager.itemModelID(ID) to JModel.model(packManager.blockModelID(ID).split()))
        }
    }
}