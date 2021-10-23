package nichrosia.arcanology.type.content.block

import net.devtech.arrp.json.models.JModel
import net.minecraft.util.Identifier
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.type.data.RuntimeResourcePackManager
import nichrosia.arcanology.util.variable

/** An interface for generating block models at runtime. */
interface ModeledBlock {
    fun generateModel(ID: Identifier, packManager: RuntimeResourcePackManager = Arcanology.packManager) {
        packManager.main.addModel(
            JModel.model("block/cube_all")
                .textures(JModel.textures().variable("all", "${Arcanology.modID}:block/${ID.path}")),
            packManager.blockModelID(ID.path)
        )
    }

    companion object {
        fun generateDefaultModel(ID: Identifier, packManager: RuntimeResourcePackManager = Arcanology.packManager) {
            packManager.main.addModel(
                JModel.model("block/cube_all")
                    .textures(JModel.textures().variable("all", "${Arcanology.modID}:block/${ID.path}")),
                packManager.blockModelID(ID.path)
            )
        }
    }
}