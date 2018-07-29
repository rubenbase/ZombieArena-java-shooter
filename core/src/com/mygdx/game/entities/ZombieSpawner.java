package com.mygdx.game.entities;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.map.TileMap;

import java.util.Random;

/**
 * Created by ruben on 05/03/2017.
 */
public class ZombieSpawner {
    private Vector2[] sitioSpawn;
    private TileMap map;

    // Random number to chose the next spaw-spot
    private Random random;

    public ZombieSpawner(TileMap map) {
        this.map = map;
        sitioSpawn = null;

        random = new Random();
    }

    public ZombieSpawner(Vector2[] sitioSpawn) {
        this.sitioSpawn = sitioSpawn;
        map = null;

        random = new Random();
    }

    public Zombie spawn() {
        Zombie z = null;
        if (sitioSpawn != null) {
            int n = random.nextInt(sitioSpawn.length);
            z = new Zombie("Zombie.png", 8, (int) sitioSpawn[n].x, (int) sitioSpawn[n].y, 60, 60);
        }
        else if (map != null) {
            float x, y;
            do {
                x = random.nextInt(map.getI() - 2) * 60;
                y = random.nextInt(map.getJ()) * -60;
            } while (map.getTileOf(x, y) != null && map.getTileOf(x, y).isObstaculo());
            z = new Zombie("Zombie.png", 8, (int)x, (int)y, 60, 60);
        }
        return z;
    }

    public Vector2[] getSitioSpawn() {
        return sitioSpawn;
    }

    public void setSitioSpawn(Vector2[] sitioSpawn) {
        this.sitioSpawn = sitioSpawn;
    }
}