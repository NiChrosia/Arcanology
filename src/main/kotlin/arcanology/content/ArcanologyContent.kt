package arcanology.content

import arcanology.Arcanology
import nucleus.common.builtin.division.content.ContentCategory

open class ArcanologyContent(root: Arcanology) : ContentCategory<Arcanology>(root) {
    override val block = ABlockRegistrar(root)
    override val blockEntity = ABlockEntityTypeRegistrar(root)

    override val item = AItemRegistrar(root)
    override val itemGroup = AItemGroupRegistrar(root)

    override val screenHandler = AScreenHandlerRegistrar(root)
}