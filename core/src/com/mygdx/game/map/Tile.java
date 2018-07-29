package com.mygdx.game.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.system.ResourceHandler;

/**
 * Created by ruben on 05/03/2017.
 */
public class Tile extends Entity {
    private boolean isObstaculo;

    public Tile(int regId, float dx, float dy, int width, int height, boolean isObstaculo) {
        this.isObstaculo = isObstaculo;
        this.diferencia = 0;

        this.setRegion(ResourceHandler.getRegion(regId));

        this.rectangulo = new Rectangle(dx, dy, width, height);
        this.setX(dx);
        this.setY(dy);
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
    public boolean update(float dTime) {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(this.getTexture(), this.getX(), this.getY(), this.getWidth(), this.getHeight(), this.getRegionX(), this.getRegionY(), this.getRegionWidth(), this.getRegionHeight(), false, false);
    }

    @Override
    public boolean chocar(Rectangle ext) {
        boolean collided = this.isOverlaping(ext);

        return collided;
    }

    public boolean isObstaculo() {
        return this.isObstaculo;
    }
}
