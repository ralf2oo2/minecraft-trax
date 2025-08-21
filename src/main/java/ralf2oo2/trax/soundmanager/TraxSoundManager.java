package ralf2oo2.trax.soundmanager;

import java.io.File;

public interface TraxSoundManager {
    void loadTrack(String id, File soundFile);

    void playTrack(String id, float volume, float pitch);

    int getTrackCount();
}
