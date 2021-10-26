package nichrosia.arcanology.type.data

import net.devtech.arrp.api.RRPCallback
import net.devtech.arrp.api.RuntimeResourcePack
import net.devtech.arrp.api.RuntimeResourcePack.id
import net.devtech.arrp.json.lang.JLang.lang
import net.fabricmc.loader.api.FabricLoader
import nichrosia.arcanology.Arcanology
import nichrosia.common.identity.ID

/** Runtime-resource-pack manager for the given [main] & [common] resource packs. */
@Suppress("HasPlatformType")
open class RuntimeResourcePackManager(val main: RuntimeResourcePack, val common: RuntimeResourcePack) {
    val english = lang()

    constructor(modID: String, mainVersion: Int = 1, commonVersion: Int = 1) : this(RuntimeResourcePack.create("$modID:main", mainVersion), RuntimeResourcePack.create("c:$modID", commonVersion))

    open fun load() {
        main.addLang(id("${Arcanology.modID}:en_us"), english)

        val packs = arrayOf(main, common)

        RRPCallback.BEFORE_VANILLA.register { it.addAll(packs) }

        if (FabricLoader.getInstance().isDevelopmentEnvironment) {
            packs.forEach(RuntimeResourcePack::dump)
        }
    }

    fun folderID(folder: String, ID: ID) = ID(ID.namespace, "$folder/${ID.path}")
    fun blockID(ID: ID) = folderID("blocks", ID)
    fun blockModelID(ID: ID) = folderID("block", ID)
    fun itemModelID(ID: ID) = folderID("item", ID)
}