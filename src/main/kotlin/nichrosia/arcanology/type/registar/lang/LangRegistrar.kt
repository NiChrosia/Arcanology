package nichrosia.arcanology.type.registar.lang

import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.registrar.lang.LangGenerator
import nichrosia.common.identity.ID
import nichrosia.common.record.member.MemberList
import nichrosia.common.record.registrar.content.ContentRegistrar
import nichrosia.common.record.registry.content.ContentRegistry

interface LangRegistrar<T> : ContentRegistrar<T> {
    val langGenerator: LangGenerator
    val translationKey: String

    override fun <E : T> publish(key: ID, value: E): E {
        return super.publish(key, value).also {
            Arcanology.packManager.english.lang[translationKeyOf(key)] = langGenerator.generate(key)
        }
    }

    fun translationKeyOf(key: ID): String {
        return "$translationKey.${key.split(".")}"
    }

    // default argument for subclasses that override translationKeyOf
    open class Basic<T>(override val translationKey: String = "") : LangRegistrar<T> {
        override val registry: ContentRegistry<T> = ContentRegistry.Basic()
        override val members: MemberList<T> = MemberList.Basic()
        override val langGenerator: LangGenerator = LangGenerator.Basic()
    }
}