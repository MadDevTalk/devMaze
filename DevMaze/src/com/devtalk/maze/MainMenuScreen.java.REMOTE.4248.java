package com.devtalk.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class MainMenuScreen implements Screen {

	private DevMaze game;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private BitmapFont font;
	
	Tile[][] paths;
	Texture gameImg, resumeImg, playerImg, settingsImg, quitImg;
	Rectangle newGame, resume, player, settings, quit;
	
	public MainMenuScreen(DevMaze g) {
		this.game = g;
		this.camera = g.camera;
		this.batch = g.batch;
		this.font = g.font;
		
		// Load Textures
		this.gameImg = new Texture(Gdx.files.internal("NEW_GAME.png"));
		this.resumeImg = new Texture(Gdx.files.internal("RESUME.png"));
		this.playerImg = new Texture(Gdx.files.internal("PLAYER.png"));
		this.settingsImg = new Texture(Gdx.files.internal("SETTINGS.png"));
		this.quitImg = new Texture(Gdx.files.internal("QUIT.png"));
		
		// Make Maze
		this.paths = new Tile[5][5];
		
		int x, y;
		
		// Place the buttons
		x = 800 - 100 - 128;
		y = 75;
		newGame = new Rectangle(x, y, 192, 64);
		y = 155;
		resume = new Rectangle(x, y, 192, 64);
		y = 235;
		settings = new Rectangle(x, y, 192, 64);
		y = 315;
		player = new Rectangle(x, y, 192, 64);
		y = 395;
		quit = new Rectangle(x, y, 192, 64);

	}

	public void render(float delta) {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);   // R,G,B,A (0.0f - 1.0f)
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		camera.update();
		
		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);

		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();
		{
			// **DRAW SYMBOL** //
			/**
			for (int i = 0; i < paths.length; i++) {
				for (int j = 0; j < paths[0].length; j++) {
					
					if(i == 2 || j == 2) {
						game.batch.draw(IN_MAZE, j * GameScreen.EDGE_SIZE_PX + (5 * GameScreen.EDGE_SIZE_PX), 
								i * GameScreen.EDGE_SIZE_PX + (5 * GameScreen.EDGE_SIZE_PX));
					}
					
					// Wanna see the indices overlaid on the maze? Uncomment this line right here
					// game.font.draw(game.batch, maze.tiles[i][j].toString(), j * GameScreen.EDGE_SIZE_PX + 15, i * GameScreen.EDGE_SIZE_PX + 40);
				}
			}
			**/
			
			// **DRAW BUTTONS** //
			batch.draw(gameImg, newGame.x, newGame.y);
			font.draw(game.batch, "NEW GAME", newGame.x + 25, newGame.y + 25);
			
			batch.draw(resumeImg, resume.x, resume.y);
			font.draw(game.batch, "RESUME", resume.x + 25, resume.y + 25);
			
			batch.draw(playerImg, player.x, player.y);
			font.draw(game.batch, "PLAYER", player.x + 25, player.y + 25);
			
			batch.draw(settingsImg, settings.x, settings.y);
			font.draw(game.batch, "SETTINGS", settings.x + 25, settings.y + 25);
			
			batch.draw(quitImg, quit.x, quit.y);
			font.draw(game.batch, "QUIT", quit.x + 25, quit.y + 25);
		}
		batch.end();

		if (Gdx.input.justTouched()) {
			int x = Gdx.input.getX();
			int y = 480 - Gdx.input.getY();   // Translate to Camera coordinates
			
			if(newGame.contains(x, y)) {
				game.newGame();
				game.setScreen(game.gameScreen);
			} else if(resume.contains(x, y))
				game.setScreen(game.gameScreen);
			
/* Uncomment these once the screens are started
*
*			else if(player.contains(x, y)) { game.setScreen(new PlayerScreen(game)); }
*			else if(settings.contains(x, y)) { game.setScreen(new SettingsScreen(game)); }
*			else if(quit.contains(x, y)) { game.setScreen(new QuitScreen(game)); }
*/
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	public void show() {
		// TexturePacker2.process("img", "../DevMaze-android/assets/", "starters.pak");
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO
	}

}
