package norsker.topdownshooter.config;
import norsker.topdownshooter.TopdownshooterApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.ArrayList;

@Configuration // tell Spring its a config
@EnableWebSocketMessageBroker // tells spring at brookeren kal op og kre
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer // interface brooker mellem clienten og servern
{


    /**
     *
     * @param registry a registry where you can register your endpoints
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry)
    {
        registry.addEndpoint("endpointOne").withSockJS();
    }

    /**
     *
     * enable simpleBrooker, enable endpoint creation from this topic.
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry)
    {
        registry.enableSimpleBroker("/topic");
    }
}
