package nichrosia.arcanology.math.intprovider

import net.minecraft.util.math.intprovider.IntProvider
import net.minecraft.util.math.intprovider.IntProviderType
import java.util.Random

open class CustomIntProvider(val provider: (Random) -> Int) : IntProvider() {
    override fun get(random: Random): Int {
        return provider(random)
    }

    override fun getMin(): Int {
        return Int.MIN_VALUE
    }

    override fun getMax(): Int {
        return Int.MAX_VALUE
    }

    override fun getType(): IntProviderType<*> {
        return IntProviderType.UNIFORM
    }
}