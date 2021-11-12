package nichrosia.arcanology.registrar.impl

import net.minecraft.util.registry.Registry
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.registrar.lang.LanguageGenerator
import nichrosia.arcanology.registrar.lang.impl.BasicLanguageGenerator
import nichrosia.arcanology.type.sound.DurativeSoundEvent
import nichrosia.common.identity.ID
import nichrosia.common.record.registrar.content.ContentRegistrar

open class SoundRegistrar : ContentRegistrar.Basic<DurativeSoundEvent>() {
    open val languageGenerator: LanguageGenerator = BasicLanguageGenerator()

    val machinery by memberOf(Arcanology.identify("machinery")) { DurativeSoundEvent(it) }

    override fun <E : DurativeSoundEvent> publish(key: ID, value: E): E {
        return super.publish(key, value).also {
            Registry.register(Registry.SOUND_EVENT, key, value)
            Arcanology.packManager.english.lang["subtitles.${key.split(".")}"] = languageGenerator.generateLang(key)
        }
    }
}