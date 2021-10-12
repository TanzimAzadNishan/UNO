/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uno.client.ui;

import com.uno.client.game.GameEngine;

/**
 *
 * @author sayeedm
 */
public abstract class UnoGameBaseViewController {
    
    public abstract String getKey();
    
    public void unsubscribe() {
        GameEngine.getInstance().unsubscribe(getKey());
    }
    
}
