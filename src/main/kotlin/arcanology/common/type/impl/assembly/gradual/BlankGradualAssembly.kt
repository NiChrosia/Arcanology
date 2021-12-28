package arcanology.common.type.impl.assembly.gradual

import assemble.common.type.api.assembly.GradualAssembly
import assemble.common.type.api.storage.ProgressInventory
import net.minecraft.util.Identifier

open class BlankGradualAssembly<C : ProgressInventory>(id: Identifier) : GradualAssembly<C>(id, listOf(), listOf(), 0L, 1L) {
    override fun consume(container: C) {}
    override fun tick(container: C) {}
}