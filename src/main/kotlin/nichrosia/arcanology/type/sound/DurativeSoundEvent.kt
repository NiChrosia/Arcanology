package nichrosia.arcanology.type.sound

import javazoom.spi.vorbis.sampled.file.VorbisAudioFileReader
import net.minecraft.SharedConstants
import net.minecraft.sound.SoundEvent
import nichrosia.common.identity.ID
import java.io.File
import kotlin.math.roundToLong

open class DurativeSoundEvent(ID: ID, unitsPerSecond: Int = defaultUnits) : SoundEvent(ID) {
    val length = run {
        val url = this::class.java.getResource("../../../../assets/${ID.namespace}/sounds/${ID.path}.ogg")
        val file = url?.file?.let(::File) ?: throw IllegalArgumentException("Identifier must have a matching sound file.")

        val stream = reader.getAudioInputStream(file.inputStream())
        val format = stream.format
        val recordedTimeInSec = file.length() / (format.frameSize * format.frameRate)

        (recordedTimeInSec * unitsPerSecond).roundToLong()
    }

    companion object {
        const val defaultUnits = SharedConstants.TICKS_PER_SECOND
        val reader = VorbisAudioFileReader()
    }
}