package ralf2oo2.trax.events.ingame;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.entity.player.PlayerEntity;
import net.modificationstation.stationapi.api.client.event.keyboard.KeyStateChangedEvent;
import net.modificationstation.stationapi.api.entity.player.PlayerHelper;
import org.lwjgl.input.Keyboard;
import ralf2oo2.trax.events.init.DataReloadListener;

public class KeyStateChangedListener {
    @EventListener
    public void keyStateChanged(KeyStateChangedEvent event){
        if(event.environment == KeyStateChangedEvent.Environment.IN_GAME){
            if(Keyboard.getEventKeyState() && Keyboard.isKeyDown(Keyboard.KEY_C)){
                PlayerEntity player = PlayerHelper.getPlayerFromGame();
                DataReloadListener.traxSoundManager.playTrack("pog.pog", 1f, 1f);
            }
        }
    }
}
