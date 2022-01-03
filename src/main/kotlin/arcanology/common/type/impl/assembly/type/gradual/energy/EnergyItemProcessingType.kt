package arcanology.common.type.impl.assembly.type.gradual.energy

import arcanology.common.type.impl.assembly.gradual.energy.EnergyItemProcessingAssembly
import assemble.common.Assemble
import assemble.common.type.api.assembly.serializer.SlotSerializer
import assemble.common.type.api.assembly.type.gradual.GradualAssemblyType
import assemble.common.type.api.storage.EnergyStorageInventory
import assemble.common.type.api.storage.ItemStorageInventory
import assemble.common.type.api.storage.Progressable
import com.google.gson.JsonObject
import net.minecraft.network.PacketByteBuf
import net.minecraft.util.Identifier

open class EnergyItemProcessingType<C>(
    id: Identifier,
    val slots: List<Int> = listOf(0, 1),
    speed: Long,
    end: Long,
    consumption: Long
) : GradualAssemblyType<C, EnergyItemProcessingAssembly<C>>(id, speed, end, consumption) where C : ItemStorageInventory, C : EnergyStorageInventory, C : Progressable {
    val ingredient = SlotSerializer(Assemble.content.slotSerializer.ingredientStack, "ingredient")
    val result = SlotSerializer(Assemble.content.slotSerializer.itemStack, "result")

    override fun read(id: Identifier, json: JsonObject): EnergyItemProcessingAssembly<C> {
        return EnergyItemProcessingAssembly(id, ingredient(json), result(json), slots, consumption, speed, end)
    }

    override fun pack(buffer: PacketByteBuf, assembly: EnergyItemProcessingAssembly<C>) {
        ingredient(buffer, assembly.ingredient)
        result(buffer, assembly.result)
    }

    override fun unpack(id: Identifier, buffer: PacketByteBuf): EnergyItemProcessingAssembly<C> {
        return EnergyItemProcessingAssembly(id, ingredient(buffer), result(buffer), slots, consumption, speed, end)
    }
}