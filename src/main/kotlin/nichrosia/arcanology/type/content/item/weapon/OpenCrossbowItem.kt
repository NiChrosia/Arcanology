package nichrosia.arcanology.type.content.item.weapon

import net.minecraft.advancement.criterion.Criteria
import net.minecraft.client.item.TooltipContext
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.enchantment.Enchantments
import net.minecraft.entity.CrossbowUser
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.projectile.FireworkRocketEntity
import net.minecraft.entity.projectile.PersistentProjectileEntity
import net.minecraft.entity.projectile.PersistentProjectileEntity.PickupPermission
import net.minecraft.entity.projectile.ProjectileEntity
import net.minecraft.item.*
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtList
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvent
import net.minecraft.sound.SoundEvents
import net.minecraft.stat.Stats
import net.minecraft.text.LiteralText
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Formatting
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.util.UseAction
import net.minecraft.util.math.Quaternion
import net.minecraft.util.math.Vec3f
import net.minecraft.world.World
import nichrosia.arcanology.util.*
import java.util.*
import java.util.function.Predicate

@Suppress("MemberVisibilityCanBePrivate")
open class OpenCrossbowItem(settings: Settings) : RangedWeaponItem(settings), Vanishable {
    open val ItemStack.projectiles: List<ItemStack>
        get() {
            val list = mutableListOf<ItemStack>()

            if (nbt?.contains("ChargedProjectiles", 9) == true) {
                val nbtList = nbt?.getList("ChargedProjectiles", 10)

                nbtList?.forEachIndexed { index, _ ->
                    list.add(ItemStack.fromNbt(nbtList.getCompound(index)))
                }
            }

            return list
        }

    val Int.quickChargeSound: SoundEvent
        get() {
            return when (this) {
                1 -> SoundEvents.ITEM_CROSSBOW_QUICK_CHARGE_1
                2 -> SoundEvents.ITEM_CROSSBOW_QUICK_CHARGE_2
                3 -> SoundEvents.ITEM_CROSSBOW_QUICK_CHARGE_3
                else -> SoundEvents.ITEM_CROSSBOW_LOADING_START
            }
        }

    open val ItemStack.speed
        get() = if (hasProjectile(Items.FIREWORK_ROCKET)) 1.6f else 3.15f

    open var charged = false
    open var loaded = false

    override fun getHeldProjectiles(): Predicate<ItemStack> = CROSSBOW_HELD_PROJECTILES
    override fun getProjectiles(): Predicate<ItemStack> = BOW_PROJECTILES
    override fun getMaxUseTime(stack: ItemStack) = getPullTime(stack) + 3
    override fun getUseAction(stack: ItemStack) = UseAction.CROSSBOW
    override fun isUsedOnRelease(stack: ItemStack) = stack.isOf(this)
    override fun getRange() = 8

    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        val itemStack = user.getStackInHand(hand)

        return if (itemStack.charged) {
            user.shootAll(world, hand, itemStack, itemStack.speed, 1.0f)
            itemStack.charged = false

            TypedActionResult.consume(itemStack)
        } else if (!user.getArrowType(itemStack).isEmpty) {
            if (!itemStack.charged) {
                charged = false
                loaded = false

                user.setCurrentHand(hand)
            }
            TypedActionResult.consume(itemStack)
        } else {
            TypedActionResult.fail(itemStack)
        }
    }

    override fun onStoppedUsing(stack: ItemStack, world: World, user: LivingEntity, remainingUseTicks: Int) {
        val i = getMaxUseTime(stack) - remainingUseTicks
        val f = getPullProgress(i, stack)

        if (f >= 1.0f && !stack.charged && user.loadProjectiles(stack)) {
            stack.charged = true

            val soundCategory = if (user is PlayerEntity) SoundCategory.PLAYERS else SoundCategory.HOSTILE
            world.playSound(
                null,
                user.x,
                user.y,
                user.z,
                SoundEvents.ITEM_CROSSBOW_LOADING_END,
                soundCategory,
                1.0f,
                1.0f / (world.getRandom().nextFloat() * 0.5f + 1.0f) + 0.2f
            )
        }
    }

    override fun usageTick(world: World, user: LivingEntity, stack: ItemStack, remainingUseTicks: Int) {
        if (!world.isClient) {
            val i = EnchantmentHelper.getLevel(Enchantments.QUICK_CHARGE, stack)
            val soundEvent = i.quickChargeSound
            val soundEvent2 = if (i == 0) SoundEvents.ITEM_CROSSBOW_LOADING_MIDDLE else null
            val f = getPullProgress(stack.maxUseTime - remainingUseTicks, stack)

            if (f < 0.2f) {
                charged = false
                loaded = false
            }

            if (f >= 0.2f && !charged) {
                charged = true

                world.playSound(
                    null,
                    user.x,
                    user.y,
                    user.z,
                    soundEvent,
                    SoundCategory.PLAYERS,
                    0.5f,
                    1.0f
                )
            }

            if (f >= 0.5f && soundEvent2 != null && !loaded) {
                loaded = true

                world.playSound(
                    null,
                    user.x,
                    user.y,
                    user.z,
                    soundEvent2,
                    SoundCategory.PLAYERS,
                    0.5f,
                    1.0f
                )
            }
        }
    }

    override fun appendTooltip(stack: ItemStack, world: World?, tooltip: MutableList<Text>, context: TooltipContext) {
        val list = stack.projectiles

        if (stack.charged && list.isNotEmpty()) {
            val itemStack = list[0]

            tooltip.add(TranslatableText("item.minecraft.crossbow.projectile").append(" ").append(itemStack.toHoverableText()))

            if (context.isAdvanced && itemStack.isOf(Items.FIREWORK_ROCKET)) {
                val list2 = mutableListOf<Text>()

                Items.FIREWORK_ROCKET.appendTooltip(itemStack, world, list2, context)

                if (list2.isNotEmpty()) {
                    list2.forEachIndexed { index, _ ->
                        list2[index] = LiteralText("  ").append(list2[index]).formatted(Formatting.GRAY)
                    }

                    tooltip.addAll(list2)
                }
            }
        }
    }

    open fun LivingEntity.loadProjectiles(projectile: ItemStack): Boolean {
        val multishotLevel = EnchantmentHelper.getLevel(Enchantments.MULTISHOT, projectile)
        val arrowAmount = if (multishotLevel == 0) 1 else 3
        val bl = this is PlayerEntity && abilities.creativeMode
        var itemStack = getArrowType(projectile)
        var itemStack2 = itemStack.copy()

        for (k in 0 until arrowAmount) {
            if (k > 0) {
                itemStack = itemStack2.copy()
            }

            if (itemStack.isEmpty && bl) {
                itemStack = ItemStack(Items.ARROW)
                itemStack2 = itemStack.copy()
            }

            if (!loadProjectile(projectile, itemStack, k > 0, bl)) {
                return false
            }
        }

        return true
    }

    open fun LivingEntity.loadProjectile(
        crossbow: ItemStack,
        projectile: ItemStack,
        simulated: Boolean,
        creative: Boolean
    ): Boolean {
        return if (projectile.isEmpty) {
            false
        } else {
            val bl = creative && projectile.item is ArrowItem
            val itemStack2: ItemStack

            if (!bl && !creative && !simulated) {
                itemStack2 = projectile.split(1)

                if (projectile.isEmpty && this is PlayerEntity) {
                    inventory.removeOne(projectile)
                }
            } else {
                itemStack2 = projectile.copy()
            }

            crossbow.putProjectile(itemStack2)

            true
        }
    }

    open fun ItemStack.putProjectile(projectile: ItemStack) {
        val nbtCompound = orCreateNbt
        val nbtList2 = if (nbtCompound.contains("ChargedProjectiles", 9)) {
            nbtCompound.getList("ChargedProjectiles", 10)
        } else NbtList()

        val nbtCompound2 = NbtCompound()

        projectile.writeNbt(nbtCompound2)
        nbtList2.add(nbtCompound2)
        nbtCompound.put("ChargedProjectiles", nbtList2)
    }

    open fun ItemStack.clearProjectiles() {
        nbt?.let {
            val nbtList = it.getList("ChargedProjectiles", 9)
            nbtList.clear()
            it.put("ChargedProjectiles", nbtList)
        }
    }

    open fun LivingEntity.shoot(
        world: World = this.world,
        hand: Hand,
        crossbow: ItemStack,
        projectile: ItemStack,
        soundPitch: Float,
        creative: Boolean,
        speed: Float,
        divergence: Float,
        simulated: Float
    ) {
        if (!world.isClient) {
            val bl = projectile.isOf(Items.FIREWORK_ROCKET)
            val projectileEntity2: Any
            if (bl) {
                projectileEntity2 = FireworkRocketEntity(
                    world,
                    projectile,
                    this,
                    x,
                    eyeY - 0.15000000596046448,
                    z,
                    true
                )
            } else {
                projectileEntity2 = world.createArrow(this, crossbow, projectile)
                if (creative || simulated != 0.0f) {
                    projectileEntity2.pickupType = PickupPermission.CREATIVE_ONLY
                }
            }
            if (this is CrossbowUser) {
                shoot(target, crossbow, projectileEntity2 as ProjectileEntity, simulated)
            } else {
                val vec3d = getOppositeRotationVector(1.0f)
                val quaternion = Quaternion(Vec3f(vec3d), simulated, true)
                val vec3d2 = getRotationVec(1.0f)
                val vec3f = Vec3f(vec3d2)
                vec3f.rotate(quaternion)
                (projectileEntity2 as ProjectileEntity).setVelocity(
                    vec3f.x.toDouble(),
                    vec3f.y.toDouble(), vec3f.z.toDouble(), speed, divergence
                )
            }

            crossbow.damage(if (bl) 3 else 1, this) {
                it.sendToolBreakStatus(hand)
            }

            world.spawnEntity(projectileEntity2 as Entity)
            world.playSound(
                null as PlayerEntity?,
                x,
                y,
                z,
                SoundEvents.ITEM_CROSSBOW_SHOOT,
                SoundCategory.PLAYERS,
                1.0f,
                soundPitch
            )
        }
    }

    open fun World.createArrow(
        entity: LivingEntity,
        crossbow: ItemStack,
        arrowStack: ItemStack
    ): PersistentProjectileEntity {
        val arrowItem = (if (arrowStack.item is ArrowItem) arrowStack.item else Items.ARROW) as ArrowItem
        val arrow = arrowItem.createArrow(this, arrowStack, entity)
        val speedMultiplier = if (consumeValid(crossbow)) {
            consume(crossbow)

            getConsumeSpeedMultiplier(crossbow)
        } else 1f

        arrow.setProperties(entity, entity.pitch, entity.yaw, 0f, speedMultiplier, 1f)

        if (entity is PlayerEntity) {
            arrow.isCritical = true
        }

        arrow.setSound(SoundEvents.ITEM_CROSSBOW_HIT)
        arrow.isShotFromCrossbow = true

        val i = EnchantmentHelper.getLevel(Enchantments.PIERCING, crossbow)

        if (i > 0) {
            arrow.pierceLevel = i.toByte()
        }

        return arrow
    }

    open fun LivingEntity.shootAll(
        world: World,
        hand: Hand,
        stack: ItemStack,
        speed: Float,
        divergence: Float
    ) {
        val fs = getSoundPitches(random)

        stack.projectiles.forEachIndexed { i, itemStack ->
            val bl = this is PlayerEntity && abilities.creativeMode

            if (!itemStack.isEmpty) {
                shoot(world, hand, stack, itemStack, fs[i], bl, speed, divergence, when(i) {
                    0 -> 0f
                    1 -> -10f
                    2 -> 10f
                    else -> 0f
                })
            }
        }

        postShoot(world, this, stack)
    }

    open fun getSoundPitches(random: Random): FloatArray {
        val bl = random.nextBoolean()

        return floatArrayOf(
            1.0f,
            getSoundPitch(bl, random),
            getSoundPitch(!bl, random)
        )
    }

    open fun getSoundPitch(flag: Boolean, random: Random): Float {
        val f = if (flag) 0.63f else 0.43f
        return 1.0f / (random.nextFloat() * 0.5f + 1.8f) + f
    }

    open fun postShoot(world: World, entity: LivingEntity, stack: ItemStack) {
        if (entity is ServerPlayerEntity) {
            if (!world.isClient) {
                Criteria.SHOT_CROSSBOW.trigger(entity, stack)
            }

            entity.incrementStat(Stats.USED.getOrCreateStat(stack.item))
        }

        stack.clearProjectiles()
    }

    open fun consume(itemStack: ItemStack) {

    }

    open fun consumeValid(itemStack: ItemStack): Boolean {
        return false
    }

    open fun getConsumeSpeedMultiplier(itemStack: ItemStack): Float {
        return 1f
    }

    open fun getConsumePullTimeSubtractor(itemStack: ItemStack): Int {
        return 0
    }

    open fun getPullTime(stack: ItemStack): Int {
        val i = EnchantmentHelper.getLevel(Enchantments.QUICK_CHARGE, stack)

        return (if (i == 0) 25 else 25 - 5 * i) - if (consumeValid(stack)) {
            consume(stack)

            getConsumePullTimeSubtractor(stack)
        } else 0
    }

    open fun getPullProgress(useTicks: Int, stack: ItemStack): Float {
        return (useTicks.toFloat() / getPullTime(stack).toFloat()).clamp(max = 1.0f)
    }

    companion object {
        val ItemStack.projectiles: List<ItemStack>
            get() {
                val list = mutableListOf<ItemStack>()

                if (nbt?.contains("ChargedProjectiles", 9) == true) {
                    val nbtList = nbt?.getList("ChargedProjectiles", 10)

                    nbtList?.forEachIndexed { index, _ ->
                        list.add(ItemStack.fromNbt(nbtList.getCompound(index)))
                    }
                }

                return list
            }

        var ItemStack.charged: Boolean
            get() = nbt?.getBoolean("Charged") == true
            set(value) = orCreateNbt.putBoolean("Charged", value)

        fun ItemStack.hasProjectile(projectile: Item): Boolean {
            return projectiles.any { it.isOf(projectile) }
        }
    }
}