package ralf2oo2.trax.mixin;

import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ralf2oo2.trax.TraxPlayer;
import ralf2oo2.trax.events.init.DataReloadListener;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {
    @Inject(method = "init", at = @At("TAIL"))
    void trax_playMusic(CallbackInfo ci){
        TraxPlayer.INSTANCE.startMusic();
    }
}
