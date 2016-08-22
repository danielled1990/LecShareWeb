/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaclass;

/**
 *
 * @author Danielle
 */
public class wantToConnect {
    private String id;
    private String connectTo;
    private String type;
    
    public String GetConnectTo(){
        return connectTo;
    }
    public String GetName(){
        return id;
    }
    public void setConnectTo(String name){
        id = name;   
    }
}
