package arcanology.type.common.world.block.settings

import arcanology.type.common.world.data.energy.EnergyTier
import net.minecraft.block.Material

@Suppress("UNCHECKED_CAST")
open class MachineBlockSettings(material: Material, miningLevel: Int, val energyTier: EnergyTier) : ArcanologyBlockSettings(material, miningLevel)