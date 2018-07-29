package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.map.TileMap;
import com.mygdx.game.system.SinCosTable;

import java.util.Random;

/**
 * Created by ruben on 05/03/2017.
 */
public class Zombie extends Entity{
    private int danho;
    private Vector2 objetivo;
    private long heridoUltimaVez;

    private float cos;
    private float sin;

    float hp;
    public static int animId;

    private TileMap map;
    private boolean perseguirObjetivo;

    public Zombie() {
        super(animId, 0, 0, 60, 60);
        knock = new Vector2();
    }

    public Zombie(String s,int animId, int x, int y, int width, int height) {
        super(animId, x, y, width, height);
        this.diferencia = 4;
        updateRectangulo();
        this.objetivo = new Vector2(0, 0);
        this.danho = 1;
        this.velocidad = 150;
        this.hp = 100;
        this.heridoUltimaVez = 0;
        perseguirObjetivo = true;
        knock = new Vector2();
    }

    public void init(TileMap map) {
        this.diferencia = 4;
        updateRectangulo();
        this.map = map;
        this.objetivo = new Vector2(0, 0);
        this.danho = 1;
        this.velocidad = 150;
        this.hp = 100;
        this.heridoUltimaVez = 0;
        float x, y;
        Random ran = new Random();
        do {
            x = ran.nextInt(map.getJ() - 2) * 60;
            y = ran.nextInt(map.getI() - 2) * -60;
        } while (map.getTileOf(x, y) == null || map.getTileOf(x, y).isObstaculo());
        this.setX(x);
        this.setY(y);
    }

    @Override
    public boolean update(float dTime) {
        this.delta.x = this.delta.y = 0;
        knock.x = 0;
        knock.y = 0;

        if (this.hp <= 0)
            return false;

        if (objetivo != null && perseguirObjetivo) {

            Vector2 dir = new Vector2(objetivo.x - this.getX(), objetivo.y - this.getY());
            dir.rotate90(-1);
            this.setRotation(dir.angle());

            sin = SinCosTable.getSin((int)this.getRotation());
            cos = SinCosTable.getCos((int)this.getRotation());

            delta.x = -sin * velocidad * dTime;
            delta.y = cos * velocidad * dTime;

            this.setX(this.getX() + this.delta.x);
            this.setY(this.getY() + this.delta.y);
        }

        if(this.delta.x != 0 || this.delta.y != 0)
            updateAnim();

        updateRectangulo();
        return true;
    }

    public void setObjetivo(float x, float y) {
        if (map != null) {
            this.objetivo.x = x;
            this.objetivo.y = y;
        }
    }

    public void setObjetivo(Vector2 obj) {
        if (obj != null)
            this.setObjetivo(obj.x, obj.y);
        else
            this.objetivo = obj;
    }

    public void setMap(TileMap m) {
        map = m;
    }

    public void hacerDanho(int d, int knockBack) {
        // Every 200 ms the zombie will be pushed back by the hacerDanho(it will be moved back in the current angle)
        float dTime = Gdx.graphics.getDeltaTime();
        if ((System.nanoTime() - heridoUltimaVez) / 1000000 > 200)
        {
            heridoUltimaVez = System.nanoTime();

            knock.x = sin * dTime * knockBack;
            knock.y = -cos * dTime * knockBack;

            this.setX(this.getX() + knock.x);
            this.setY(this.getY() + knock.y);
        }
        this.hp -= d;
    }

    public int getDanho() {
        return this.danho;
    }

    public boolean vivo() {
        return this.hp > 0;
    }

    public float getCos() {
        return cos;
    }

    public float getSin() {
        return sin;
    }

    public void pararPerseguir() {
        perseguirObjetivo = false;
    }
    public void empezarAPerseguir() {
        perseguirObjetivo = true;
    }
}