/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaclass;

import java.util.HashMap;
import javax.websocket.Session;

/**
 *
 * @author Danielle
 */
public class allUsers {
    private final static HashMap<String, Session> sockets = new HashMap<>();
    
    public HashMap<String, Session>  getAllUsers(){
        return sockets;
    }
    public void insertUser(Session session,String name){
        sockets.put(name, session);
    }
    
}
