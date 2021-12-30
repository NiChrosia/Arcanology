package arcanology.common.content

import arcanology.common.Arcanology
import nucleus.common.builtin.division.content.ContentCategory

open class ArcanologyContent(root: Arcanology) : ContentCategory<Arcanology>(root) {
    override val block = ABlockRegistrar(root)
    override val blockEntity = ABlockEntityTypeRegistrar(root)

    override val item = AItemRegistrar(root)
    override val itemGroup = AItemGroupRegistrar(root)

    override val screenHandler = AScreenHandlerRegistrar(root)
    val assemblyType = AAssemblyTypeRegistrar(root)
    val nbtSerializer = ANbtSerializerRegistrar(root)

    val energyTier = AEnergyTierRegistrar(root)
    val fluidTier = AFluidTierRegistrar(root)
    val machineTier = AMachineTierRegistrar(root)
}