package nichrosia.arcanology.func

import dev.technici4n.fasttransferlib.api.energy.*
import io.github.cottonmc.cotton.gui.widget.WPlainPanel
import io.github.cottonmc.cotton.gui.widget.WWidget
import net.minecraft.item.ItemStack
import net.minecraft.util.math.Vec2f
import nichrosia.arcanology.math.Vec2
import java.util.*
import kotlin.math.PI
import kotlin.math.pow
import net.minecraft.client.MinecraftClient as RealMinecraftClient

fun WPlainPanel.add(widget: WWidget, vec: Vec2, width: Int, height: Int) {
    add(widget, vec.x.toInt(), vec.y.toInt(), width, height)
}

infix fun Double.pow(n: Int) = pow(n)

infix fun <A, B, C> Pair<A, B>.toTriple(other: C): Triple<A, B, C> = Triple(first, second, other)
infix fun <A, B, C, D> Triple<A, B, C>.toQuadruple(other: D): Quadruple<A, B, C, D> = Quadruple(first, second, third, other)

internal val Double.formatted: String
    get() {
        val formatting = when {
            this >= 1_000_000_000_000 -> this / 1_000_000_000_000
            this >= 1_000_000_000 -> this / 1_000_000_000
            this >= 1_000_000 -> this / 1_000_000
            this >= 1_000 -> this / 1_000
            else -> this
        }

        return "%.1f".format(formatting) + asNumeral
    }

internal val Double.asNumeral: String
    get() = thousandsNumeralMap.getClosest(this)

internal val ItemStack.energyIO: EnergyIo?
    get() = EnergyApi.ITEM.find(this, null)

internal val <T> Optional<T>.asNullable: T?
    get() = if (isPresent) get() else null

internal val minecraftClient: RealMinecraftClient
    get() = RealMinecraftClient.getInstance()

private val thousandsNumeralMap = mapOf(
    1000.0 pow 1 to "k",
    1000.0 pow 2 to "M",
    1000.0 pow 3 to "B",
    1000.0 pow 4 to "T"
)

data class Quadruple<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)