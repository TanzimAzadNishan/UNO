/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uno.client.game;

import com.uno.common.game.Card;
import com.uno.common.requests.DrawCardRequest;
import com.uno.common.requests.JoinRequest;
import com.uno.common.requests.MoveRequest;
import com.uno.common.requests.PassRequest;
import com.uno.common.requests.PenaltyConsumeRequest;
import com.uno.common.requests.RelinquishRequest;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sayeedm
 */
public class UnoClient {
    
    /* TODO: read this from /etc/hosts file */
    private static final String UNO_SERVER = "172.20.53.28";
    private static final int UNO_PORT = 1234;
    
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    
    public boolean connect(Collection<UnoClientSubscriber> subscribers) {        
        try {
            socket = new Socket(UNO_SERVER, UNO_PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            new Thread(new UnoResponseHandler(in)).start();
            subscribers.forEach(UnoClientSubscriber::onConnectedToServer);
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public void join(String playerName, int desiredPlayerCount) {
        if (out != null) {
            JoinRequest request = new JoinRequest(playerName, desiredPlayerCount);
            new GameRequestDispatcher<>().send(request, out);
        }
    }
    
    public void sendMove(Card card) {
        if (out != null) {
            MoveRequest req = new MoveRequest(card);
            new GameRequestDispatcher<>().send(req, out);
        }
    }
    
    public void sendDrawCard() {
        if (out != null) 
            new GameRequestDispatcher<>().send(new DrawCardRequest(), out);
        
    }
    
    public void sendAcceptPenalty() {
        if (out != null)
            new GameRequestDispatcher<>().send(new PenaltyConsumeRequest(), out);
    }
    
    public void sendRelinquish() {
        if (out != null)
            new GameRequestDispatcher<>().send(new RelinquishRequest(), out);
    }
    
    public void sendPass() {
        if (out != null)
            new GameRequestDispatcher<>().send(new PassRequest(), out);
    }
    
    
    
    
    
    
}
