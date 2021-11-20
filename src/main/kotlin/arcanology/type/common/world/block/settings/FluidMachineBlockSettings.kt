package arcanology.type.common.world.block.settings

import arcanology.type.common.world.data.energy.EnergyTier
import arcanology.type.common.world.data.fluid.FluidTier
import net.minecraft.block.Material

open class FluidMachineBlockSettings(material: Material, miningLevel: Int, energyTier: EnergyTier, open val fluidTier: FluidTier) : MachineBlockSettings(material, miningLevel, energyTier)