package com.mygdx.game.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.util.Arrays;

/**
 * Created by ruben on 08/03/2017.
 */
public class HighScores {
    public static java.lang.String[] highscores = { "0", "0", "0" };
    public static java.lang.String archivoHighscores = "highscores.dat";

    public static void load() {
        FileHandle arquivo = Gdx.files.external(HighScores.archivoHighscores);
        java.lang.String texto;
        if (!arquivo.exists()) {
            HighScores.save();
        } else {
            texto = arquivo.readString();
            highscores = texto.split(",");
        }
    }

    public static void engadirPuntuacion(int puntuacion) {
        boolean encontrado = false;
        int i = 0;

        java.lang.String[] aux = Arrays.copyOf(highscores, highscores.length);
        while ((i < HighScores.highscores.length) && (!encontrado)) {
            if (puntuacion > Integer.parseInt(HighScores.highscores[i])) {
                highscores[i] = java.lang.String.valueOf(puntuacion);
                encontrado = true;
                i++;
            } else {
                i++;
            }
        }
        if (encontrado) {
            i--;
            while (i + 1 < HighScores.highscores.length) {
                highscores[i + 1] = aux[i];
                i++;
            }
            save();
        }

    }
    private static void save(){
        FileHandle arquivo = Gdx.files.external(HighScores.archivoHighscores);
        arquivo.writeString(HighScores.highscores[0] + ",", false);
        arquivo.writeString(HighScores.highscores[1] + ",", true);
        arquivo.writeString(HighScores.highscores[2] , true);

    }
}
