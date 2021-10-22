package nichrosia.arcanology.type.content.item.magic

import net.minecraft.block.BlockState
import net.minecraft.client.item.TooltipContext
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.LivingEntity
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import nichrosia.arcanology.type.id.item.IdentifiedPickaxeItem
import nichrosia.arcanology.type.data.config.tool.ToolMaterialConfig
import nichrosia.arcanology.type.instance.rune.Rune.Companion.mana
import nichrosia.arcanology.type.instance.rune.Rune.Companion.runes
import nichrosia.arcanology.type.item.magic.RuneItem
import nichrosia.arcanology.util.clamp

@Suppress("MemberVisibilityCanBePrivate")
open class RunicPickaxeItem(
    material: ToolMaterialConfig.ToolMaterialImpl,
    additionalAttackDamage: Int,
    attackSpeed: Float,
    settings: Settings,
    ID: Identifier
) : IdentifiedPickaxeItem(material, additionalAttackDamage, attackSpeed, settings, ID), RuneItem {
    override fun postMine(
        stack: ItemStack,
        world: World,
        state: BlockState,
        pos: BlockPos,
        miner: LivingEntity
    ): Boolean {
        if (!world.isClient && state.getHardness(world, pos) != 0.0f) {
            if (stack.runes.all { it.type.useDurability(stack) }) {
                stack.damage(1, miner) { e ->
                    e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND)
                }
            } else {
                if (stack.runes.any { !it.type.useDurability(stack) }) {
                    stack.mana -= 1

                    stack.mana = clamp(stack.mana, max = stack.runes.maxOf { it.type.maxMana })
                }
            }
        }

        return true
    }

    override fun appendTooltip(
        stack: ItemStack,
        world: World?,
        tooltip: MutableList<Text>,
        context: TooltipContext
    ) {
        super<IdentifiedPickaxeItem>.appendTooltip(stack, world, tooltip, context)
        super<RuneItem>.appendTooltip(stack.runes, tooltip, context)
    }

    override fun getMiningSpeedMultiplier(stack: ItemStack, state: BlockState): Float {
        return stack.runes.map { it.type.miningSpeedMultiplier }.fold(1f) { i, e -> i * e }
    }

    override fun getItemBarColor(stack: ItemStack): Int {
        return stack.runes.firstOrNull { it.type.overrideItemBar }?.type?.getItemBarColor(stack) ?: super.getItemBarColor(stack)
    }

    override fun isItemBarVisible(stack: ItemStack): Boolean {
        return stack.runes.any { it.type.isItemBarVisible(stack) }
    }

    override fun getItemBarStep(stack: ItemStack): Int {
        return stack.runes.firstOrNull { it.type.overrideItemBar }?.type?.getItemBarStep(stack) ?: super.getItemBarStep(stack)
    }
}