package com.devtalk.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.devtalk.maze.DevMaze;
import com.devtalk.maze.Tile;

public class MainMenuScreen implements Screen {

	private DevMaze game;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	// private BitmapFont font;

	Tile[][] paths;
	Texture singleplayerImg, multiplayerImg, settingsImg, bgimage;
	Rectangle singlePlayer, multiPlayer, settings;

	public MainMenuScreen(DevMaze g) {
		this.game = g;
		this.camera = g.camera;
		this.batch = g.batch;
		// this.font = g.font;

		// Load Textures
		this.bgimage = new Texture(Gdx.files.internal("bgimage.png"));
		this.singleplayerImg = new Texture(Gdx.files.internal("SINGLE_PLAYER.png"));
		this.multiplayerImg = new Texture(Gdx.files.internal("MULTI_PLAYER.png"));
		this.settingsImg = new Texture(Gdx.files.internal("SETTINGS.png"));

		// Make Maze
		this.paths = new Tile[5][5];

		// Place the buttons
		int x = 150;
		int y = 295;
		singlePlayer = new Rectangle(x, y, 512, 64);
		y = 215;
		multiPlayer = new Rectangle(x, y, 512, 64);
		y = 135;
		settings = new Rectangle(x, y, 512, 64);

	}

	@Override
	public void dispose() {
		// TODO
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	public void render(float delta) {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1); // R,G,B,A (0.0f - 1.0f)
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		camera.position.set(camera.viewportWidth / 2,
				camera.viewportHeight / 2, 0);
		
		camera.update();
		
		Sprite sprite = new Sprite(bgimage);
		sprite.setSize(960,640);
		

		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		{
			// **DRAW SYMBOL** //
			/**
			 * for (int i = 0; i < paths.length; i++) { for (int j = 0; j <
			 * paths[0].length; j++) {
			 * 
			 * if(i == 2 || j == 2) { game.batch.draw(IN_MAZE, j *
			 * GameScreen.EDGE_SIZE_PX + (5 * GameScreen.EDGE_SIZE_PX), i *
			 * GameScreen.EDGE_SIZE_PX + (5 * GameScreen.EDGE_SIZE_PX)); }
			 * 
			 * // Wanna see the indices overlaid on the maze? Uncomment this
			 * line right here // game.font.draw(game.batch,
			 * maze.tiles[i][j].toString(), j * GameScreen.EDGE_SIZE_PX + 15, i
			 * * GameScreen.EDGE_SIZE_PX + 40); }
			 **/

			// **DRAW BUTTONS** //
			sprite.draw(batch);

			batch.draw(multiplayerImg, multiPlayer.x, multiPlayer.y);
			//font.draw(game.batch, "RESUME", resume.x + 25, resume.y + 25);

			batch.draw(settingsImg, settings.x, settings.y);
			//font.draw(game.batch, "PLAYER", player.x + 25, player.y + 25);
			
			batch.draw(singleplayerImg, singlePlayer.x, singlePlayer.y);
			//font.draw(game.batch, "NEW GAME", newGame.x + 25, newGame.y + 25);
		}
		
		batch.end();

		if (Gdx.input.justTouched()) {
			int x = Gdx.input.getX();
			int y = 480 - Gdx.input.getY(); // Translate to Camera coordinates

			if (singlePlayer.contains(x, y)) {
				game.newGame();
				game.setScreen(game.gameScreen);
			}

			/*
			 * Uncomment these once the screens are started else
			 * if(resume.contains(x, y)) game.setScreen(game.gameScreen); else
			 * if(player.contains(x, y)) { game.setScreen(new
			 * PlayerScreen(game)); } else if(settings.contains(x, y)) {
			 * game.setScreen(new SettingsScreen(game)); }
			 */

			//else if (quit.contains(x, y)) {
				//Gdx.app.exit();
			//}
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	public void show() {
		// TexturePacker2.process("img", "../DevMaze-android/assets/",
		// "starters.pak");
	}

}
