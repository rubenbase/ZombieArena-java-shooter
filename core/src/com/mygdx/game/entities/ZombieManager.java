package com.mygdx.game.entities;

import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.map.TileMap;

import java.util.ArrayList;

/**
 * Created by ruben on 05/03/2017.
 */
public class ZombieManager {

    public ArrayList<Zombie> zombies;
    private Pool<Zombie> zPool;

    private long ultimoSpawneado;
    private int retrado;

    private TileMap map;
    private int contador;

    public ZombieManager(TileMap map) {
        zombies = new ArrayList<Zombie>();
        zPool = new Pool<Zombie>() {
            @Override
            public Zombie newObject() {
                return new Zombie();
            }
        };

        ultimoSpawneado = 0;
        retrado = 1500;
        this.map = map;
    }

    public boolean spawnear() {
        if ((System.nanoTime() - ultimoSpawneado) / 1000000 > retrado) {
            ultimoSpawneado = System.nanoTime();
            Zombie z = zPool.obtain();
            z.init(map);
            zombies.add(z);
            contador++;
            return true;
        }
        else {
            return false;
        }
    }

    public boolean isVacio() {
        return zombies.isEmpty();
    }

    public int getContador() {
        return contador;
    }

    public void resetContador() {
        contador = 0;
    }

    public void eliminarZombie(Zombie z) {
        zPool.free(z);
        zombies.remove(z);
    }

    public void empezarSpawnear() {
        ultimoSpawneado = 0;
        spawnear();
    }
}