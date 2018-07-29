package com.mygdx.game.items;

import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.system.ResourceHandler;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ruben on 05/03/2017.
 */
public class ItemSpawner {

    private Random random;

    public ArrayList<Box> items;

    private Pool<Box> bPool;

    private long ultimaSpawneada;
    private int retraso;

    static int idMunicion ;
    static int idVida ;

    public ItemSpawner() {

        idMunicion = ResourceHandler.createRegion("atlas.png", 60, 60, 60, 60);
       idVida = ResourceHandler.createRegion("atlas.png", 0, 60, 60, 60);
        items = new ArrayList<Box>();

        bPool = new Pool<Box>() {
            @Override
            public Box newObject() {
                return new Box();
            }
        };

        ultimaSpawneada = 0;
        retraso = 0;
        random = new Random();
    }

    public boolean spawn(float x, float y) {
        if ((System.nanoTime() - ultimaSpawneada) / 1000 > retraso) {
            ultimaSpawneada = System.nanoTime();

            int op = random.nextInt(Box.MAX + 3);

            Box box = bPool.obtain();
            int id;

            if (op == Box.MUNICION)
                id = idMunicion;
            else if (op == Box.VIDA)
                id = idVida;
            else {
                return false;
            }

            box.init(id, x, y, 60, 60, op);
            items.add(box);
            return true;
        }
        else {
            return false;
        }
    }

    public void eliminarItem(Box b) {
        bPool.free(b);
       items.remove(b);

    }
}
