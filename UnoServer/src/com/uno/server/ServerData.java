/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uno.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 * @author
 */
public class ServerData {
    
    private static ServerData instance = null;
    private ServerData() {
    
    }
    
    public synchronized static ServerData getInstance() {
        if (instance == null)
            instance = new ServerData();
        
        return instance;
    }
    
    
    private List<Game> runningGames = new ArrayList<>();
    
    public Game findOrCreateGame(String playerName, int desiredPlayerCount, ObjectInputStream in, ObjectOutputStream out) {
        
        /*TODO: find if already joined */
        List<Game> possibleMatches = runningGames.stream().filter(game -> game.totalSlots() == desiredPlayerCount && game.isAvailable()).collect(Collectors.toList());
        
        System.out.println(possibleMatches.size() + " possible games found with player count " + desiredPlayerCount);
        
        Game bestMatch = null;
        for (Game game : possibleMatches) {
            if (game.addPlayer(playerName, in, out)) {
                System.out.println("Empty seat found for " + playerName);        
                bestMatch = game;
                break;
            } else {
                System.out.println("Ouch someone got the seat before " + playerName + " did");
            }
        }
        
        if (bestMatch == null) {
            /* we didn't find any empty seats, lets create a game and wait for other players */
            System.out.println("No games found, " + playerName + " is creating one and waiting for other players");
            bestMatch = new Game(desiredPlayerCount);
            bestMatch.addPlayer(playerName, in, out);
            runningGames.add(bestMatch);
        }
            
        return bestMatch;
    }
    
    
}
