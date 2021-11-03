package nichrosia.arcanology.registry.impl

import net.minecraft.SharedConstants
import net.minecraft.client.sound.OggAudioStream
import net.minecraft.sound.SoundEvent
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.registry.lang.LanguageGenerator
import nichrosia.arcanology.registry.lang.impl.BasicLanguageGenerator
import nichrosia.common.identity.ID
import nichrosia.common.registry.type.basic.BasicContentRegistrar
import java.io.File
import kotlin.math.roundToLong

open class SoundRegistrar : BasicContentRegistrar<SoundRegistrar.DurativeSoundEvent>() {
    open val languageGenerator: LanguageGenerator = BasicLanguageGenerator()

    val machinery by memberOf(Arcanology.identify("machinery")) { DurativeSoundEvent(it) }

    override fun <E : DurativeSoundEvent> register(input: ID, output: E): E {
        return super.register(input, output).also {
            Registry.register(Registry.SOUND_EVENT, input, output)
            Arcanology.packManager.english.lang["subtitles.${input.split(".")}"] = languageGenerator.generateLang(input)
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