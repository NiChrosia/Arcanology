package arcanology.mixin;

import arcanology.common.type.api.world.block.PersistentBlock;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin {
    @Inject(method = "tick", at = @At(value = "INVOKE", target = "net/minecraft/server/world/ServerChunkManager.tick(Ljava/util/function/BooleanSupplier;)V"))
    private void persistentBlockHook(BooleanSupplier shouldKeepTicking, CallbackInfo info) {
        PersistentBlock.tickPersistentBlocks((ServerWorld)(Object)this);
    }
}