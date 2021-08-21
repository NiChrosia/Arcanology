package nichrosia.arcanology.content

import net.minecraft.recipe.Ingredient
import net.minecraft.item.ToolMaterial as ToolMaterialInterface
import nichrosia.arcanology.content.type.Content

object AToolMaterials : Content {
    lateinit var silver: ToolMaterial
    
    override fun load() {
        silver = ToolMaterial(5f, 350, 25, 2, 6f) { Ingredient.ofItems(AMaterials.silver.ingot) }
    }
    
    open class ToolMaterial(
        @JvmField val attackDamage: Float,
        @JvmField val durability: Int,
        @JvmField val enchantability: Int,
        @JvmField val miningLevel: Int,
        @JvmField val miningSpeedMultiplier: Float,
        @JvmField val repairIngredient: () -> Ingredient
    ) : ToolMaterialInterface {
        override fun getAttackDamage() = attackDamage
        override fun getDurability() = durability
        override fun getEnchantability() = enchantability
        override fun getMiningLevel() = miningLevel
        override fun getMiningSpeedMultiplier() = miningSpeedMultiplier
        override fun getRepairIngredient() = repairIngredient()
    }
}