package arcanology.common.type.impl.assembly.gradual

import assemble.common.type.api.assembly.GradualAssembly
import assemble.common.type.api.storage.Progressable
import net.minecraft.util.Identifier

open class BlankGradualAssembly<C : Progressable>(id: Identifier) : GradualAssembly<C>(id, listOf(), listOf(), listOf(), listOf(), 0L, 1L) {
    override fun tick(container: C) {}
}