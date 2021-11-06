package nichrosia.arcanology.registrar.impl

import net.minecraft.entity.effect.StatusEffect
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.registrar.lang.impl.BasicLanguageGenerator
import nichrosia.common.identity.ID
import nichrosia.common.record.registrar.content.ContentRegistrar

open class StatusEffectRegistrar : ContentRegistrar.Basic<StatusEffect>() {
    open val languageGenerator = BasicLanguageGenerator()

    override fun <E : StatusEffect> publish(key: ID, value: E, registerIfAbsent: Boolean): E {
        return super.publish(key, value, registerIfAbsent).also {
            Registry.register(Registry.STATUS_EFFECT, key, it)
            Arcanology.packManager.english.lang["status_effect.${key.split(".")}"] = languageGenerator.generateLang(key)
        }
    }
}