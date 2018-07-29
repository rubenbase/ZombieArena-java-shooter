package com.mygdx.game.items;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.Player;

/**
 * Created by ruben on 05/03/2017.
 */
public abstract class Item extends Entity {

    public Item(String string, int i, int x, int y, int j, int k) {
        super();
    }

    public abstract void pickUp(Player player);
}
