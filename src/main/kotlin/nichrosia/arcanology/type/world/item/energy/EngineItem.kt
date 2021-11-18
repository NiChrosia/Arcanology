package nichrosia.arcanology.type.world.item.energy

import kotlin.math.pow
import kotlin.math.roundToInt

/** An advanced item capable of increasing in efficiency at higher temperatures, but energy consumption will heavily
 * increase when temperatures past optimal are reached. */
interface EngineItem {
    /** The maximum temperature of the item. An unused item's temperature is assumed to be 0. */
    val maxTemp: Double

    /** The point when energy usage heavily spikes. */
    val pastOptimalPoint: Double

    val energyConsumption: Int
      get() = getEnergyConsumption(temperature)

    val boostAmount: Double
      get() = getBoostAmount(temperature)

    /** How much the temperature increases per tick. */
    val temperatureIncrement: Double

    var temperature: Double
    var using: Boolean
    var active: Boolean

    var lastTick: Long

    fun usageTick() {
        using = true

        if (!active) start()
    }

    fun onStopUsing() {
        using = false
    }

    fun tick() {
        if (using && active && temperature + temperatureIncrement <= maxTemp) {
            if (System.currentTimeMillis() - 1000L >= lastTick) {
                shutdown()
            }

            temperature += temperatureIncrement
        } else if (!active && temperature - temperatureIncrement > 0.1) {
            temperature -= temperatureIncrement
        }
    }

    fun start() {
        active = true
    }

    fun shutdown() {
        active = false
    }

    fun getBoostAmount(temperature: Double): Double {
        return temperature.pow(2)
    }

    fun getEnergyConsumption(temperature: Double): Int {
        return temperature.pow(if (temperature > pastOptimalPoint) 4 else 1).roundToInt()
    }
}