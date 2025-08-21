package ralf2oo2.trax.events.init;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import net.fabricmc.loader.api.FabricLoader;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.sound.SoundManager;
import net.modificationstation.stationapi.api.event.resource.DataReloadEvent;
import org.lwjgl.Sys;
import ralf2oo2.trax.TraxSong;
import ralf2oo2.trax.soundmanager.TraxSoundManager;
import ralf2oo2.trax.util.Util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;

public class DataReloadListener {
    Minecraft minecraft;
    Map<String, Object> trackInfo;


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
                        String fileName = path.getFileName().toString();
                        fileName = Util.removeExtension(fileName);
                        traxSoundManager.loadTrack(createTraxSong(fileName, path));
                    });
        }
        catch (IOException e){
            System.out.println(e);
        }
        System.out.println("Loaded " + traxSoundManager.getTrackCount() + " tracks");
    }

    private void loadTrackData(){
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

        Gson gson = new Gson();
        try {
            JsonReader reader = new JsonReader(new FileReader(trackDir.resolve("trackinfo.json").toFile()));
            this.trackInfo = gson.fromJson(reader, Map.class);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private TraxSong createTraxSong(String id, Path path){
        String songName = Util.removeExtension(path.getFileName().toString());
        String artist = "";
        String album = "";
        if(trackInfo != null && trackInfo.containsKey(songName)){
            Map<String, Object> info = (Map<String, Object>) trackInfo.get(songName);
            songName = info.containsKey("name") ? (String) info.get("name") : songName;
            artist = info.containsKey("artist") ? (String) info.get("artist") : artist;
            album = info.containsKey("album") ? (String) info.get("album") : artist;
        }
        return new TraxSong(songName, artist, album, id, path);
    }


}
