package ralf2oo2.trax.mixin;

import de.cuina.fireandfuel.CodecJLayerMP3;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.sound.Sound;
import net.minecraft.client.sound.SoundEntry;
import net.minecraft.client.sound.SoundManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;
import ralf2oo2.trax.soundmanager.TraxSoundManager;

import java.io.File;

@Mixin(SoundManager.class)
public class SoundManagerMixin implements TraxSoundManager {

    @Shadow private static SoundSystem soundSystem;
    @Shadow private static boolean started;
    @Shadow private GameOptions gameOptions;
    private SoundEntry traxSounds = new SoundEntry();

    @Inject(method = "start", at = @At(value = "INVOKE", target = "Lpaulscode/sound/SoundSystemConfig;setCodec(Ljava/lang/String;Ljava/lang/Class;)V", ordinal = 2), remap = false)
    void trax_setCodec(CallbackInfo ci){
        SoundSystemConfig.setCodec("mp3", CodecJLayerMP3.class);
    }

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    void trax_cancelBackgroundMusic(CallbackInfo ci){
        ci.cancel();
    }

    @Override
    public void loadTrack(String id, File soundFile) {
        this.traxSounds.loadStatic(id, soundFile);
    }

    @Override
    public void playTrack(String id, float volume, float pitch) {
        if (started) {

            if (soundSystem.playing("trax")) {
                soundSystem.stop("trax");
            }

            if (id != null) {
                Sound track = this.traxSounds.get(id);
                if (track != null && volume > 0.0F) {
//                    if (soundSystem.playing("BgMusic")) {
//                        soundSystem.stop("BgMusic");
//                    }
                    soundSystem.backgroundMusic( "trax", track.soundFile, track.id, false);
                    soundSystem.setVolume("trax", 0.5F * this.gameOptions.soundVolume);
                    soundSystem.play("trax");
                }

            }
        }
    }

    @Override
    public int getTrackCount() {
        return traxSounds.loadedSoundCount;
    }
}
