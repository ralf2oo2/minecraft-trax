package ralf2oo2.trax.mixin;

import de.cuina.fireandfuel.CodecJLayerMP3;
import net.fabricmc.loader.api.FabricLoader;
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
import ralf2oo2.trax.TraxPlayer;
import ralf2oo2.trax.TraxSong;
import ralf2oo2.trax.soundmanager.TraxSoundManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

import static ralf2oo2.trax.events.init.DataReloadListener.traxSoundManager;

@Mixin(SoundManager.class)
public class SoundManagerMixin implements TraxSoundManager {

    @Shadow private static SoundSystem soundSystem;
    @Shadow private static boolean started;
    @Shadow private GameOptions gameOptions;
    private SoundEntry traxSounds = new SoundEntry();
    private List<TraxSong> traxSongList = new ArrayList<>();

    @Inject(method = "start", at = @At(value = "INVOKE", target = "Lpaulscode/sound/SoundSystemConfig;setCodec(Ljava/lang/String;Ljava/lang/Class;)V", ordinal = 2), remap = false)
    void trax_setCodec(CallbackInfo ci){
        SoundSystemConfig.setCodec("mp3", CodecJLayerMP3.class);
    }

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    void trax_cancelBackgroundMusic(CallbackInfo ci){
//        if(started && !soundSystem.playing("trax")){
//            TraxPlayer.INSTANCE.skipTrack();
//        }
        ci.cancel();
    }

    @Override
    public void loadTrack(TraxSong traxSong) {
        if(started) System.out.println("soundsystem has started");
        this.traxSongList.add(traxSong);
        this.traxSounds.loadStatic(traxSong.getId(), traxSong.getFilePath().toFile());
    }

    @Override
    public void playTrack(TraxSong traxSong, float volume, float pitch) {
        if (started) {

            if (soundSystem.playing("trax")) {
                soundSystem.stop("trax");
            }

            if (traxSong.getId() != null) {
                Sound track = this.traxSounds.get(traxSong.getId());
                if (track != null && volume > 0.0F) {
//                    if (soundSystem.playing("BgMusic")) {
//                        soundSystem.stop("BgMusic");
//                    }
                    soundSystem.backgroundMusic( "trax", track.soundFile, track.id, false);
                    soundSystem.setVolume("trax", 0.5F * this.gameOptions.soundVolume);
                    soundSystem.play("trax");
                    System.out.println(track.soundFile.toString());
                }

            }
        }
    }

    @Override
    public List<TraxSong> getTraxSongs() {
        return new ArrayList<>(traxSongList);
    }

    @Override
    public int getTrackCount() {
        return traxSounds.loadedSoundCount;
    }
}
