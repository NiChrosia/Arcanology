package nichrosia.arcanology.type.world.feature

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.ChunkSectionPos
import net.minecraft.util.math.MathHelper
import net.minecraft.util.registry.Registry
import net.minecraft.world.ChunkSectionCache
import net.minecraft.world.Heightmap
import net.minecraft.world.StructureWorldAccess
import net.minecraft.world.chunk.WorldChunk
import net.minecraft.world.gen.feature.*
import net.minecraft.world.gen.feature.util.FeatureContext
import java.util.*
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.sin

open class CustomOreFeature(codec: Codec<CustomOreFeatureConfig>) : Feature<CustomOreFeatureConfig>(codec) {
    override fun generate(context: FeatureContext<CustomOreFeatureConfig>): Boolean {
        val random = context.random
        val blockPos = context.origin
        val structureWorldAccess = context.world
        val config = context.config
        val veinSize = config.sizeFactory(context).toFloat()

        val f = random.nextFloat() * PI
        val g = veinSize / 8.0
        val i = MathHelper.ceil((veinSize / 16.0f * 2.0f + 1.0f) / 2.0f)
        val startX = blockPos.x + sin(f) * g
        val endX = blockPos.x - sin(f) * g
        val startZ = blockPos.z + cos(f) * g
        val endZ = blockPos.z - cos(f) * g
        val startY = (blockPos.y + random.nextInt(3) - 2.0)
        val endY = (blockPos.y + random.nextInt(3) - 2.0)
        val veinX = blockPos.x - MathHelper.ceil(g) - i
        val veinY = blockPos.y - 2 - i
        val veinZ = blockPos.z - MathHelper.ceil(g) - i
        val horizontalSize = 2 * (MathHelper.ceil(g) + i)
        val verticalSize = 2 * (2 + i)
        for (chunkX in (veinX)..(veinX + horizontalSize)) {
            for (chunkZ in (veinZ)..(veinZ + horizontalSize)) {
                if (veinY <= structureWorldAccess.getTopY(Heightmap.Type.WORLD_SURFACE_WG, chunkX, chunkZ)) {
                    return generateVeinPart(structureWorldAccess, random, context, startX, endX, startZ, endZ, startY, endY, veinX, veinY, veinZ, horizontalSize, verticalSize)
                }
            }
        }

        return false
    }

    open fun generateVeinPart(
        structureWorldAccess: StructureWorldAccess,
        random: Random,
        context: FeatureContext<CustomOreFeatureConfig>,
        startX: Double,
        endX: Double,
        startZ: Double,
        endZ: Double,
        startY: Double,
        endY: Double,
        x: Int,
        y: Int,
        z: Int,
        horizontalSize: Int,
        verticalSize: Int
    ): Boolean {
        var i = 0
        val bitSet = BitSet(horizontalSize * verticalSize * horizontalSize)
        val mutable = BlockPos.Mutable()
        val j = context.config.sizeFactory(context)
        val ds = DoubleArray(j * 4)

        var t: Double
        var u: Double
        var v: Double
        var w: Double

        for (m in 0 until j) {
            val f = m.toFloat() / j.toFloat()
            t = MathHelper.lerp(f.toDouble(), startX, endX)
            u = MathHelper.lerp(f.toDouble(), startY, endY)
            v = MathHelper.lerp(f.toDouble(), startZ, endZ)
            w = random.nextDouble() * j.toDouble() / 16.0
            val l = ((MathHelper.sin(3.1415927f * f) + 1.0f).toDouble() * w + 1.0) / 2.0
            ds[m * 4 + 0] = t
            ds[m * 4 + 1] = u
            ds[m * 4 + 2] = v
            ds[m * 4 + 3] = l
        }

        for (m in 0 until j - 1) {
            if (ds[m * 4 + 3] > 0.0) {
                for (n in m + 1 until j) {
                    if (ds[n * 4 + 3] > 0.0) {
                        t = ds[m * 4 + 0] - ds[n * 4 + 0]
                        u = ds[m * 4 + 1] - ds[n * 4 + 1]
                        v = ds[m * 4 + 2] - ds[n * 4 + 2]
                        w = ds[m * 4 + 3] - ds[n * 4 + 3]
                        if (w * w > t * t + u * u + v * v) {
                            if (w > 0.0) {
                                ds[n * 4 + 3] = -1.0
                            } else {
                                ds[m * 4 + 3] = -1.0
                            }
                        }
                    }
                }
            }
        }

        val chunkSectionCache = ChunkSectionCache(structureWorldAccess)

        try {
            var n = 0
            while (n < j) {
                t = ds[n * 4 + 3]
                if (t >= 0.0) {
                    u = ds[n * 4 + 0]
                    v = ds[n * 4 + 1]
                    w = ds[n * 4 + 2]
                    val aa = max(MathHelper.floor(u - t), x)
                    val ab = max(MathHelper.floor(v - t), y)
                    val ac = max(MathHelper.floor(w - t), z)
                    val ad = max(MathHelper.floor(u + t), aa)
                    val ae = max(MathHelper.floor(v + t), ab)
                    val af = max(MathHelper.floor(w + t), ac)
                    for (ag in aa..ad) {
                        val ah = (ag.toDouble() + 0.5 - u) / t
                        if (ah * ah < 1.0) {
                            for (ai in ab..ae) {
                                val aj = (ai.toDouble() + 0.5 - v) / t
                                if ((ah * ah) + (aj * aj) < 2.0) {
                                    for (ak in ac..af) {
                                        val al = (ak.toDouble() + 0.5 - w) / t
                                        if (ah * ah + aj * aj + al * al < 3.0 && !structureWorldAccess.isOutOfHeightLimit(ai)) {
                                            val am = ag - x + (ai - y) * horizontalSize + (ak - z) * horizontalSize * verticalSize
                                            if (!bitSet[am]) {
                                                bitSet.set(am)
                                                mutable[ag, ai] = ak
                                                if (structureWorldAccess.method_37368(mutable)) {
                                                    val chunkSection = chunkSectionCache.getSection(mutable)
                                                    if (chunkSection !== WorldChunk.EMPTY_SECTION) {
                                                        val an = ChunkSectionPos.getLocalCoord(ag)
                                                        val ao = ChunkSectionPos.getLocalCoord(ai)
                                                        val ap = ChunkSectionPos.getLocalCoord(ak)
                                                        val blockState = chunkSection!!.getBlockState(an, ao, ap)
                                                        val var57 = context.config.targets.iterator()
                                                        while (var57.hasNext()) {
                                                            val target = var57.next()
                                                            if (!blockState.isAir && OreFeature.shouldPlace(blockState, { pos -> chunkSectionCache.getBlockState(pos) }, random, context.config, target, mutable)) {
                                                                chunkSection.setBlockState(an, ao, ap, target.state, false)
                                                                ++i
                                                                break
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                ++n
            }
        } catch (var60: Throwable) {
            try {
                chunkSectionCache.close()
            } catch (var59: Throwable) {
                var60.addSuppressed(var59)
            }
            throw var60
        }

        chunkSectionCache.close()
        return i > 0
    }

    companion object {
        val instance = register("custom_ore_feature", CustomOreFeature(
            RecordCodecBuilder.create {
                it.group(
                    Codec.list(OreFeatureConfig.Target.CODEC).fieldOf("targets")
                        .forGetter(CustomOreFeatureConfig::targets),
                    Codec.intRange(0, 64).fieldOf("size")
                        .forGetter(CustomOreFeatureConfig::size),
                    Codec.floatRange(0.0f, 1.0f).fieldOf("discard_chance_on_air_exposure")
                        .forGetter(CustomOreFeatureConfig::discardOnAirChance)
                ).apply(it) { targets, size, discardOnAirChance ->
                    CustomOreFeatureConfig(targets, size, discardOnAirChance)
                }
            }))

        private fun <C : FeatureConfig, F : Feature<C>> register(name: String, feature: F): F {
            return Registry.register(Registry.FEATURE, name, feature) as F
        }
    }
}