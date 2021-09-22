package nichrosia.arcanology.data

import net.devtech.arrp.api.RRPCallback
import net.devtech.arrp.api.RuntimeResourcePack.id
import net.devtech.arrp.json.lang.JLang
import net.devtech.arrp.json.lang.JLang.lang
import net.devtech.arrp.json.tags.JTag.tag
import net.minecraft.item.Item
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.Arcanology.commonResourcePack
import nichrosia.arcanology.Arcanology.modID
import nichrosia.arcanology.Arcanology.resourcePack
import nichrosia.arcanology.type.content.LoadableContent

@Suppress("HasPlatformType")
object DataGenerator : LoadableContent {
    private val tags = mutableMapOf<Identifier, MutableList<Identifier>>()
    lateinit var englishLang: JLang

    override fun load() {
        loadTags()
        addLang()

        RRPCallback.BEFORE_VANILLA.register {
            it.add(commonResourcePack)
            it.add(resourcePack)
        }

        resourcePack.dump()
        commonResourcePack.dump()
    }

    private fun loadTags() {
        tags.forEach { (name, items) ->
            commonResourcePack.apply { addTag(name, tag().apply { items.forEach { add(it) } }) }
        }
    }

    fun createLang() {
        englishLang = lang()
    }

    private fun addLang() {
        resourcePack.addLang(id("${modID}:en_us"), englishLang)
    }

    private fun folderID(folder: String, name: String, modID: String = Arcanology.modID) = Identifier(modID, "$folder/$name")

    fun addTags(id: Identifier, vararg items: Item) {
        tags.putIfAbsent(id, mutableListOf())

        tags[id]?.addAll(items.map { Registry.ITEM.getId(it) })
    }

    fun blockID(name: String) = folderID("blocks", name)
    fun blockModelID(name: String) = folderID("block", name)
    fun itemModelID(name: String) = folderID("item", name)
    fun itemTagID(name: String) = folderID("item", name, "c")
}