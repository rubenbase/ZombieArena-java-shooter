package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.StringBuilder;
import com.mygdx.game.GameStart;
import com.mygdx.game.system.HighScores;

/**
 * Created by ruben on 08/03/2017.
 */
public class HighscoreScreen  implements Screen, InputProcessor {
    SpriteBatch batch;
    BitmapFont font;
    private StringBuilder sBuilder;
    GameStart juego;
    GlyphLayout glyphLayout;

    public HighscoreScreen(GameStart juego){
        this.juego = juego;
        HighScores.load();

        sBuilder = new StringBuilder();
        sBuilder.append(" Z O M B I E - sA R E N A: 11CMR\n\n\n" );
        for(int i=0;i<HighScores.highscores.length;i++){
            sBuilder.append("\n"+HighScores.highscores[i]);
        }

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fuentes/strangerbackinthenight.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.color = Color.YELLOW;
         parameter.size *= 1.7;
        parameter.size = (int)(15 * ((float) Gdx.graphics.getWidth()/600 ));

        font = generator.generateFont(parameter);
        generator.dispose();
        glyphLayout = new GlyphLayout();
        glyphLayout.setText(font, sBuilder);
        Gdx.input.setInputProcessor(this);
    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);

    }

    @Override
    public void render(float delta) {
        // TODO Auto-generated method stub
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        font.setColor(Color.YELLOW);
        font.draw(batch, glyphLayout,Gdx.graphics.getWidth() / 2 - glyphLayout.width / 2,Gdx.graphics.getHeight() - glyphLayout.height);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
        batch = new SpriteBatch();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);

    }

    @Override
    public void dispose() {
font.dispose();
        batch.dispose();

        Gdx.input.setInputProcessor(null);




    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
dispose();
        juego.setScreen(new PresentationScreen(juego));
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}