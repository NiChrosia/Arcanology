package arcanology.mixin.client;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import arcanology.type.common.world.item.tooltip.CustomTooltipDataProvider;
import arcanology.util.collections.OptionalsKt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.stream.Collectors;

@Mixin(Screen.class)
public abstract class MixinScreen {
    @Shadow
    public abstract List<Text> getTooltipFromItem(ItemStack stack);

    @Shadow
    protected abstract void renderTooltipFromComponents(MatrixStack matrices, List<TooltipComponent> components, int x, int y);

    /** @author GabrielOlvH */
    @Inject(method = "renderTooltip(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/item/ItemStack;II)V", at = @At("INVOKE"), cancellable = true)
    private void tooltipComponent(MatrixStack matrices, ItemStack stack, int x, int y, CallbackInfo ci) {
        if (stack.getItem() instanceof CustomTooltipDataProvider provider) {
            // mimic vanilla behaviour, cursed mixin yes but blame mojang
            var lines = getTooltipFromItem(stack);
            var data = OptionalsKt.transform(stack.getTooltipData(), TooltipComponent::of);
            var list = lines.stream().map(Text::asOrderedText).map(TooltipComponent::of).collect(Collectors.toList());
            data.ifPresent(datax -> list.add(1, datax));

            // add custom tooltip stuff
            provider.getData(stack).forEach(it -> list.add(1, it.toComponent()));

            this.renderTooltipFromComponents(matrices, list, x, y);
            ci.cancel();
        }
    }
}