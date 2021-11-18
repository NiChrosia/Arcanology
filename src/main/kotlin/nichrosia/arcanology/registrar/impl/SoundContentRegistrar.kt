package nichrosia.arcanology.registrar.impl

import net.minecraft.sound.SoundEvent
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.type.registar.lang.VanillaLangRegistrar
import nichrosia.arcanology.type.sound.DurativeSoundEvent

open class SoundContentRegistrar : VanillaLangRegistrar.Basic<SoundEvent>(Registry.SOUND_EVENT, "subtitles") {
    val machinery by memberOf(Arcanology.identify("machinery")) { DurativeSoundEvent(it) }
}