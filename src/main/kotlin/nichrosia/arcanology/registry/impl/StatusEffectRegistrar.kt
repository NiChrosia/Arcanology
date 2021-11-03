package nichrosia.arcanology.registry.impl

import net.minecraft.entity.effect.StatusEffect
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.registry.lang.impl.BasicLanguageGenerator
import nichrosia.common.identity.ID
import nichrosia.common.registry.type.basic.BasicContentRegistrar

open class StatusEffectRegistrar : BasicContentRegistrar<StatusEffect>() {
    open val languageGenerator = BasicLanguageGenerator()

    override fun <E : StatusEffect> register(input: ID, output: E): E {
        return super.register(input, output).also {
            Registry.register(Registry.STATUS_EFFECT, input, it)
            Arcanology.packManager.english.lang["status_effect.${input.split(".")}"] = languageGenerator.generateLang(input)
        }
    }
}