package arcanology.common.content

import arcanology.common.Arcanology
import arcanology.common.type.impl.world.block.MachineBlock
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Material
import nucleus.common.builtin.division.content.BlockRegistrar

open class ABlockRegistrar(root: Arcanology) : BlockRegistrar<Arcanology>(root) {
    val machine by memberOf(root.identify("machine")) { MachineBlock(FabricBlockSettings.of(Material.METAL)) }.apply {
        lang(::readableEnglish)
        blockstate(::staticBlockstate)
        model(::omnidirectionalModel)
    }
}