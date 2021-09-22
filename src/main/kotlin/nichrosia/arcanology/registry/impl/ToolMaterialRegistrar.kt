package nichrosia.arcanology.registry.impl

import net.minecraft.item.ToolMaterial as ToolMaterial
import net.minecraft.recipe.Ingredient
import nichrosia.arcanology.content.AMaterials
import nichrosia.arcanology.registry.BasicRegistrar
import nichrosia.arcanology.registry.properties.RegistryProperty

open class ToolMaterialRegistrar : BasicRegistrar<ToolMaterialRegistrar.ToolMaterialImpl>() {
    val silver by RegistryProperty("silver") { ToolMaterialImpl(5f, 350, 25, 2, 6f) { Ingredient.ofItems(AMaterials.silver.ingot) } }

    open class ToolMaterialImpl(
        @JvmField val attackDamage: Float,
        @JvmField val durability: Int,
        @JvmField val enchantability: Int,
        @JvmField val miningLevel: Int,
        @JvmField val miningSpeedMultiplier: Float,
        @JvmField val repairIngredient: () -> Ingredient
    ) : ToolMaterial {
        override fun getAttackDamage() = attackDamage
        override fun getDurability() = durability
        override fun getEnchantability() = enchantability
        override fun getMiningLevel() = miningLevel
        override fun getMiningSpeedMultiplier() = miningSpeedMultiplier
        override fun getRepairIngredient() = repairIngredient()
    }
}