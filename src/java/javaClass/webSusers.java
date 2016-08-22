/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaclass;

import java.util.ArrayList;
import java.util.HashMap;
import javax.websocket.Session;

/**
 *
 * @author Danielle
 */
public class webSusers {
    private final static HashMap<String, ArrayList<BroadCastUsers>> broadcastSessions = new HashMap<>();//users on the video/imges site
    private final static HashMap<String, Session> allusers = new HashMap<>();
    private final static HashMap<String, SessionUtil> browserusers = new HashMap<>();//global session util per browser user
    
    private static webSusers instance = null;
    
    protected webSusers(){}
    
    public static webSusers getInstance() {
      if(instance == null) {
         instance = new webSusers();
      }
      return instance;
     }
    
    public void setStreamUser(String name){//new stream user
        ArrayList<BroadCastUsers> userOfStream = broadcastSessions.get(name);
        if(userOfStream==null){//new stream user
            userOfStream = new ArrayList<>();
            broadcastSessions.put(name, userOfStream);
            
        }
    }
    public void addSessionToStreamImageUser(String name,BroadCastUsers session){
        ArrayList<BroadCastUsers> userOfStream = broadcastSessions.get(name);
        userOfStream.add(session);    
    }
    public void insertNewUser(String name,Session session){
        Session ses = allusers.get(name);
        if(ses==null){
            allusers.put(name, session);
        }
    }
    public void removeUser(String name,Session session){
        Session ses = allusers.get(name);
        if(ses!=null){
            allusers.remove(name);
        }
    }
    public Session getSessionOfUser(String name){
        return allusers.get(name);   
    }
    public void addSession(String name){
         SessionUtil userSession = browserusers.get(name);
        if(userSession==null){//new browser user session
            userSession = new SessionUtil();
            browserusers.put(name, userSession);
            
        }
    }
    public SessionUtil geBrowserSessionOfUser(String name){
        return browserusers.get(name);
    }

    /*public void addSession(String nameToConnectTo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/
}
