package arcanology.type.common.registar.lang

import net.minecraft.util.registry.Registry
import arcanology.Arcanology
import arcanology.registrar.lang.LangGenerator
import arcanology.type.common.registar.minecraft.VanillaRegistrar
import nichrosia.common.identity.ID
import nichrosia.common.record.member.MemberList
import nichrosia.common.record.registry.content.ContentRegistry

interface VanillaLangRegistrar<T> : VanillaRegistrar<T> {
    val langGenerator: LangGenerator
    val translationKey: String

    override fun <E : T> publish(key: ID, value: E): E {
        return super.publish(key, value).also {
            Arcanology.packManager.english.lang["$translationKey.${key.split(".")}"] = langGenerator.generate(key)
        }
    }

    open class Basic<T>(override val minecraftRegistry: Registry<T>, override val translationKey: String) : VanillaLangRegistrar<T> {
        override val registry: ContentRegistry<T> = ContentRegistry.Basic()
        override val members: MemberList<T> = MemberList.Basic()
        override val langGenerator: LangGenerator = LangGenerator.Basic()
    }
}