package arcanology.client.content

import arcanology.client.ClientArcanology
import nucleus.client.builtin.division.content.ClientContentCategory

class ClientContent(root: ClientArcanology) : ClientContentCategory<ClientArcanology>(root) {
    override val screen = AScreenRegistrar(root)
}