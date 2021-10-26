package nichrosia.arcanology.registry.impl

import net.minecraft.entity.effect.StatusEffect
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.registry.lang.LanguageGenerator
import nichrosia.arcanology.registry.lang.impl.BasicLanguageGenerator
import nichrosia.arcanology.type.content.status.effect.ElementalWrathStatusEffect
import nichrosia.common.identity.ID
import nichrosia.registry.BasicRegistrar

open class StatusEffectRegistrar : BasicRegistrar<StatusEffect>() {
    open val languageGenerator: LanguageGenerator = BasicLanguageGenerator()

    val elementalWrath by memberOf(Arcanology.identify("elemental_wrath")) { ElementalWrathStatusEffect() }

    override fun <E : StatusEffect> register(location: ID, value: E): E {
        return super.register(location, value).also {
            Registry.register(Registry.STATUS_EFFECT, location, it)
            Arcanology.packManager.english.lang["status_effect.${location.split(".")}"] = languageGenerator.generateLang(location)
        }
    }
}