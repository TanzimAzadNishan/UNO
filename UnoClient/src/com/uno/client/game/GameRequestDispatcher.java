/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uno.client.game;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 
 */
public class GameRequestDispatcher<T extends Serializable> {
    
    public void send(T req, ObjectOutputStream out) {
        Runnable r = new Runnable() {
            public void run() {
                try {
                    out.writeObject(req);
                    out.flush();
                } catch (IOException ex) {
                    Logger.getLogger(GameRequestDispatcher.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        
        EngineThreadPool.getInstance().getExecutor().execute(r);
    }
    
}
