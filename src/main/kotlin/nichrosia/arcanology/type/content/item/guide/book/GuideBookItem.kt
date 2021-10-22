package nichrosia.arcanology.type.content.item.guide.book

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.Hand
import net.minecraft.util.Identifier
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.type.id.item.IdentifiedItem
import vazkii.patchouli.api.PatchouliAPI

open class GuideBookItem(settings: Settings, ID: Identifier, private val bookNamespace: String) : IdentifiedItem(settings, ID) {
    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        if (!world.isClient) {
            PatchouliAPI.get().openBookGUI(user as ServerPlayerEntity, Arcanology.idOf(bookNamespace))
            return TypedActionResult.success(user.getStackInHand(hand))
        }

        return TypedActionResult.consume(user.getStackInHand(hand))
    }
}