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

open class SoundRegistrar : BasicRegistrar<SoundEvent>() {
    open val languageGenerator: LanguageGenerator = BasicLanguageGenerator()

    val machinery by memberOf(ID(Arcanology.modID, "machinery")) { SoundEvent(it.asIdentifier) }

    override fun <E : SoundEvent> register(location: ID, value: E): E {
        return super.register(location, value).also {
            Registry.register(Registry.SOUND_EVENT, location.asIdentifier, value)
            Arcanology.packManager.englishLang.lang["subtitles.${location.split(".")}"] = languageGenerator.generateLang(location)
        }
    }

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