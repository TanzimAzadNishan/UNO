/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uno.common.requests;

import java.io.Serializable;

/**
 *
 * @author
 */
public class JoinRequest implements Serializable {
    
    private String name;
    private int desiredPlayerCount;
    
    public JoinRequest() {
        
    }
    
    public JoinRequest(String name, int desiredPlayerCount) {
        this.desiredPlayerCount = desiredPlayerCount;
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getDesiredPlayerCount() {
        return desiredPlayerCount;
    }
    
    public void setDesiredPlayerCount(int desiredPlayerCount) {
        this.desiredPlayerCount = desiredPlayerCount;
    }
    
}
