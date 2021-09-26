package nichrosia.arcanology.util

import net.minecraft.util.math.Vec3i
import net.minecraft.world.gen.YOffset
import net.minecraft.world.gen.decorator.ConfiguredDecorator
import net.minecraft.world.gen.decorator.Decoratable
import net.minecraft.world.gen.decorator.DecoratorConfig
import net.minecraft.world.gen.feature.ConfiguredFeature
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.FeatureConfig
import net.minecraft.world.gen.feature.OreFeatureConfig
import net.minecraft.world.gen.feature.util.FeatureContext
import nichrosia.arcanology.type.world.feature.CustomOreFeatureConfig
import kotlin.math.roundToInt

fun <R> Decoratable<R>.range(min: Int, max: Int): R {
    return uniformRange(YOffset.fixed(min), YOffset.fixed(max))
}

fun <R> Decoratable<R>.range(pair: Pair<Int, Int>): R {
    return range(pair.first, pair.second)
}

fun distanceToOreSize(distancePerSizeIncrease: Int, minimumSize: Int, maximumSize: Int): (FeatureContext<CustomOreFeatureConfig>) -> Int {
    return { context -> context.origin.getSquaredDistance(Vec3i.ZERO).roundToInt() / distancePerSizeIncrease.clamp(minimumSize, maximumSize) }
}