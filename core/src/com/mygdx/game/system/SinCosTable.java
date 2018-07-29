package com.mygdx.game.system;

/**
 * Created by ruben on 05/03/2017.
 */
public class SinCosTable {
    private static float[] cos = new float[360];
    private static float[] sin= new float[360];

    public static void init() {
        for	(int i = 0; i < 360; i++) {
            cos[i] = (float) Math.cos(Math.toRadians(i));
            sin[i] = (float) Math.sin(Math.toRadians(i));
        }
    }

    public static float getCos(int a) {
        while (a >= 360)
            a-=360;
        while (a < 0)
            a+=360;
        return cos[a];
    }

    public static float getSin(int a) {
        while (a >= 360)
            a-=360;
        while (a < 0)
            a+=360;
        return sin[a];
    }

}
