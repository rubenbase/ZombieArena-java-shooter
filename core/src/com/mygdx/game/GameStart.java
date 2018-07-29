package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.screens.PresentationScreen;
import com.mygdx.game.system.Audio;

/**
 * Created by ruben on 08/03/2017.
 */
public class GameStart extends Game {
    private PresentationScreen pantallaPresentacion;


    @Override
    public void create() {
        Audio.inicializarMusica();

        pantallaPresentacion = new PresentationScreen(this);
        setScreen(pantallaPresentacion);
    }

    @Override
    public void dispose() {
        super.dispose();
        Audio.liberarAudio();

    }
}
