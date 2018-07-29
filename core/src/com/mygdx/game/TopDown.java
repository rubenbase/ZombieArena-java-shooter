package com.mygdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.armory.Bullet;
import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.Zombie;
import com.mygdx.game.entities.ZombieManager;
import com.mygdx.game.items.Box;
import com.mygdx.game.items.ItemSpawner;
import com.mygdx.game.map.TileMap;
import com.mygdx.game.screens.HighscoreScreen;
import com.mygdx.game.system.HighScores;
import com.mygdx.game.system.InputTracker;
import com.mygdx.game.system.ResourceHandler;
import com.mygdx.game.system.SinCosTable;

import java.util.ArrayList;

import static com.badlogic.gdx.Application.ApplicationType.*;

public class TopDown implements Screen {
	GameStart juego;
private Viewport gamePort;
	SpriteBatch batch;
	OrthographicCamera cam;
	InputTracker input;
	BitmapFont font;

	Player player;
	ArrayList<Bullet> bullets;

	TileMap map;

	FPSLogger log;
	ZombieManager manager;

	ItemSpawner is;

	int nivel;
	private boolean sonido;

	public TopDown(GameStart juego, boolean sonido) {
		this.juego = juego;
		this.sonido = sonido;
		batch = new SpriteBatch();
		font = new BitmapFont();
		SinCosTable.init();
		ResourceHandler.initResourceHandler();

		cam = new OrthographicCamera();
		gamePort = new ScreenViewport(cam);
		//cam.setToOrtho(false);

		Zombie.animId = ResourceHandler.createAnimation("Zombie.png", 8, 0, 0, 60, 60);
		Bullet.regId = ResourceHandler.createRegion("atlas.png", 149, 25, 2, 10);
		player = new Player(ResourceHandler.createAnimation("Soldier_atlas.png", 8, 0, 0, 60, 60),
				62, -58, 60, 60);

		input = new InputTracker();

		int[][] mapSpec = {
				{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
				{1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
				{1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
				{1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
				{1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
				{1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
				{1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
				{1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
				{1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
				{1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
				{1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
				{1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
				{1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
				{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		};

		map = new TileMap(mapSpec);

		Gdx.input.setInputProcessor(input);

		manager = new ZombieManager(map);

		nivel = 1;

		is = new ItemSpawner();

		log = new FPSLogger();
	}




	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if (Gdx.app.getType() == Application.ApplicationType.Android) {
			System.out.println("mobil");


		}else{

		}

		cam.position.x = player.getX() + (player.getWidth() / 2);
		cam.position.y = player.getY() + (player.getHeight() / 2);
		cam.update();
		if (player.alive()) {
			if (manager.getContador() < nivel * 5) {
				manager.spawnear();
			}
		}

		if (manager.isVacio()) {
			nivel++;
			manager.resetContador();
			manager.empezarSpawnear();
		}

		//********************
		// Movement
		//********************
		for (int j = 0; j < manager.zombies.size(); j++) {
			if (manager.zombies.get(j).vivo()) {
				if (player.alive())
					manager.zombies.get(j).setObjetivo(player.getRectangulo().x, player.getRectangulo().y);
				else
					manager.zombies.get(j).setObjetivo(null);
				manager.zombies.get(j).update(delta);
			}
			else {
				is.spawn(manager.zombies.get(j).getX(), manager.zombies.get(j).getY());
				manager.eliminarZombie(manager.zombies.get(j));
				player.addScore(10);
			}
		}

		if (player.alive()) {
			player.update(delta);
		}



		if(player.getHp()<=0){
			HighScores.engadirPuntuacion(player.getPuntuacion());
			juego.setScreen(new HighscoreScreen(juego));

			System.out.println("Muerto");
		}
		bullets = player.getArma().getBullets();
		player.getArma().update(manager.zombies, delta);
		//*******************
		// Collision
		//*******************

		for (int j = 0; j < is.items.size(); j++) {
			if (is.items.get(j).isOverlaping(player.getRectangulo())) {
				is.items.get(j).pillarItem(player);
				is.eliminarItem(is.items.get(j));
			}
		}

		for (int j = 0; j < manager.zombies.size(); j++) {
			if (manager.zombies.get(j).vivo()) {
				map.collideBounds(manager.zombies.get(j));
			}
		}

		for (int i = 0; i < bullets.size(); i++) {
			if (bullets.get(i).chocar(map)) {
				bullets.remove(i);
			}
		}

		for (int j = 0; j < manager.zombies.size(); j++) {
			if (manager.zombies.get(j).vivo()) {
				if (manager.zombies.get(j).isOverlaping(player.getRectangulo()) && player.alive()) {
					manager.zombies.get(j).pararPerseguir();
					player.hacerDanho(1, -manager.zombies.get(j).getSin(), manager.zombies.get(j).getCos());
				}
				else {
					manager.zombies.get(j).empezarAPerseguir();
				}

				for (int i = 0; i < bullets.size(); i++) {
					if (bullets.get(i).chocar(manager.zombies.get(j).getRectangulo()))
						manager.zombies.get(j).hacerDanho(bullets.get(i).getArmShooted().getDamage(), bullets.get(i).getArmShooted().getKnockBack());
				}
			}
		}

		map.collideBounds(player);
		//*******************
		// Drawing
		//*******************
		batch.setProjectionMatrix(cam.combined);
		batch.begin();

		map.draw(batch);

		for (Box b: is.items) {
			b.draw(batch);
		}

		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).draw(batch);
		}

		if (player.isDamaged())
			batch.setColor(1.0f, 0.0f, 0.0f, 1.0f);
		if (player.alive())
			player.draw(batch);
		batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);

		for (int j = 0; j < manager.zombies.size(); j++) {
			if (manager.zombies.get(j).vivo()) {
				manager.zombies.get(j).draw(batch);
			}
		}

		font.draw(batch, "NIVEL: " + nivel, 330, 120);

		font.draw(batch, "JUGADOR:", cam.position.x - 300, cam.position.y + 230);
		font.draw(batch, "Vida " + player.getHp(), cam.position.x - 300, cam.position.y + 200);
		font.draw(batch, "Municion " + player.getMunicion(), cam.position.x - 300, cam.position.y + 170);
		font.draw(batch, "Puntuacion " + player.getPuntuacion(), cam.position.x - 300, cam.position.y + 140);

		font.draw(batch, "ARMA:", cam.position.x - 300, cam.position.y - 140);
		font.draw(batch, "Daño: " + player.getArma().getDamage(), cam.position.x - 300, cam.position.y - 170);
		font.draw(batch, "Ratio: " + player.getArma().getFireRate(), cam.position.x - 300, cam.position.y - 200);
		batch.end();

		InputTracker.updateState();
//		log.log();
	}

	@Override
	public void resize(int width, int height) {
gamePort.update(width,height);
	batch.setProjectionMatrix(cam.combined);

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {
		//batch.dispose();
		//Gdx.input.setInputProcessor(null);

		//	font.dispose();
	}
}
