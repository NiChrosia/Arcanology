package nichrosia.arcanology.type.energy

open class EnergyTier(
    open val name: String,
    open val storage: Long,

    open val maxInputSpeed: Long,
    open val maxOutputSpeed: Long,

    open val consumptionSpeed: Long,
    open val progressionSpeed: Long,

    open val maxProgress: Int = 10000
) {
    companion object {
        val primitive = EnergyTier("primitive", 1_000L, 8L, 4L, 2L, 25)
        val standard = EnergyTier("standard", 5_000L, 32L, 16L, 8L, 50)
        val industrial = EnergyTier("industrial", 25_000L, 128L, 64L, 32L, 100)
        val vanguard = EnergyTier("vanguard", 100_000L, 512L, 256L, 128L, 250)
    }
}