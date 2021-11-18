package arcanology.type.common.registar.graphics

import nichrosia.common.math.geom.Size2
import nichrosia.common.math.geom.sized
import nichrosia.common.record.registrar.dynamic.DynamicRegistrar
import nichrosia.common.record.registrar.meta.MetadataRegistrar
import nichrosia.common.record.registry.meta.MetadataRegistry
import nichrosia.common.record.registry.meta.key.MetadataKey

interface TextureRegistrar<K, V> : DynamicRegistrar<MetadataKey<K, Size2>, V>, MetadataRegistrar<K, Size2, V> {
    override val registry: MetadataRegistry<K, Size2, V>
    
    val defaultWidth: Int
    val defaultHeight: Int

    fun convert(key: K, width: Int, height: Int): V?

    override fun convert(key: MetadataKey<K, Size2>): V? {
        return convert(key.key, key.metadata.width, key.metadata.height)
    }

    override fun find(key: MetadataKey<K, Size2>): V? {
        val registryResult = registry.find(key)

        return registryResult ?: run {
            val converted = convert(key)

            converted?.let {
                register(key, it)
            }
        }
    }

    fun find(key: K, width: Int, height: Int): V? {
        return find(MetadataKey(key, width sized height))
    }

    abstract class Basic<K, V> : TextureRegistrar<K, V> {
        override val defaultWidth: Int = 16
        override val defaultHeight: Int = 16

        override val registry: MetadataRegistry<K, Size2, V> = MetadataRegistry.Basic()
    }
}