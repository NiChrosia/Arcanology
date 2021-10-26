package nichrosia.arcanology.registry.impl

import net.minecraft.SharedConstants
import net.minecraft.client.sound.OggAudioStream
import net.minecraft.sound.SoundEvent
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.registry.lang.LanguageGenerator
import nichrosia.arcanology.registry.lang.impl.BasicLanguageGenerator
import nichrosia.common.identity.ID
import nichrosia.registry.BasicRegistrar
import java.io.File
import kotlin.math.roundToLong

open class SoundRegistrar : BasicRegistrar<SoundRegistrar.DurativeSoundEvent>() {
    open val languageGenerator: LanguageGenerator = BasicLanguageGenerator()

    val machinery by memberOf(Arcanology.identify("machinery")) { DurativeSoundEvent(it) }

    override fun <E : DurativeSoundEvent> register(location: ID, value: E): E {
        return super.register(location, value).also {
            Registry.register(Registry.SOUND_EVENT, location, value)
            Arcanology.packManager.english.lang["subtitles.${location.split(".")}"] = languageGenerator.generateLang(location)
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