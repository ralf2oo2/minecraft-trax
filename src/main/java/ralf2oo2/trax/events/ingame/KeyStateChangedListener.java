package ralf2oo2.trax.events.ingame;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.entity.player.PlayerEntity;
import net.modificationstation.stationapi.api.client.event.keyboard.KeyStateChangedEvent;
import net.modificationstation.stationapi.api.entity.player.PlayerHelper;
import org.lwjgl.input.Keyboard;

public class KeyStateChangedListener {
    @EventListener
    public void keyStateChanged(KeyStateChangedEvent event){
        if(event.environment == KeyStateChangedEvent.Environment.IN_GAME){
            if(Keyboard.getEventKeyState() && Keyboard.isKeyDown(Keyboard.KEY_C)){
                PlayerEntity player = PlayerHelper.getPlayerFromGame();
                player.world.playSound(player, "trax:trax.sound.getlow", 1f, 1f);
            }
        }
    }
}
