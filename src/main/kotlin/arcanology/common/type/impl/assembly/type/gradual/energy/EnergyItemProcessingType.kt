package arcanology.common.type.impl.assembly.type.gradual.energy

import arcanology.common.type.impl.assembly.gradual.energy.EnergyItemProcessingAssembly
import assemble.common.type.api.assembly.type.AssemblyType
import assemble.common.type.api.storage.EnergyInventory
import assemble.common.type.api.storage.ItemInventory
import assemble.common.type.api.storage.ProgressInventory
import assemble.common.type.dsl.io.item.asIngredientStack
import assemble.common.type.dsl.io.item.asItemStack
import assemble.common.type.dsl.io.item.readIngredientStack
import assemble.common.type.dsl.io.item.writeIngredientStack
import com.google.gson.JsonObject
import net.minecraft.network.PacketByteBuf
import net.minecraft.util.Identifier

open class EnergyItemProcessingType<C>(
    id: Identifier,
    val slots: List<Int> = listOf(0, 1),
    /** Progress increment per tick. */
    val speed: Long,
    /** Progress threshold to craft. */
    val end: Long,
    /** Energy consumption per tick. */
    val consumption: Long
) : AssemblyType<C, EnergyItemProcessingAssembly<C>>(id) where C : ItemInventory, C : EnergyInventory, C : ProgressInventory {
    override fun deserialize(id: Identifier, json: JsonObject): EnergyItemProcessingAssembly<C> {
        val ingredient = json["ingredient"].asJsonObject.asIngredientStack
        val result = json["result"].asJsonObject.asItemStack

        return EnergyItemProcessingAssembly(id, ingredient, result, slots, consumption, speed, end)
    }

    override fun pack(packet: PacketByteBuf, assembly: EnergyItemProcessingAssembly<C>) {
        packet.writeIngredientStack(assembly.ingredient)
        packet.writeItemStack(assembly.result)
    }

    override fun unpack(id: Identifier, packet: PacketByteBuf): EnergyItemProcessingAssembly<C> {
        val ingredient = packet.readIngredientStack()
        val result = packet.readItemStack()

        return EnergyItemProcessingAssembly(id, ingredient, result, slots, consumption, speed, end)
    }
}