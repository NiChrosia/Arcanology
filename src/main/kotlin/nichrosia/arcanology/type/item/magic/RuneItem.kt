package nichrosia.arcanology.type.item.magic

import net.minecraft.client.item.TooltipContext
import net.minecraft.text.LiteralText
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import nichrosia.arcanology.util.clamp
import nichrosia.arcanology.type.instance.rune.Rune

interface RuneItem {
    fun appendTooltip(runes: Array<Rune>, text: MutableList<Text>, context: TooltipContext) {
        runes.forEach {
            text.add(LiteralText("")) // For the gap between runes and other stuff

            text.add(TranslatableText(it.type.translationKey).append(" ${it.level.toRomanNumeral()}"))
        }
    }

    /** Converts the given number to roman numerals. Only goes up to 20 to prevent absurdly long code. */
    fun Int.toRomanNumeral(): String {
        return when {
            this < 4 -> "I".repeat(this)
            this < 9 -> "I".repeat(if (this < 5) 1 else 0) + "V" + "I".repeat((this - 5).clamp())
            this < 14 -> "I".repeat(if (this < 10) 1 else 0) + "X" + "I".repeat((this - 10).clamp())
            this < 19 -> "X" + "I".repeat(if (this < 15) 1 else 0) + "V" + "I".repeat((this - 15).clamp())
            this < 21 -> "X" + "I".repeat(if (this < 20) 1 else 0) + "X"
            else -> "§k00§r"
        }
    }
}