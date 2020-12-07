package norsker.topdownshooter;

import norsker.topdownshooter.model.gamemodels.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.time.LocalDateTime;

@EnableScheduling
@Controller
public class GameRunner{


    long lastTicktime = 0;
    long lastTick = 0;

    @Autowired
    private SimpMessagingTemplate template;

    /**
     * Defines our update method, run a update tick, and send the new gamestate to the clients that are subscribed to /topic/update
     * fixed rate with parameter of 10 means ever 10 millisecond run this.
     */
    @Scheduled(fixedRate = 10)
    public synchronized void update() {
        GameInfo info = GameInfo.getInstance();
        lastTick = System.nanoTime() - lastTicktime;

        try {
            if (info.isUpdated()) { // no need to update if the previous cycle is not finished
                info.setUpdated(false);
                //the update function that run the game uses game info

                for (Updatable object : info.getGameObjects()) {
                    object.update();
                    if (object.getClass().getSimpleName().equals("Bullet")) {
                        Bullet bullet = (Bullet) object;
                        for (Updatable object_2 : info.getGameObjects()) {
                            if (object_2.getClass().getSimpleName().equals("Player"))
                            {
                                Player player = (Player) object_2;
                                if (bullet.intersects(player))
                                {
                                    bullet.damage((Player) object_2);
                                }
                            }
                        }
                    }
                }
                info.setUpdated(true);
                this.template.convertAndSend("/topic/update", info);
                info.updateGameInfo();
            }
        }catch (Exception e){info.setUpdated(true);}
    }

}