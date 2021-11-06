package nichrosia.arcanology.type.data.runtimeresource

import net.devtech.arrp.api.RRPCallback
import net.devtech.arrp.api.RuntimeResourcePack
import net.devtech.arrp.api.RuntimeResourcePack.id
import net.devtech.arrp.json.lang.JLang.lang
import net.devtech.arrp.json.tags.JTag
import net.fabricmc.loader.api.FabricLoader
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.type.data.runtimeresource.tag.ContentTag
import nichrosia.common.identity.ID

/** Runtime-resource-pack manager for the given [main], [common], [fabric], & [minecraft] resource packs. */
@Suppress("HasPlatformType")
open class RuntimeResourcePackManager(
    val modID: String,
    val main: RuntimeResourcePack,
    val common: RuntimeResourcePack,
    val fabric: RuntimeResourcePack,
    val minecraft: RuntimeResourcePack
) {
    val english = lang()
    val tags = mutableListOf<ContentTag<*>>()

    constructor(
        modID: String,
        mainVersion: Int = 1,
        commonVersion: Int = 1,
        fabricVersion: Int = 1,
        minecraftVersion: Int = 1
    ) : this(
        modID,
        RuntimeResourcePack.create("$modID:main", mainVersion),
        RuntimeResourcePack.create("c:$modID", commonVersion),
        RuntimeResourcePack.create("fabric:$modID", fabricVersion),
        RuntimeResourcePack.create("minecraft:$modID", minecraftVersion)
    )

    open fun load() {
        main.addLang(id("${Arcanology.modID}:en_us"), english)
        registerTags()

        val packs = arrayOf(main, common, fabric, minecraft)

        packs.apply {
            RRPCallback.BEFORE_VANILLA.register {
                it.addAll(this)
            }

            if (FabricLoader.getInstance().isDevelopmentEnvironment) {
                forEach(RuntimeResourcePack::dump)
            }
        }
    }

    open fun registerTags() {
        tags.forEach { tag ->
            val pack = when(tag.location.namespace) {
                modID -> main
                "c" -> common
                "fabric" -> fabric
                "minecraft" -> minecraft
                else -> throw IllegalStateException("Found unrecognized tag namespace '${tag.location.namespace}' within registries.")
            }

            pack.addTag(tag.location, tag.apply(JTag.tag()))
        }
    }

    fun folderID(folder: String, ID: ID): ID {
        return ID(ID.namespace, "$folder/${ID.path}")
    }

    fun blockModelID(ID: ID) = folderID("block", ID)
    fun itemModelID(ID: ID) = folderID("item", ID)
}