package nichrosia.arcanology.energy

enum class EnergyTier(val storage: Double,
                      val maxInput: Double,
                      /** Optional parameter, if not filled maxInput is used as the general energy IO speed. */
                      val maxOutput: Double = maxInput) {
    T1(1_000.0, 16.0),
    T2(10_000.0, 64.0),
    T3(50_000.0, 256.0),
    T4(200_000.0, 1024.0),
    T5(1_000_000.0, 4096.0)
}