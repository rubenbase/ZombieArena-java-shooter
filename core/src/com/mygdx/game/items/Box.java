package com.mygdx.game.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.Player;
import com.mygdx.game.system.ResourceHandler;

/**
 * Created by ruben on 05/03/2017.
 */

public class Box extends Entity {

    public final static int VIDA = 0;
    public final static int MUNICION = 1;
    public final static int MAX = 2;

    private int uso;

    public Box() {
        this.delta = new Vector2();
    }

    public void init(int regId, float x, float y, float width, float height, int uso) {
        System.out.println("Donde da el fallo -> "+ ResourceHandler.getRegion((regId)));
        this.setRegion(ResourceHandler.getRegion(regId));
        this.rectangulo = new Rectangle(x, y, width, height);
        this.diferencia = 0;
        updateRectangulo();
        this.setOrigin(width/2, height/2);
        this.setX(x);
        this.setY(y);
        this.uso = uso;
    }

    @Override
    public float getWidth() {
        return this.getRegionWidth();
    }

    @Override
    public float getHeight() {
        return this.getRegionHeight();
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(this.getTexture(), getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), 1, 1, this.getRotation(),
                getRegionX(), getRegionY(), getRegionWidth(), getRegionHeight(),
                false, false);
    }

    public void pillarItem(Player pl) {
        switch (uso) {
            case VIDA:
                pl.curar(10);
                break;
            case MUNICION:
                pl.anadirMunicion(430);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean update(float dTime) {
        // TODO Auto-generated method stub
        return false;
    }

}
