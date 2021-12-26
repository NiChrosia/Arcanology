package arcanology.client.content

import arcanology.client.ClientArcanology
import arcanology.common.Arcanology
import arcanology.common.type.impl.screen.ModularScreen
import nucleus.client.builtin.division.content.ScreenRegistrar

open class AScreenRegistrar(root: ClientArcanology) : ScreenRegistrar<ClientArcanology>(root) {
    val modular by memberOf(root.identify("modular")) { entryOf(Arcanology.content.screenHandler.modular, ::ModularScreen) }
}