package nichrosia.arcanology.type.energy

/** Currently not balanced at all, as they aren't used */
open class EnergyTier(
    open val storage: Long,
    open val maxInput: Long,
    open val maxOutput: Long,
    open val baseUsagePerTick: Long,
    /** Base progress out of 1000 per tick. */
    open val baseProgressPerTick: Int
) {
    companion object {
        val standard = EnergyTier(5_000L, 32L, 16L, 8L, 5)
        val industrial = EnergyTier(25_000L, 128L, 64L, 32L, 8 + 1/3)
        val vanguard = EnergyTier(100_000L, 512L, 256L, 128L, 25)
    }
}