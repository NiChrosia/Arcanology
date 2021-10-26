package nichrosia.arcanology.type.energy

open class EnergyTier(
    open val storage: Long,

    open val maxInputSpeed: Long,
    open val maxOutputSpeed: Long,

    open val consumptionSpeed: Long,
    open val progressionSpeed: Double,

    open val maxProgress: Int = 1000
) {
    companion object {
        val standard = EnergyTier(5_000L, 32L, 16L, 8L, 5.0)
        val industrial = EnergyTier(25_000L, 128L, 64L, 32L, 8 + 1.0 / 3.0)
        val vanguard = EnergyTier(100_000L, 512L, 256L, 128L, 25.0)
    }
}