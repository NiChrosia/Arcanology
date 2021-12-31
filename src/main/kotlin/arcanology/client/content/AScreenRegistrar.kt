package arcanology.client.content

import arcanology.client.ClientArcanology
import arcanology.common.Arcanology
import arcanology.common.type.impl.gui.screen.ItemProcessingScreen
import nucleus.client.builtin.division.content.ScreenEntry
import nucleus.client.builtin.division.content.ScreenRegistrar

class AScreenRegistrar(root: ClientArcanology) : ScreenRegistrar<ClientArcanology>(root) {
    val itemProcessingMachine by memberOf<ScreenEntry<*, *>>(root.identify("item_processing_machine")) {
        entryOf(Arcanology.content.screenHandler.itemProcessingMachine, ::ItemProcessingScreen)
    }
}