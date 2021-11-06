package nichrosia.arcanology.registrar.impl

import net.minecraft.SharedConstants
import net.minecraft.client.sound.OggAudioStream
import net.minecraft.sound.SoundEvent
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.registrar.lang.LanguageGenerator
import nichrosia.arcanology.registrar.lang.impl.BasicLanguageGenerator
import nichrosia.common.identity.ID
import nichrosia.common.record.registrar.content.ContentRegistrar
import java.io.File
import kotlin.math.roundToLong

open class SoundRegistrar : ContentRegistrar.Basic<SoundRegistrar.DurativeSoundEvent>() {
    open val languageGenerator: LanguageGenerator = BasicLanguageGenerator()

    val machinery by memberOf(Arcanology.identify("machinery")) { DurativeSoundEvent(it) }

    override fun <E : DurativeSoundEvent> publish(key: ID, value: E, registerIfAbsent: Boolean): E {
        return super.publish(key, value, registerIfAbsent).also {
            Registry.register(Registry.SOUND_EVENT, key, value)
            Arcanology.packManager.english.lang["subtitles.${key.split(".")}"] = languageGenerator.generateLang(key)
        }
    }

    open class DurativeSoundEvent(ID: ID) : SoundEvent(ID) {
        val length = run {
            val file = this::class.java.getResource("../../../../assets/${ID.namespace}/sounds/${ID.path}.ogg")?.file?.let { File(it) } ?: throw IllegalStateException("Identifier must have a matching sound file.")

            val audioStream = OggAudioStream(file.inputStream())
            val format = audioStream.format
            val recordedTimeInSec = file.length() / (format.frameSize * format.frameRate)

            (recordedTimeInSec * SharedConstants.TICKS_PER_SECOND).roundToLong()
        }
    }
}