package norsker.topdownshooter;

import en.websocket.model.Message;
import en.websocket.model.Response;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller // skal ikke v;re en restcontroller
public class HomeController
{


    /**
     *
     * @param message the object that ha been send from the stomp.client
     * @return Response is send to the subscriberes  of /topic/messages
     */
    @MessageMapping("/myMessages") //definere pathen om stompclienten sendr til
    @SendTo("/topic/messages") // definere hvilken path svaret sendes til.
    public Response handleMessage(Message message)
    {
        Response response = new Response("server says hello");
        return response;
    }


}
