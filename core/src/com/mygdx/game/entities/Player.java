package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.armory.Weapon;
import com.mygdx.game.system.InputTracker;
import com.mygdx.game.system.ResourceHandler;
import com.mygdx.game.system.SinCosTable;

/**
 * Created by ruben on 05/03/2017.
 */
public class Player extends Entity{
    private int hp;
    private int municion;
    private Weapon arma;
    private Weapon espada;
    private Weapon pistola;
    private int puntuacion;
    private long heridoUltimaVez;
    private long velocidadDanho;

    public Player(int animId, float x, float y, int width, int height) {
        super(animId, x, y, width, height);
        this.diferencia = 4;
        this.hp = 100;
        this.municion = 400;
        pistola = new Weapon(ResourceHandler.createAnimation("Soldier_atlas.png", 1, 0, 60, 60, 60), 130, 10, 1000, 1000, 2000, false);
        espada = new Weapon(ResourceHandler.createAnimation("Soldier_atlas.png", 3, 0, 118, 60, 60), 30, 3, 50, 0, 2000, true);
        pistola.setAABB(rectangulo);
        arma = pistola;
        this.velocidad = 270;
        puntuacion = 0;
        heridoUltimaVez = 0;
        velocidadDanho = 100;
        knock = new Vector2();
        updateRectangulo();
    }

    @Override
    public boolean update(float dTime) {
        knock.x = 0;
        knock.y = 0;
        this.delta.x = 0;
        this.delta.y = 0;

        // Handle user input
        if (InputTracker.isPressed(InputTracker.DOWN)) {
            this.delta.y = -this.velocidad * dTime;
        }
        if (InputTracker.isPressed(InputTracker.UP)) {
            this.delta.y = this.velocidad * dTime;
        }
        if (InputTracker.isPressed(InputTracker.RIGHT)) {
            this.delta.x = this.velocidad * dTime;
        }
        if (InputTracker.isPressed(InputTracker.LEFT)) {
            this.delta.x = -this.velocidad * dTime;
        }

        if (InputTracker.isJustReleased(InputTracker.UP) ||
                InputTracker.isJustReleased(InputTracker.DOWN)) {
            this.delta.y = 0;
        }
        if (InputTracker.isJustReleased(InputTracker.LEFT) ||
                InputTracker.isJustReleased(InputTracker.RIGHT)) {
            this.delta.x = 0;
        }
        if (InputTracker.isJustReleased(InputTracker.SPACE)) {
            arma = arma == pistola ? espada : pistola;
        }

        // Animate sprite if moving
        if (this.delta.x != 0 || this.delta.y != 0) {
            updateAnim();
        }

        // Cast a vector from the player's position in the screen(always the center)
        // to the mouse pointer. Then, get that vector's angle and set it to the sprite's
        // rotation, so the sprite "faces" the mouse.
        float midX = Gdx.graphics.getWidth() / 2;
        float midY = Gdx.graphics.getHeight() / 2;
        float mouseX = InputTracker.getMousePos().x;
        float mouseY = Gdx.graphics.getHeight() - InputTracker.getMousePos().y;

        Vector2 dir = new Vector2(mouseX - midX, mouseY - midY);
        dir.rotate90(-1);
        this.setRotation(dir.angle());

        // Update player's position
        this.setX(this.getX() + this.delta.x);
        this.setY(this.getY() + this.delta.y);

        if (Gdx.input.isTouched()) {
            float px = this.getX() + (this.getWidth() / 2);
            float py = this.getY() + (this.getHeight() / 2);

            px -= SinCosTable.getSin((int)this.getRotation() + 20) * 23;
            py += SinCosTable.getCos((int)this.getRotation() + 20) * 23;

            if (!arma.isMeele()) {
                if (municion > 0) {
                    municion--;
                    arma.shoot(px, py, getRotation());
                }
            }
            else {
                arma.shoot(px, py, getRotation());
            }
        }

        updateRectangulo();
        espada.setAABB(rectangulo);
        return true;
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        arma.draw(batch, getX(), getY(), this.getRotation());
    }

    // Just boring getters and setters
    public int getMunicion() {
        return this.municion;
    }

    public void anadirMunicion(int amount) {
        this.municion += amount;
    }

    public int getHp() {
        return this.hp;
    }

    public void hacerDanho(int amount, float xDir, float yDir) {
        if ((System.nanoTime() - heridoUltimaVez) / 1000000 > velocidadDanho) {
            heridoUltimaVez = System.nanoTime();

            float dTime = Gdx.graphics.getDeltaTime();

            knock.x = xDir * 2000 * dTime;
            knock.y = yDir * 2000 * dTime;

            this.setX(this.getX() + knock.x);
            this.setY(this.getY() + knock.y);

            this.hp -= amount;
        }
    }

    public boolean alive() {
        return this.hp > 0;
    }

    public void addScore(int a) {
        this.puntuacion += a;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setArma(Weapon w) {
        this.arma = w;
    }

    public Weapon getArma() {
        return this.arma;
    }

    public void curar(int am) {
        this.hp += am;
    }

    public boolean isDamaged() {
        return ((System.nanoTime() - heridoUltimaVez) / 1000000 < velocidadDanho) ? true: false;
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}