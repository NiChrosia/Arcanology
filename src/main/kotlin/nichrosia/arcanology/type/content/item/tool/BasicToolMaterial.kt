package nichrosia.arcanology.type.content.item.tool

import net.minecraft.item.ToolMaterial
import net.minecraft.recipe.Ingredient

/** A basic implementation of [ToolMaterial], because Mojang decided it should be an interface. */
open class BasicToolMaterial(
    val toolDurability: Int,
    val toolMiningSpeedMultiplier: Float,
    val toolAdditionalAttackDamage: Float,
    val toolMiningLevel: Int,
    val toolEnchantability: Int,
    val ingredientProvider: () -> Ingredient
) : ToolMaterial {
    override fun getDurability() = toolDurability
    override fun getMiningSpeedMultiplier() = toolMiningSpeedMultiplier
    override fun getAttackDamage() = toolAdditionalAttackDamage
    override fun getMiningLevel() = toolMiningLevel
    override fun getEnchantability() = toolEnchantability
    override fun getRepairIngredient() = ingredientProvider()
}