package com.mygdx.game.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

/**
 * Created by ruben on 08/03/2017.
 */
public class Audio {
    public static Music musicapresentacion;
    private static boolean inicioPausada;
    private static boolean juegoPausada;

    public static void inicializarMusica() {
        musicapresentacion = Gdx.audio.newMusic(Gdx.files.internal("musica/musicapresentacion.mp3"));

    }

    public static void liberarAudio() {
        musicapresentacion.dispose();


    }

    public static void apagarSonido() {
        musicapresentacion.setVolume(0);

    }

    public static void encenderSonido(){
        musicapresentacion.setVolume(0);

    }

    public static boolean isInicioPausada() {
        return inicioPausada;
    }


    public static void setInicioPausada(boolean inicioPausada) {
        Audio.inicioPausada = inicioPausada;
    }

    public static boolean isJuegoPausada() {
        return juegoPausada;
    }
    public static void setJuegoPausada(boolean juegoPausada) {
        Audio.juegoPausada = juegoPausada;
    }

}
