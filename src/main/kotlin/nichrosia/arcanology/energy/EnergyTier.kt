package nichrosia.arcanology.energy

enum class EnergyTier(
    val storage: Double,
    val maxInput: Double,
    /** Optional parameter, if not filled maxInput is used as the general energy IO speed. */
    val maxOutput: Double = maxInput
) {
    T1(5_000.0, 32.0, 16.0),
    T2(25_000.0, 128.0, 64.0),
    T3(100_000.0, 512.0, 256.0),
    T4(750_000.0, 4_096.0, 2_048.0),
    T5(10_000_000.0, 32_768.0);
}