package nichrosia.arcanology.type.energy

enum class EnergyTier(
    val storage: Double,
    val maxInput: Double,
    /** Optional parameter, if not filled maxInput is used as the general energy IO speed. */
    val maxOutput: Double = maxInput
) {
    Standard(5_000.0, 32.0, 16.0), // currently not balanced at all, as they aren't used
    Industrial(25_000.0, 128.0, 64.0),
    Vanguard(100_000.0, 512.0, 256.0);
}