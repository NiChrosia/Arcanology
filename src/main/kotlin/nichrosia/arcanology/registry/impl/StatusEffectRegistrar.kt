package nichrosia.arcanology.registry.impl

import net.minecraft.entity.effect.StatusEffect
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.type.content.status.effect.ElementalWrathStatusEffect
import nichrosia.arcanology.registry.RegistryRegistrar
import nichrosia.arcanology.registry.lang.LanguageGenerator
import nichrosia.arcanology.registry.lang.impl.BasicLanguageGenerator
import nichrosia.arcanology.registry.properties.RegistryProperty

open class StatusEffectRegistrar : RegistryRegistrar<StatusEffect>(Registry.STATUS_EFFECT, "status_effect") {
    override val languageGenerator: LanguageGenerator = BasicLanguageGenerator()

    val elementalWrath by RegistryProperty("elemental_wrath") { ElementalWrathStatusEffect() }
}