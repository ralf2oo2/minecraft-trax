package ralf2oo2.trax.mixin;

import de.cuina.fireandfuel.CodecJLayerMP3;
import net.minecraft.client.sound.SoundManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import paulscode.sound.SoundSystemConfig;

@Mixin(SoundManager.class)
public class SoundManagerMixin {
    @Inject(method = "start", at = @At(value = "INVOKE", target = "Lpaulscode/sound/SoundSystemConfig;setCodec(Ljava/lang/String;Ljava/lang/Class;)V", ordinal = 2), remap = false)
    void trax_setCodec(CallbackInfo ci){
        SoundSystemConfig.setCodec("mp3", CodecJLayerMP3.class);
    }

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    void trax_cancelBackgroundMusic(CallbackInfo ci){
        ci.cancel();
    }
}
