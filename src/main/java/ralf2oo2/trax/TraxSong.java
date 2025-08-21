package ralf2oo2.trax;

import java.nio.file.Path;

public class TraxSong {
    private final String name;
    private final String artist;
    private final String album;
    private final String id;
    private final Path filePath;

    public TraxSong(String name, String artist, String album, String id, Path filePath){
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.id = id;
        this.filePath = filePath;
    }

    public String getName(){
        return this.name;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public String getId() {
        return id;
    }

    public Path getFilePath() {
        return filePath;
    }
}
