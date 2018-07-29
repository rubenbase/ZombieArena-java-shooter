package com.mygdx.game.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

/**
 * Created by ruben on 05/03/2017.
 */
public final class ResourceHandler {

    private static ArrayList<String> paths;
    private static ArrayList<Texture> texs;
    private static ArrayList<TextureRegion> texRegs;
    private static ArrayList<Animation> anims;

    public static void initResourceHandler() {
        texs = new ArrayList<Texture>();
        texRegs = new ArrayList<TextureRegion>();
        anims = new ArrayList<Animation>();
        paths = new ArrayList<String>();
    }

    private static Texture getTexture(String path) {
        Texture ret = null;
        if (paths.contains(path)) {
            int index = paths.indexOf(path);
            if (index >= 0)
                ret = texs.get(index);
        }
        else {
            paths.add(path);
            texs.add(new Texture(Gdx.files.internal(path)));
            ret = texs.get(texs.size() - 1);
        }
        return ret;
    }

    public static Animation getAnimation(int index) {
        Animation ret = null;
        if (index < anims.size() && index >= 0)
            ret = anims.get(index);
        return ret;
    }

    public static int createAnimation(String path, int nFrames, int x, int y, int width, int height) {
        Animation anim;
        Texture aTex = getTexture(path);

        TextureRegion[] temp = new TextureRegion[nFrames];
        for (int i = 0; i < temp.length; i++)
        {
            temp[i] = new TextureRegion(aTex, x + i * width, y, width, height);
        }
        anim = new Animation(0.10f, temp);

        anims.add(anim);
        return anims.indexOf(anim);
    }

    public static int createRegion(String path, int x, int y, int w, int h) {
        TextureRegion reg = new TextureRegion(getTexture(path), x, y, w, h);
        texRegs.add(reg);
        return texRegs.indexOf(reg);
    }

    public static TextureRegion getRegion(int index) {
        TextureRegion t = null;
        if (index >= 0 && index < texRegs.size())
            t = texRegs.get(index);
        return t;
    }

    public static void cleanUp() {
        for (Texture t: texs) {
            t.dispose();
        }
    }

}
