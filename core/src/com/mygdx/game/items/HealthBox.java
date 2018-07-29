package com.mygdx.game.items;

import com.mygdx.game.entities.Player;

/**
 * Created by ruben on 05/03/2017.
 */
public class HealthBox extends Item  {

    public HealthBox(int x, int y) {
        super("health.png", 1, x, y, 60, 60);
    }

    @Override
    public void pickUp(Player player) {
        player.curar(50);
    }

    @Override
    public boolean update(float dTime) {
        return false;
    }

}
