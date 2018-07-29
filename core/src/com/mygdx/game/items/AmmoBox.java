package com.mygdx.game.items;

import com.mygdx.game.entities.Player;

/**
 * Created by ruben on 05/03/2017.
 */
public class AmmoBox extends Item {

    public AmmoBox(int x, int y) {
        super("ammo.png", 1, x, y, 60, 60);
    }

    @Override
    public void pickUp(Player player) {
        player.anadirMunicion(400);
    }

    @Override
    public boolean update(float dTime) {
        return false;
    }

}