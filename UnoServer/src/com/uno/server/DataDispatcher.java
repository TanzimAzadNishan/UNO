/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uno.server;

import com.uno.common.game.GameStatus;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sayeedm
 */
public class DataDispatcher<T extends Serializable> {
    public void send(T res, ObjectOutputStream out) {
        Runnable r = new Runnable() {
            public void run() {
                try {
                    synchronized(out) {
                        out.writeObject(res);
                        out.flush();
                        out.reset();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(DataDispatcher.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        
        ServerThreadPools.getInstance().getResponseExecutor().execute(r);
    }
}


