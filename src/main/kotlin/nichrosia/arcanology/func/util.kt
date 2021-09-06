package nichrosia.arcanology.func

import dev.technici4n.fasttransferlib.api.energy.*
import net.minecraft.item.ItemStack
import java.util.*
import kotlin.math.pow
import net.minecraft.client.MinecraftClient as RealMinecraftClient

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

infix fun Double.pow(n: Int) = pow(n)

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