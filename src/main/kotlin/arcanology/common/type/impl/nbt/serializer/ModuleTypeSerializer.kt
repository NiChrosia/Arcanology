package arcanology.common.type.impl.nbt.serializer

import arcanology.common.Arcanology
import arcanology.common.type.api.machine.module.ModuleType
import arcanology.common.type.impl.world.block.entity.MachineBlockEntity
import assemble.common.type.api.assembly.GradualAssembly
import dev.nathanpb.ktdatatag.serializer.DataSerializer
import net.minecraft.nbt.NbtCompound
import net.minecraft.util.Identifier

open class ModuleTypeSerializer : DataSerializer<ModuleType<out GradualAssembly<MachineBlockEntity>>> {
    override fun read(tag: NbtCompound, key: String): ModuleType<out GradualAssembly<MachineBlockEntity>> {
        val id = tag.getString(key).let(::Identifier)

        return Arcanology.content.moduleType.content[id] ?: throw IllegalArgumentException("Given id in compound is not present in module type registrar.")
    }

    override fun write(tag: NbtCompound, key: String, data: ModuleType<out GradualAssembly<MachineBlockEntity>>) {
        val id = Arcanology.content.moduleType.reversed[data] ?: throw IllegalArgumentException("Given module type is not present in module type registrar.")

        tag.putString(key, id.toString())
    }
}