package nichrosia.arcanology.type.data.config.tool

import net.minecraft.item.Item
import net.minecraft.item.ToolMaterial
import net.minecraft.recipe.Ingredient
import nichrosia.arcanology.registry.Registrar
import nichrosia.arcanology.type.data.MaterialHelper
import nichrosia.arcanology.type.data.config.MaterialConfiguration
import kotlin.reflect.KProperty1

open class ToolMaterialConfiguration(
    name: String,
    val attackDamage: Float,
    val durability: Int,
    val enchantability: Int,
    val miningLevel: Int,
    val miningSpeedMultiplier: Float,
    val repairIngredient: RepairIngredient = RepairIngredient.Ingot
) : MaterialConfiguration<ToolMaterial, ToolMaterialConfiguration.ToolMaterialImpl>(name, Registrar.toolMaterial, content = {
    ToolMaterialImpl(attackDamage, durability, enchantability, miningLevel, miningSpeedMultiplier) { Ingredient.ofItems(repairIngredient.property(this)) }
}) {
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

    enum class RepairIngredient(val property: KProperty1<MaterialHelper, Item>) {
        Ingot(MaterialHelper::ingot), Crystal(MaterialHelper::crystal), MagicCrystal(MaterialHelper::magicCrystal), Dust(MaterialHelper::dust);
    }
}