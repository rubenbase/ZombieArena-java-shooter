package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.map.Tile;
import com.mygdx.game.map.TileMap;
import com.mygdx.game.system.ResourceHandler;

/**
 * Created by ruben on 05/03/2017.
 */
abstract public class Entity extends Sprite implements Disposable{
    private Animation animacion;
    private float tiempoEstado;

    protected float diferencia = 4;

    protected Rectangle rectangulo;
    protected Vector2 delta;
    protected float velocidad;
    protected Vector2 knock;

    public Entity() {

    }

    public Entity(int animId, float x, float y, int width, int height) {
        this.animacion = ResourceHandler.getAnimation(animId);

        this.setOrigin(width / 2, height / 2);

        this.setX(x);
        this.setY(y);

        this.rectangulo = new Rectangle();
updateRectangulo();

        this.tiempoEstado = 0f;

        this.delta = new Vector2(0, 0);

        this.velocidad = 0;
    }

    public void updateAnim() {
        this.tiempoEstado += Gdx.graphics.getDeltaTime();
    }

    @Override
    public float getWidth() {
        return ((TextureRegion)this.animacion.getKeyFrame(this.tiempoEstado)).getRegionWidth();
    }

    @Override
    public float getHeight() {
        return ((TextureRegion)this.animacion.getKeyFrame(this.tiempoEstado)).getRegionHeight();
    }

    public Rectangle getRectangulo() {
        return this.rectangulo;
    }

    public abstract boolean update(float dTime);

    public void draw(SpriteBatch batch) {
        batch.draw(this.getFrameActual(), this.getX(), this.getY(),
                this.getOriginX(), this.getOriginY(), this.getWidth(), this.getHeight(),
                1f, 1f, this.getRotation());
    }

    public boolean chocar(Rectangle ext) {
        boolean collided = false;

        updateRectangulo();

        if (isOverlaping(ext)) {
            collided = true;

            this.setX(this.getX() - this.delta.x - this.knock.x);
            this.setY(this.getY() - this.delta.y - this.knock.y);
            updateRectangulo();

            float xBefore = rectangulo.x;
            float yBefore = rectangulo.y;

            this.setX(this.getX() + this.delta.x + knock.x);
            updateRectangulo();
            if (isOverlaping(ext)) {
                if (xBefore > ext.x) {
                    this.setX(ext.x + ext.width - this.getDiferencia());
                }
                if (xBefore < ext.x) {
                    this.setX(ext.x - rectangulo.width - this.getDiferencia());
                }
                updateRectangulo();
            }


            this.setY(this.getY() + this.delta.y + knock.y);
            updateRectangulo();
            if (isOverlaping(ext)) {
                if (yBefore < ext.y) {
                    this.setY(ext.y - rectangulo.height - this.getDiferencia());
                }
                if (yBefore > ext.y) {
                    this.setY(ext.y + ext.height - this.getDiferencia());
                }
                updateRectangulo();
            }
        }

        return collided;
    }

    public boolean chocar(TileMap map) {
        boolean collided = false;

        this.setX(this.getX() - this.delta.x - this.knock.x);
        this.setY(this.getY() - this.delta.y - this.knock.y);

        updateRectangulo();
        float xBefore = rectangulo.x;
        float yBefore = rectangulo.y;

        this.setX(this.getX() + this.delta.x + this.knock.x);
        updateRectangulo();
        for (int i = 0; i < map.getI(); i++) {
            for (int j = 0; j < map.getJ(); j++) {
                Tile tile = map.getTile(i, j);
                if (tile != null) {
                    if (this.isOverlaping(tile.getRectangulo()) && tile.isObstaculo()) {
                        collided = true;
                        if (xBefore > tile.getX()) {
                            this.setX(tile.getRectangulo().x + tile.getRectangulo().width - this.getDiferencia());
                        }
                        else if (xBefore < tile.getX()) {
                            this.setX(tile.getRectangulo().x - this.rectangulo.width - this.getDiferencia());
                        }
                        updateRectangulo();
                    }
                }
            }
        }

        this.setY(this.getY() + this.delta.y + knock.y);
        updateRectangulo();
        for (int i = 0; i < map.getI(); i++) {
            for (int j = 0; j < map.getJ(); j++) {
                Tile tile = map.getTile(i, j);
                if (tile != null) {
                    if (this.isOverlaping(tile.getRectangulo()) && tile.isObstaculo()) {
                        collided = true;
                        if (yBefore > tile.getY()) {
                            this.setY(tile.getRectangulo().y + tile.getRectangulo().height - this.getDiferencia());
                        }
                        else if (yBefore < tile.getY()) {
                            this.setY(tile.getRectangulo().y - this.rectangulo.height - this.getDiferencia());
                        }
                        updateRectangulo();
                    }
                }
            }
        }

        return collided;
    }

    public boolean isOverlaping(Rectangle ext) {
        boolean colliding = true;
        updateRectangulo();
        if ((int) rectangulo.y + rectangulo.height <= ext.y) {
            colliding = false;
        }
        if ((int) rectangulo.y >= ext.y + ext.height) {
            colliding = false;
        }
        if ((int) rectangulo.x + rectangulo.width <= ext.x) {
            colliding = false;
        }
        if ((int) rectangulo.x >= (ext.x + ext.width)) {
            colliding = false;
        }

        return colliding;
    }

    public TextureRegion getFrameActual() {
        return (TextureRegion) this.animacion.getKeyFrame(this.tiempoEstado, true);
    }

    protected void updateRectangulo() {
        this.rectangulo.x = this.getX() + this.diferencia;
        this.rectangulo.y = this.getY() + this.diferencia;
        this.rectangulo.width = this.getWidth() - this.diferencia * 2;
        this.rectangulo.height = this.getWidth() - this.diferencia * 2;
    }

    public float getDiferencia() {
        return this.diferencia;
    }

    @Override
    public void dispose() {
        this.getTexture().dispose();
    }

    public void setAnimacion(Animation anim) {
        this.animacion = anim;
    }

    public Animation getAnimacion() {
        return this.animacion;
    }
}
