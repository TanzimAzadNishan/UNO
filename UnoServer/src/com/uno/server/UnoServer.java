/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uno.server;


import com.uno.common.game.Card;
import com.uno.common.game.Color;
import com.uno.common.game.UnoUtils;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 
 */
public class UnoServer {
    
    private static final int SERVER_PORT = 1234;
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try {            
            ServerSocket ss = new ServerSocket(SERVER_PORT);
            
            while (true) {
                Socket s = ss.accept();
                System.out.println("Incoming Connection from " + s);
                new Thread(new ClientConnectionHandler(s)).start();
            }
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
    
}
