package nichrosia.arcanology.worldgen

import com.mojang.serialization.Codec
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
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.sin

open class CustomOreFeature(codec: Codec<OreFeatureConfig>) : OreFeature(codec) {
    lateinit var config: CustomOreFeatureConfig
    lateinit var context: FeatureContext<OreFeatureConfig>

    override fun generate(context: FeatureContext<OreFeatureConfig>): Boolean {
        this.context = context

        val random = context.random
        val blockPos = context.origin
        val structureWorldAccess = context.world
        val veinSize = this.config.getSize(context)

        val f = random.nextFloat() * 3.1415927f
        val g = veinSize.toFloat() / 8.0f
        val i = MathHelper.ceil((veinSize.toFloat() / 16.0f * 2.0f + 1.0f) / 2.0f)
        val d = blockPos.x.toDouble() + sin(f.toDouble()) * g.toDouble()
        val e = blockPos.x.toDouble() - sin(f.toDouble()) * g.toDouble()
        val h = blockPos.z.toDouble() + cos(f.toDouble()) * g.toDouble()
        val j = blockPos.z.toDouble() - cos(f.toDouble()) * g.toDouble()
        val l = (blockPos.y + random.nextInt(3) - 2).toDouble()
        val m = (blockPos.y + random.nextInt(3) - 2).toDouble()
        val n = blockPos.x - MathHelper.ceil(g) - i
        val o = blockPos.y - 2 - i
        val p = blockPos.z - MathHelper.ceil(g) - i
        val q = 2 * (MathHelper.ceil(g) + i)
        val r = 2 * (2 + i)
        for (s in n..n + q) {
            for (t in p..p + q) {
                if (o <= structureWorldAccess.getTopY(Heightmap.Type.OCEAN_FLOOR_WG, s, t)) {
                    return generateVeinPart(
                        structureWorldAccess, random, config, d, e, h, j, l, m, n, o, p, q, r
                    )
                }
            }
        }
        return false
    }

    override fun generateVeinPart(
        structureWorldAccess: StructureWorldAccess,
        random: Random,
        config: OreFeatureConfig,
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
        val j = this.config.getSize(context)
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
            for (n in 0 until j) {
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
                                if (ah * ah + aj * aj < 1.0) {
                                    for (ak in ac..af) {
                                        val al = (ak.toDouble() + 0.5 - w) / t
                                        if (ah * ah + aj * aj + al * al < 1.0 && !structureWorldAccess.isOutOfHeightLimit(ai)) {
                                            val am = ag - x + (ai - y) * horizontalSize + (ak - z) * horizontalSize * verticalSize
                                            if (!bitSet[am]) {
                                                bitSet.set(am)
                                                mutable[ag, ai] = ak

                                                if (structureWorldAccess.method_37368(mutable)) {
                                                    val chunkSection = chunkSectionCache.getSection(mutable)
                                                    if (chunkSection !== WorldChunk.EMPTY_SECTION && chunkSection != null) {
                                                        val an = ChunkSectionPos.getLocalCoord(ag)
                                                        val ao = ChunkSectionPos.getLocalCoord(ai)
                                                        val ap = ChunkSectionPos.getLocalCoord(ak)
                                                        val blockState = chunkSection.getBlockState(an, ao, ap)
                                                        val var57: Iterator<OreFeatureConfig.Target> = this.config.targets.iterator()

                                                        while (var57.hasNext()) {
                                                            val target = var57.next()
                                                            if (shouldPlace(blockState, chunkSectionCache::getBlockState, random, this.config, target, mutable)) {
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

    /** Due to bad code design, [CustomOreFeature] can only be used with one feature.
     * A temporary fix would be to create an anonymous extension using the object keyword. */
    open fun configure(config: CustomOreFeatureConfig): ConfiguredFeature<*, *> {
        this.config = config

        return ConfiguredFeature(this, config)
    }

    companion object {
        val instance = register("custom-ore", CustomOreFeature(OreFeatureConfig.CODEC))

        private fun <C : FeatureConfig, F : Feature<C>> register(name: String, feature: F): F {
            return Registry.register(Registry.FEATURE, name, feature) as F
        }
    }
}