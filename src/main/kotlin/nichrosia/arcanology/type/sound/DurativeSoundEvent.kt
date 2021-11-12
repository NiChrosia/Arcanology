package nichrosia.arcanology.type.sound

import net.minecraft.SharedConstants
import net.minecraft.client.sound.OggAudioStream
import net.minecraft.sound.SoundEvent
import nichrosia.common.identity.ID
import java.io.File
import kotlin.math.roundToLong

// TODO make looping client-only, as this literally crashes the server
open class DurativeSoundEvent(ID: ID) : SoundEvent(ID) {
    val length = run {
        val file = this::class.java.getResource("../../../../assets/${ID.namespace}/sounds/${ID.path}.ogg")?.file?.let { File(it) } ?: throw IllegalStateException("Identifier must have a matching sound file.")

        val audioStream = OggAudioStream(file.inputStream())
        val format = audioStream.format
        val recordedTimeInSec = file.length() / (format.frameSize * format.frameRate)

        (recordedTimeInSec * SharedConstants.TICKS_PER_SECOND).roundToLong()
    }
}