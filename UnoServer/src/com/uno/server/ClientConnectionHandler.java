/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uno.server;

import com.uno.common.game.GameState;
import com.uno.common.requests.JoinRequest;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sayeedm
 */
public class ClientConnectionHandler implements Runnable {
    
    private Socket s;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    
    
    public ClientConnectionHandler(Socket s) throws IOException {
        this.s = s;
        this.in = new ObjectInputStream(s.getInputStream());
        this.out = new ObjectOutputStream(s.getOutputStream());
    }
    
    @Override
    public void run() {
        while(true) {
            
            try {
                
                Object obj = in.readObject();
                
                
                if (obj instanceof JoinRequest) {
                    JoinRequest req = (JoinRequest)obj;
                    System.out.println("Player " + req.getName() + " wants to join a " + req.getDesiredPlayerCount() + " Player Game");
                    
                    Game game = ServerData.getInstance().findOrCreateGame(req.getName(), req.getDesiredPlayerCount(), in, out);
                    
                    while (game.currentState() != GameState.GAME_STARTED) {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(ClientConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    
                    break;
                    
                }
                
                
                
            } catch (IOException ex) {
                Logger.getLogger(ClientConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
                return;
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ClientConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }
    }
    
}
