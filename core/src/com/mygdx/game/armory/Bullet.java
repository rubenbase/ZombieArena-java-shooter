package com.mygdx.game.armory;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.map.Tile;
import com.mygdx.game.map.TileMap;
import com.mygdx.game.system.ResourceHandler;

/**
 * Created by ruben on 05/03/2017.
 */
public class Bullet extends Entity{
    private Weapon arm;
    private float currentDistance;
    private boolean collided;

    public static int regId;

    public Bullet() {
        this.setRegion(ResourceHandler.getRegion(regId));

        this.setOrigin(2 / 2, 10 / 2);

        this.setX(0);
        this.setY(0);

        this.rectangulo = new Rectangle();
        updateRectangulo();


        this.delta = new Vector2(0, 0);

        this.velocidad = 0;
    }

    public void init(float x, float y, Weapon arm, float rotation) {
        this.diferencia = 0;
        this.setX(x);
        this.setY(y);

        updateRectangulo();

        this.updateRectangulo();

        this.arm = arm;
        this.setRotation(rotation);
        this.currentDistance = 0;
        this.collided = false;
        this.currentDistance = 0;
        this.collided = false;
    }

    @Override
    public boolean update(float dTime) {
        this.delta.x = 0;
        this.delta.y = 0;

        delta.x = -(float) Math.sin(Math.toRadians(this.getRotation())) * arm.getBullSpeed() * dTime;
        delta.y = (float) Math.cos(Math.toRadians(this.getRotation())) * arm.getBullSpeed() * dTime;

        currentDistance += delta.x + delta.y;

        if (currentDistance > this.arm.getRange() || this.collided) {
            return false;
        }

        this.setX(this.getX() + delta.x);
        this.setY(this.getY() + delta.y);

        this.rectangulo.x = this.getX();
        this.rectangulo.y = this.getY();

        return true;
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

    @Override
    public boolean chocar(Rectangle ext) {
        boolean coll = false;
        if (isOverlaping(ext)) {
            coll = true;
            this.collided = true;
        }

        return coll;
    }

    @Override
    public boolean chocar(TileMap map) {
        boolean coll = false;
        for (int i = 0; i < map.getI(); i++) {
            for (int j = 0; j < map.getJ(); j++) {
                Tile tile = map.getTile(i, j);
                if (tile != null) {
                    if (this.isOverlaping(tile.getRectangulo()) && tile.isObstaculo()) {
                        coll = true;
                        collided = true;
                    }
                }
            }
        }

        return coll;
    }

    public Weapon getArmShooted() {
        return this.arm;
    }

    public void setWeapon(Weapon wp) {
        this.arm = wp;
    }

    @Override
    protected void updateRectangulo() {
        this.rectangulo.x = this.getX();
        this.rectangulo.y = this.getY();
        this.rectangulo.width = 7;
        this.rectangulo.height = 7;
    }
}