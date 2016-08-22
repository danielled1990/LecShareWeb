/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaclass;

import javax.websocket.Session;

/**
 *
 * @author Danielle
 */
public class BroadCastUsers {
    private String type;
    private String nameToconectTo;
    private String name;
    private String videoId;
    private Session webSocketImagesession;
    private Session webSocketChatSession;
    public String getNameToConnectTo(){
        return nameToconectTo;
    }
    public String getVideoId(){
        return videoId;
    }

    public String getName(){
        return name;
    }
    public Session getWebSocketImagesession(){
        return webSocketImagesession;
    }
    public void setWebSocketImagesession(Session session){
        webSocketImagesession = session;
    }
        public Session getwebSocketChatSession(){
        return webSocketChatSession;
    }
    public void setwebSocketChatSession(Session session){
        webSocketChatSession = session;
    }
    
    
}
