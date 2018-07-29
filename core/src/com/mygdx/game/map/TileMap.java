package com.mygdx.game.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.system.ResourceHandler;

import java.util.ArrayList;

/**
 * Created by ruben on 05/03/2017.
 */
public class TileMap {
    private Tile[][] map;

    public TileMap(int i, int j) {
        map = new Tile[i][j];
    }

    public TileMap(int[][] mapSpec) {
        int grass = ResourceHandler.createRegion("atlas.png", 0, 0, 60, 60);
        int wall = ResourceHandler.createRegion("atlas.png", 60, 0, 60, 60);
        map = new Tile[mapSpec.length][mapSpec[0].length];
        for (int i = 0; i < mapSpec.length; i++) {
            for (int j = 0; j < mapSpec[i].length; j++) {
                if (mapSpec[i][j] == 1) {
                    map[i][j] = new Tile(wall, 60 * j, -(60 * i), 60, 60, true);
                }
                else if (mapSpec[i][j] == 2) {
                    map[i][j] = new Tile(grass, 60 * j, -(60 * i), 60, 60, false);
                }
            }
        }
    }

    public void collideBounds(Entity ent) {
        ent.chocar(this);
    }

    public void draw(SpriteBatch batch) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] != null)
                    map[i][j].draw(batch);
            }
        }
    }

    public Tile getTile(int i, int j) {
        if ( i < 0 || i >= map.length || j < 0 || j > map[0].length)
            return null;
        else
            return map[i][j];
    }

    public void setTile(int i, int j, Tile t) {
        if (i < map.length && j < map[0].length)
            map[i][j] = t;
    }

    public int getI() {
        return map.length;
    }

    public int getJ() {
        return map[0].length;
    }

    public Tile getTileOf(float x, float y) {
        Tile tile = null;
        x++;
        y++;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                Tile t = this.getTile(i, j);
                if (t != null) {
                    if (x >= t.getX() && x <= t.getX() + t.getWidth() && y >= t.getY() && y <= t.getY() + t.getHeight()) {
                        tile = t;
                    }
                }
            }
        }
        return tile;
    }

    public Tile[] getPath(Tile a, Tile b) {
        ArrayList<Tile> open = new ArrayList<Tile>();
        ArrayList<Tile> closed = new ArrayList<Tile>();

        closed.add(a);

        while (a != b) {
            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[0].length; j++) {
                    Tile t = this.getTile(i, j);
                    if (t != null) {
                        if (!closed.contains(t) && !t.isObstaculo()) {
                            if ((t.getX() == a.getX()) && (t.getY() == a.getY() + 60 || t.getY() == a.getY() - 60)) {
                                open.add(t);
                            }
                            if ((t.getY() == a.getY()) && (t.getX() == a.getX() + 60 || t.getX() == a.getX() - 60)) {
                                open.add(t);
                            }
//							if ((t.getX() == a.getX() + 60 || t.getX() == a.getX() - 60) && (t.getY() == a.getY() + 60 || t.getY() == a.getY() - 60)) {
//								open.add(t);
//							}
                        }
                    }
                }
            }

            float lowestF = 0;
            Tile lowestTile = null;

            for (int c = 0; c < open.size(); c++) {
                float h = Math.abs((b.getX() - open.get(c).getX())) + Math.abs((b.getY() - open.get(c).getY()));
                float g = Math.abs((open.get(c).getX() - a.getX())) + Math.abs((open.get(c).getY() - a.getY()));

                float f = h + g;
                if (c == 0) {
                    lowestF = f;
                    lowestTile = open.get(c);
                }

                else if (f < lowestF) {
                    lowestF = f;
                    lowestTile = open.get(c);
                }
            }

            open.remove(lowestTile);
            closed.add(lowestTile);

            a = lowestTile;
        }

        Tile[] path = new Tile[closed.size()];
        for (int c = 0; c < path.length; c++) {
            path[c] = closed.get(c);
        }

        return path;
    }
}
