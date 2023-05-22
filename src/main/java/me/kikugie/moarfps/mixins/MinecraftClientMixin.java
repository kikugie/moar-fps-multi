package me.kikugie.moarfps.mixins;

import me.kikugie.moarfps.MoarFPS;
import me.kikugie.moarfps.config.ConfigState;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Shadow
    private int fpsCounter;

    @ModifyVariable(
            at = @At(value = "FIELD", target = "Lnet/minecraft/client/MinecraftClient;fpsCounter:I"),
            method = "render(Z)V",
            index = 1,
            argsOnly = true
    )
    private boolean modifyFpsCounter(boolean value) {
        ConfigState config = MoarFPS.getConfig();
        if (!config.enabled)
            return false;

        this.fpsCounter = config.fakeFps;
        if (config.fluctuate) {
            this.fpsCounter += (int) (Math.random() * config.fluctuateAmount * 2) - config.fluctuateAmount;
        }
        return true;
    }
}
