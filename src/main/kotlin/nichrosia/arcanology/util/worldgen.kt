package nichrosia.arcanology.util

import net.minecraft.util.math.Vec3i
import net.minecraft.world.gen.YOffset
import net.minecraft.world.gen.decorator.Decoratable
import net.minecraft.world.gen.feature.util.FeatureContext
import nichrosia.arcanology.type.world.feature.CustomOreFeatureConfig
import kotlin.math.roundToInt

/** Utility function to more easily set range. */
fun <R> Decoratable<R>.range(min: Int, max: Int): R {
    return uniformRange(YOffset.fixed(min), YOffset.fixed(max))
}

/** Utility function to set range using pairs. */
fun <R> Decoratable<R>.range(pair: Pair<Int, Int>): R {
    return range(pair.first, pair.second)
}

/** Utility function to generate a simple ore size generator based on distance. */
fun distanceToOreSize(distancePerSizeIncrease: Int, minimumSize: Int, maximumSize: Int): (FeatureContext<CustomOreFeatureConfig>) -> Int {
    return { context -> context.origin.getSquaredDistance(Vec3i.ZERO).roundToInt() / clamp(distancePerSizeIncrease, minimumSize, maximumSize) }
}