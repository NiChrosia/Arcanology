package nichrosia.arcanology.data

import net.devtech.arrp.api.RRPCallback
import net.devtech.arrp.api.RuntimeResourcePack
import net.devtech.arrp.api.RuntimeResourcePack.id
import net.devtech.arrp.json.lang.JLang.lang
import net.devtech.arrp.json.tags.JTag.tag
import net.minecraft.item.Item
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.type.content.LoadableContent
import nichrosia.arcanology.util.*

/** Runtime-resource-pack manager for the given [main] & [common] resource packs. */
@Suppress("HasPlatformType")
open class RuntimeResourcePackManager(val main: RuntimeResourcePack, val common: RuntimeResourcePack) : LoadableContent {
    val tags = TagMap()
    val englishLang = lang()

    constructor(mainID: String, commonID: String, mainVersion: Int = 1, commonVersion: Int = 1) : this(
        RuntimeResourcePack.create(mainID, mainVersion),
        RuntimeResourcePack.create(commonID, commonVersion)
    )

    override fun load() {
        tags.load()
        main.addLang(id("${Arcanology.modID}:en_us"), englishLang)

        RRPCallback.BEFORE_VANILLA.register { it.addAll(main, common) }

        arrayOf(main, common).forEach { it.dump() }
    }

    fun folderID(folder: String, name: String, modID: String = Arcanology.modID) = Identifier(modID, "$folder/$name")
    fun blockID(name: String) = folderID("blocks", name)
    fun blockModelID(name: String) = folderID("block", name)
    fun itemModelID(name: String) = folderID("item", name)
    fun itemTagID(name: String) = folderID("item", name, "c")

    open inner class TagMap : LinkedHashMap<Identifier, MutableList<Identifier>>() {
        open fun add(id: Identifier, vararg items: Item) {
            tags.putIfAbsent(id, mutableListOf())

            tags[id]?.addAll(items.map { Registry.ITEM.getId(it) })
        }

        open fun load() {
            forEach { (name, items) ->
                common.apply { addTag(name, tag().apply { items.forEach { add(it) } }) }
            }
        }
    }
}