package nichrosia.arcanology.itype.rune

import net.minecraft.item.ItemStack
import nichrosia.arcanology.content.ARuneTypes
import nichrosia.arcanology.type.rune.RuneType

@Suppress("MemberVisibilityCanBePrivate")
open class Rune(val type: RuneType, val level: Int) {
    companion object {
        var ItemStack.runes: Array<Rune>
            get() {
                var empty = false

                val runeTypes = orCreateNbt.getString("arcanologyRunes").ifEmpty { null }?.split("|") ?: run {
                    empty = true; listOf()
                }

                val levels = orCreateNbt.getString("arcanologyRuneLevels").ifEmpty { null }?.split("|")?.map { it.toInt() } ?: run {
                    empty = true; listOf()
                }

                if (empty) {
                    val runeArray = arrayOf(Rune(ARuneTypes.manabound, 1))
                    runes = runeArray

                    return runeArray
                }

                return parseRunes(runeTypes, levels)
            }
            set(value) {
                orCreateNbt.putString("arcanologyRunes", value.joinToString("|") { it.type.name })
                orCreateNbt.putString("arcanologyRuneLevels", value.joinToString("|") { it.level.toString() })
            }

        var ItemStack.mana: Int
            get() {
                return orCreateNbt.getInt("arcanologyMana")
            }
            set(value) {
                orCreateNbt.putInt("arcanologyMana", value)
            }

        fun parseRunes(runes: List<String>, levels: List<Int>): Array<Rune> {
            val output = mutableListOf<Rune>()

            for (i in runes.indices) {
                val rune = Rune(RuneType.types.first { it.name == runes[i] }, levels[i])

                output.add(rune)
            }

            return output.toTypedArray()
        }
    }
}