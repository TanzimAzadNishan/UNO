/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uno.client.ui.custom;

import com.uno.common.game.Card;

/**
 *
 * @author
 */
public interface CardPickListener {
    void onCardPicked(Card card, CardView view, int index);
}
