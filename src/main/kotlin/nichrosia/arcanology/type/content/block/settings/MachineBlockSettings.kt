package nichrosia.arcanology.type.content.block.settings

import net.minecraft.block.Material
import nichrosia.arcanology.type.energy.EnergyTier

open class MachineBlockSettings(material: Material, miningLevel: Int, val energyTier: EnergyTier) : ArcanologyBlockSettings(material, miningLevel) {
    override fun requiresTool(): MachineBlockSettings {
        return super.requiresTool() as MachineBlockSettings
    }

    override fun strength(hardness: Float, resistance: Float): MachineBlockSettings {
        return super.strength(hardness, resistance) as MachineBlockSettings
    }
}