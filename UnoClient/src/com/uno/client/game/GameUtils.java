/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uno.client.game;

import com.uno.common.game.PlayerStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author
 */
public class GameUtils {
    
    public static List<PlayerStatus> findNewPlayers(List<PlayerStatus> oldList, List<PlayerStatus> newList) {
        System.out.println("old : " + oldList.stream().map(p -> p.getName() + " (" + p.getId() + ")").collect(Collectors.joining(", ")));
        System.out.println("new : " + newList.stream().map(p -> p.getName() + " (" + p.getId() + ")").collect(Collectors.joining(", ")));
        List<PlayerStatus> newPlayers = new ArrayList<>();
        int diff = newList.size() - oldList.size();
        if (diff > 0) {
            for (int i = oldList.size(); i < newList.size(); i++) {
                newPlayers.add(newList.get(i));
            }
        }
        
        return newPlayers;
    }
    
}
