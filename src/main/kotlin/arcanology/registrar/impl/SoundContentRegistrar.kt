package arcanology.registrar.impl

import net.minecraft.sound.SoundEvent
import net.minecraft.util.registry.Registry
import arcanology.Arcanology
import arcanology.type.common.registar.lang.VanillaLangRegistrar
import arcanology.type.common.sound.DurativeSoundEvent

open class SoundContentRegistrar : VanillaLangRegistrar.Basic<SoundEvent>(Registry.SOUND_EVENT, "subtitles") {
    val machinery by memberOf(Arcanology.identify("machinery")) { DurativeSoundEvent(it) }
}