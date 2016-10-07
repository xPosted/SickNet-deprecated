package com.jubaka.sors.managed;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * Created by root on 07.10.16.
 */
@Named
@ApplicationScoped
@ServerEndpoint("/webSocketHandler")
public class WebSocketHandler {


    @OnOpen
    public void onConnect(Session webSession, EndpointConfig config) {
        try {
            System.out.println("niga connected over webSocket");
            webSession.getBasicRemote().sendText("niga message");


        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    @OnMessage
    public void handleMessage(String message, Session session) {
        System.out.println("Message from webSOcket "+message);
    }
}