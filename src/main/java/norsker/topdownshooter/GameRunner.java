package norsker.topdownshooter;

import norsker.topdownshooter.model.gamemodels.GameInfo;
import norsker.topdownshooter.model.gamemodels.Updatable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@EnableScheduling
@Controller
public class GameRunner{

    @Autowired
    private SimpMessagingTemplate template;

    @Scheduled(fixedRate = 50)
    public void update()
    {
        GameInfo info = GameInfo.getInstance();
        //the update function that run the game uses game info

        for (Updatable object: info.getGameObjects())
        {
            object.update();
        }

        this.template.convertAndSend("/topic/update", info);
    }

}