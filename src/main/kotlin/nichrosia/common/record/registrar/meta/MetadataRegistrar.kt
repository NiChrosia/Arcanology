package nichrosia.common.record.registrar.meta

import nichrosia.common.record.registrar.Registrar
import nichrosia.common.record.registry.meta.MetadataRegistry
import nichrosia.common.record.registry.meta.key.MetadataKey

/** A registrar implementation that contains *metadata*, which is used in addition to the key for identifying values.
 * Additionally, an implementation of this class should provide functions to use the deserialized forms of the key. */
interface MetadataRegistrar<K, M, V> : Registrar<MetadataKey<K, M>, V> {
    override val registry: MetadataRegistry<K, M, V>
}