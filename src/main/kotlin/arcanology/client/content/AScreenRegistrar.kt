package arcanology.client.content

import arcanology.client.ClientArcanology
import nucleus.client.builtin.division.content.ScreenRegistrar

open class AScreenRegistrar(root: ClientArcanology) : ScreenRegistrar<ClientArcanology>(root) {
//    val modular by memberOf(root.identify("modular")) { entryOf(Arcanology.content.screenHandler.modular, ::ModularScreen) }
}