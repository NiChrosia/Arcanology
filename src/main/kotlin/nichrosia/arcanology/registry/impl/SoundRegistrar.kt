package nichrosia.arcanology.registry.impl

import net.minecraft.SharedConstants
import net.minecraft.client.sound.OggAudioStream
import net.minecraft.sound.SoundEvent
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.registry.RegistryRegistrar
import nichrosia.arcanology.registry.lang.LanguageGenerator
import nichrosia.arcanology.registry.lang.impl.BasicLanguageGenerator
import nichrosia.arcanology.registry.properties.RegistryProperty
import java.io.File
import kotlin.math.roundToLong

open class SoundRegistrar : RegistryRegistrar<SoundEvent>(Registry.SOUND_EVENT, "subtitles") {
    override val languageGenerator: LanguageGenerator = BasicLanguageGenerator()

    val machinery by RegistryProperty("machinery") { SoundEvent(Arcanology.idOf(it)) }

    companion object {
        val SoundEvent.length: Long
            get() {
                val file = this@Companion::class.java.getResource("../../../../assets/${id.namespace}/sounds/${id.path}.ogg")?.file?.let { File(it) } ?: throw IllegalStateException("Identifier must have a matching sound file.")

                val audioStream = OggAudioStream(file.inputStream())
                val format = audioStream.format
                val recordedTimeInSec = file.length() / (format.frameSize * format.frameRate)

                return (recordedTimeInSec * SharedConstants.TICKS_PER_SECOND).roundToLong()
            }
    }
}