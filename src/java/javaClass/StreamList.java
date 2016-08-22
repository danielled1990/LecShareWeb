/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaclass;

import java.util.ArrayList;

/**
 *
 * @author Danielle
 */
public class StreamList {
    private String type;
    
    private ArrayList<String> streamersName = new ArrayList<>();
    
    public void AddToArrayList(String name){
        streamersName.add(name);
    }
    public void RemoveFromArrayList(String name){
        streamersName.remove(name);
    }
    public void SetType(String type){
        this.type = type;
    }

    
}
