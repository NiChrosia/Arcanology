package arcanology.common.type.api.registrar

import dev.nathanpb.ktdatatag.serializer.DataSerializer
import net.minecraft.util.Identifier
import nucleus.common.builtin.division.ModRoot
import nucleus.common.registrar.type.BinaryRegistrar

open class NbtSerializerRegistrar<R : ModRoot<R>>(root: R) : BinaryRegistrar<Identifier, DataSerializer<*>, R>(root)