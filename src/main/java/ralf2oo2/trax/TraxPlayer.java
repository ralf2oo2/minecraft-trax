package ralf2oo2.trax;

import ralf2oo2.trax.events.init.DataReloadListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TraxPlayer {
    public static final TraxPlayer INSTANCE = new TraxPlayer();

    private int queueIndex = 0;
    private List<TraxSong> queue = new ArrayList<>();


    public void startMusic(){
        newQueue();
        playTrack(queueIndex);
    }

    private void newQueue() {
        this.queue = DataReloadListener.traxSoundManager.getTraxSongs();
        this.queueIndex = 0;
        Collections.shuffle(queue);
    }

    private void nextSong(){
        queueIndex++;
    }

    private void playTrack(int index){
        if(queueIndex < queue.size() - 1){
            DataReloadListener.traxSoundManager.playTrack(queue.get(queueIndex), 0.5f, 1f);
        }
    }

    public void skipTrack(){
        nextSong();
    }
}
