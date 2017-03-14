package com.jubaka.sors.managed;

import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.json.*;
import javax.json.spi.JsonProvider;
import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by root on 07.10.16.
 */
@Named
@Startup
@ApplicationScoped   //it is created multiple times,  what a fuck?
@ServerEndpoint("/webSocketHandler")
public class WebSocketHandler {
    private ReentrantLock lock = new ReentrantLock();



    private static HashMap<Long,TaskViewBean> taskBeans = new HashMap<Long,TaskViewBean>();

    public WebSocketHandler() {
        System.out.println(this.getClass()+" created");
    }

    @OnOpen
    public void onConnect(Session webSession, EndpointConfig config) {

            System.out.println("niga connected over webSocket");
            handleConnection(webSession);
    }

    @OnMessage
    public void handleMessage(String message, Session session) {
        System.out.println("Message from webSOcket "+message);
        JsonReader json = Json.createReader(new StringReader(message));
        JsonObject jsonObj = json.readObject();
        String idStr = jsonObj.getString("id");
        Long taskViewId = Long.valueOf(idStr);
        TaskViewBean taskViewBean = taskBeans.get(taskViewId);
        System.out.println("Search in map by id "+taskViewId);
        if (taskViewBean != null) {
            taskViewBean.setWebSession(session);
        }

    }

    private void handleConnection(Session webSession) {
        try {
            JsonProvider provider = JsonProvider.provider();
            JsonObjectBuilder jsonBuilder = provider.createObjectBuilder();
            jsonBuilder.add("action", "getId");
            JsonObject jsonObj = jsonBuilder.build();
            webSession.getBasicRemote().sendText(jsonObj.toString());
        } catch(IOException io) {
            io.printStackTrace();
        }
    }

    public void updateSubnetInfo(Session webSession) throws IOException {
       try {
           lock.lock();
           JsonProvider provider = JsonProvider.provider();
           JsonObjectBuilder objBuilder = provider.createObjectBuilder();
           objBuilder.add("action", "update");
           objBuilder.add("target", "subnet");
           JsonObject jsonObj = objBuilder.build();
           webSession.getBasicRemote().sendText(jsonObj.toString());

       } finally {
           lock.unlock();
       }
    }

    public void updateCategoryInfo(Session webSession) throws IOException {
        try {
            lock.lock();
            JsonProvider provider = JsonProvider.provider();
            JsonObjectBuilder objBuilder = provider.createObjectBuilder();
            objBuilder.add("action", "update");
            objBuilder.add("target", "categories");
            JsonObject jsonObj = objBuilder.build();
            webSession.getBasicRemote().sendText(jsonObj.toString());

        } finally {
            lock.unlock();
        }
    }

    public void updateIpInfo(Session webSession) throws IOException {
       try {
           lock.lock();
           JsonProvider provider = JsonProvider.provider();
           JsonObjectBuilder objBuilder = provider.createObjectBuilder();
           objBuilder.add("action", "update");
           objBuilder.add("target", "ip");
           JsonObject jsonObj = objBuilder.build();
           webSession.getBasicRemote().sendText(jsonObj.toString());

       }finally {
           lock.unlock();
       }
    }

    public HashMap<Long,TaskViewBean> getTaskBeans() {
        return taskBeans;
    }

    public void putBean(Long id, TaskViewBean bean) {
        System.out.println("putting taskBean to map "+id);
        taskBeans.put(id,bean);

    }
}