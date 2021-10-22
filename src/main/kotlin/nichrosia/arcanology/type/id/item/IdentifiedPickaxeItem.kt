package nichrosia.arcanology.type.id.item

import net.minecraft.item.PickaxeItem
import net.minecraft.util.Identifier
import nichrosia.arcanology.type.data.config.tool.ToolMaterialConfig

open class IdentifiedPickaxeItem(
    toolMaterial: ToolMaterialConfig.ToolMaterialImpl,
    additionalAttackDamage: Int,
    attackSpeed: Float,
    settings: Settings,
    override val ID: Identifier
) : PickaxeItem(toolMaterial, additionalAttackDamage, attackSpeed, settings), AbstractItem