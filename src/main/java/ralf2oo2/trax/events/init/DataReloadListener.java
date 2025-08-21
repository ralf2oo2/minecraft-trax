package ralf2oo2.trax.events.init;

import net.fabricmc.loader.api.FabricLoader;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.sound.SoundManager;
import net.modificationstation.stationapi.api.event.resource.DataReloadEvent;
import org.lwjgl.Sys;
import ralf2oo2.trax.soundmanager.TraxSoundManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.stream.Stream;

public class DataReloadListener {
    Minecraft minecraft;
    public static TraxSoundManager traxSoundManager;
    @EventListener
    public void dataReload(DataReloadEvent event){
        System.out.println("reload event");
        minecraft = Minecraft.class.cast(FabricLoader.getInstance().getGameInstance());
        traxSoundManager = (TraxSoundManager) minecraft.soundManager;

        loadFiles();
    }

    public void loadFiles(){
        Path gameDir = FabricLoader.getInstance().getGameDir();

        Path trackDir = gameDir.resolve("trax");

        if(!Files.exists(trackDir)){
            try{
                Files.createDirectories(trackDir);
            } catch (IOException e){
                System.out.println(e);
                return;
            }
        }
        try(Stream<Path> paths = Files.list(trackDir)){
            paths.filter(Files::isRegularFile)
                    .filter(path -> {
                        String name = path.getFileName().toString().toLowerCase(Locale.ROOT);
                        return name.endsWith(".mp3") || name.endsWith(".wav") || name.endsWith(".ogg");
                    })
                    .forEach(path -> {
                        System.out.println("Found sound file: " + path);
                        traxSoundManager.loadTrack("pog.pog", path.toFile());
                    });
        }
        catch (IOException e){
            System.out.println(e);
        }
        System.out.println("Loaded " + traxSoundManager.getTrackCount() + " tracks");
    }
}
