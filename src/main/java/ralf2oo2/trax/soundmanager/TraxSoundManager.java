package ralf2oo2.trax.soundmanager;

import ralf2oo2.trax.TraxSong;

import java.io.File;
import java.util.List;

public interface TraxSoundManager {
    void loadTrack(TraxSong traxSong);

    void playTrack(TraxSong traxSong, float volume, float pitch);

    List<TraxSong> getTraxSongs();

    int getTrackCount();
}
