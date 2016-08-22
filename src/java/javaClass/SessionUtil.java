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
public class SessionUtil {
    private String videoId;
    private String connectToBroadCastOf;
    private String URL;
    private Session webSocketImageSession;
    private Session webSocketChatSession;
    private int page = 0;
    public String getVideoId(){
        return videoId;
    }
    public void setVideoId(String video){
        videoId = video;
    }
    public void setCconnectToBroadCastOf(String name){
        connectToBroadCastOf = name;
    }
    public String getCconnectToBroadCastOf(){
        return connectToBroadCastOf;
    }
    public void setWebSocketImageSession(Session session){
        webSocketImageSession=session;
    }
    public Session getWebSocketImageSession(){
        return webSocketImageSession;
    }
    public void setWebSocketChatSession(Session session){
        webSocketChatSession=session;
    }
    public Session getWebSocketChatSession(){
        return webSocketChatSession;
    }
    public String getURL(){
        return URL;
    }
    public void setURL(String URL){
        this.URL = URL;
    }
    public int getPage(){
        return page;
    }
    public void setPage(int page){
        this.page = page;
    }
}
