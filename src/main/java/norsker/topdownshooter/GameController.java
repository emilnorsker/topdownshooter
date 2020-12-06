package norsker.topdownshooter;

import norsker.topdownshooter.model.Input;
import norsker.topdownshooter.model.Message;
import norsker.topdownshooter.model.gamemodels.GameInfo;
import norsker.topdownshooter.model.gamemodels.Gameobject;
import norsker.topdownshooter.model.gamemodels.Player;
import norsker.topdownshooter.model.gamemodels.Updatable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller // skal ikke være en restcontroller
public class GameController
{

    int increasingNumber = 0;

    /**
     *  @return Response is send to the subscriberes  of /topic/messages
     */
//    @MessageMapping("/myMessages") //definere pathen om stompclienten sendr til
    @SendTo("/topic/update") // definere hvilken path svaret sendes til.
    public List<Updatable> updateClient()
    {
        GameInfo info = GameInfo.getInstance();
        return info.getGameObjects();
    }

    @MessageMapping("/createPlayer")
    void createPlayer(Message playerId)
    {
        GameInfo info = GameInfo.getInstance();
        info.addObject(new Player(Integer.parseInt(playerId.getMessage())));
        System.out.println("player spawned");
    }

    @MessageMapping("/input") //definere pathen om stompclienten sender til
    public void handleMessage(Input input)
    {
        GameInfo.getInstance().getGameObjects().forEach(obj ->
        {
            if (obj.getClass().getSimpleName().equals("Player"))
            {
                Player player = (Player) obj;
                if (player.getPlayerId()==input.getPlayerId())
                {
                    player.setInput(input);
                }
            }
        });
    }





    @GetMapping
    String home(Model model)
    {
        model.addAttribute("randomNumber", increasingNumber);
        model.addAttribute("boardsize", GameInfo.getInstance().getBoardsize());
        increasingNumber++;
        return "game";
    }


}
