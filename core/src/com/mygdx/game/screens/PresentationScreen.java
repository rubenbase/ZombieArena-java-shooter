package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.GameStart;
import com.mygdx.game.TopDown;
import com.mygdx.game.system.Audio;

/**
 * Created by ruben on 08/03/2017.
 */
public class PresentationScreen implements Screen, InputProcessor {
    public static Texture textureSonidoOn;
    public static  Texture getTextureSonidoOff;
    Preferences prefs = Gdx.app.getPreferences("nomedearquivo");
    private Music musicapresentacion;

    private GameStart meuxogogame;
    public static boolean sonido;
ShapeRenderer shapeRenderer;
    private static Texture fondo;
    private SpriteBatch spritebatch;
    private OrthographicCamera camara2d;
    private Rectangle botones[]={new Rectangle(900, 400, 240, 60),new Rectangle(900, 290, 240, 60),new Rectangle(900, 170, 240, 60)};
    public final static Rectangle CONTRO_MUSICA = new Rectangle(260,625,64,64);


public PresentationScreen(GameStart meuxogogame) {

        this.meuxogogame=meuxogogame;
shapeRenderer = new ShapeRenderer();
        camara2d = new OrthographicCamera();

        spritebatch = new SpriteBatch();


    fondo = new Texture(Gdx.files.internal("fondopresentacion.png"));
    textureSonidoOn = new Texture(Gdx.files.internal("soundon.png"));
    getTextureSonidoOff = new Texture(Gdx.files.internal("soundoff.png"));

        Gdx.input.setInputProcessor(this);
    this.musicapresentacion = Audio.musicapresentacion;

    String dato = prefs.getString("nomeparametro1","true");
    if (dato.equals("true")){
        sonido = true;
        System.out.println("sonido on");
    }else {
        sonido = false;

        System.out.println("sonido off");

    }
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
        Vector3 temp = new Vector3(screenX,screenY,0);
        camara2d.unproject(temp);

        Circle dedo = new Circle(temp.x,temp.y,2);
        Rectangle recTemporal = new Rectangle();

        if (Intersector.overlaps(dedo, botones[0]))	{	// Pulsar Juego nuevo
           dispose();
            System.out.println("Start");
            meuxogogame.setScreen(new TopDown(meuxogogame,sonido));
            musicapresentacion.stop();
        }
        if (Intersector.overlaps(dedo, botones[1]))	{	// Pulsar Scores
           dispose();
         meuxogogame.setScreen(new HighscoreScreen(meuxogogame));
            System.out.println("Scores");
        }
        if (Intersector.overlaps(dedo, botones[2]))	{	// Pulsar Exit
            Gdx.app.exit();
            System.out.println("salir");
        }

       recTemporal.set(CONTRO_MUSICA.x,CONTRO_MUSICA.y,CONTRO_MUSICA.width,CONTRO_MUSICA.height);
        if (Intersector.overlaps(dedo, recTemporal)){
            if(sonido) {
                sonido = false;
                System.out.println("Sonido off");
              Audio.apagarSonido();
              prefs.putString("nomeparametro1","false");
             prefs.flush();

            }else{
                sonido=true;
                //Audio.encenderSonido();

                System.out.println("Sonido on");
               prefs.putString("nomeparametro1","true");
                prefs.flush();

            }
        }

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

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);

    }

    @Override
    public void render(float delta) {
        spritebatch.begin();

        spritebatch.draw(fondo,0,0, 1280,720);
        if(sonido){
            spritebatch.draw(textureSonidoOn,  CONTRO_MUSICA.x,CONTRO_MUSICA.y,CONTRO_MUSICA.width,CONTRO_MUSICA.height);

        }else{
            spritebatch.draw(getTextureSonidoOff,  CONTRO_MUSICA.x,CONTRO_MUSICA.y,CONTRO_MUSICA.width,CONTRO_MUSICA.height);

        }


        spritebatch.end();
        if(sonido) {
           // if (!Audio.musicaFin.isPlaying()) {
                musicapresentacion.setVolume(0.5f);
            musicapresentacion.play();
            musicapresentacion.setLooping(true);
            }
//        shapeRenderer.setProjectionMatrix(camara2d.combined);
//
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//        shapeRenderer.setColor(1, 1, 0, 1);
//        shapeRenderer.rect(260,625,64,64);
//        shapeRenderer.end();
    }

    @Override
    public void resize(int width, int height) {
        camara2d.setToOrtho(false, 1280, 720);
        camara2d.update();
        spritebatch.setProjectionMatrix(camara2d.combined);
        spritebatch.disableBlending();
    }

    @Override
    public void pause() {
        Gdx.input.setInputProcessor(null);

    }

    @Override
    public void resume() {
        Gdx.input.setInputProcessor(this);

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        fondo.dispose();
       spritebatch.dispose();
        shapeRenderer.dispose();
        Gdx.input.setInputProcessor(null);

    }
}
