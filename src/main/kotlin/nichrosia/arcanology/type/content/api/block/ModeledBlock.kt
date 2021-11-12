package nichrosia.arcanology.type.content.api.block

import net.devtech.arrp.json.models.JModel
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.type.data.runtimeresource.RuntimeResourcePackManager
import nichrosia.arcanology.util.addProperty
import nichrosia.common.identity.ID

/** An interface for generating block models at runtime. */
interface ModeledBlock {
    fun generateModel(ID: ID, packManager: RuntimeResourcePackManager = Arcanology.packManager): Map<ID, JModel> {
        return generateDefaultModel(ID, packManager)
    }

    fun textureID(name: String, modID: String = Arcanology.modID) = "$modID:block/$name"

    companion object {
        fun generateDefaultModel(ID: ID, packManager: RuntimeResourcePackManager = Arcanology.packManager): Map<ID, JModel> {
            return mapOf(
                packManager.blockModelID(ID) to JModel.model("block/cube_all")
                    .textures(JModel.textures().addProperty("all", "${Arcanology.modID}:block/${ID.path}"))
            )
        }
    }
}