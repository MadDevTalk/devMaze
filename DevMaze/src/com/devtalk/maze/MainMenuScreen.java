package com.devtalk.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class MainMenuScreen implements Screen {

	final DevMaze game;
	GameScreen gamestate;
	OrthographicCamera camera;
	Texture gameImg, resumeImg, playerImg, settingsImg, quitImg;
	Rectangle newGame, resume, player, settings, quit;
	int i, x, y;

	public MainMenuScreen(final DevMaze game, GameScreen gamestate) {
		this.game = game;
		if(gamestate != null) {
			this.gamestate = gamestate;
		}
		i = 0;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		
		// Load Textures
		gameImg = new Texture(Gdx.files.internal("NEW_GAME.png"));
		resumeImg = new Texture(Gdx.files.internal("RESUME.png"));
		playerImg = new Texture(Gdx.files.internal("PLAYER.png"));
		settingsImg = new Texture(Gdx.files.internal("SETTINGS.png"));
		quitImg = new Texture(Gdx.files.internal("QUIT.png"));
		
		
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

		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		game.font.draw(game.batch, "Welcome to DevMaze! ", 100, 150);
		
		// **DRAW BUTTONS** //
		game.batch.draw(gameImg, newGame.x, newGame.y);   // Double draw is a hack 
		game.batch.draw(gameImg, newGame.x, newGame.y);   // around the ^2 rule
		game.font.draw(game.batch, "NEW GAME", newGame.x + 25, newGame.y + 25);
		
		game.batch.draw(resumeImg, resume.x, resume.y);   // Double draw is a hack 
		game.batch.draw(resumeImg, resume.x, resume.y);   // around the ^2 rule
		game.font.draw(game.batch, "RESUME", resume.x + 25, resume.y + 25);
		
		game.batch.draw(playerImg, player.x, player.y);   // Double draw is a hack 
		game.batch.draw(playerImg, player.x, player.y);   // around the ^2 rule
		game.font.draw(game.batch, "PLAYER", player.x + 25, player.y + 25);
		
		game.batch.draw(settingsImg, settings.x, settings.y);   // Double draw is a hack 
		game.batch.draw(settingsImg, settings.x, settings.y);   // around the ^2 rule
		game.font.draw(game.batch, "SETTINGS", settings.x + 25, settings.y + 25);
		
		game.batch.draw(quitImg, quit.x, quit.y);   // Double draw is a hack 
		game.batch.draw(quitImg, quit.x, quit.y);   // around the ^2 rule
		game.font.draw(game.batch, "QUIT", quit.x + 25, quit.y + 25);

		game.batch.end();

		if (Gdx.input.justTouched()) {
			x = Gdx.input.getX();
			y = 480 - Gdx.input.getY();   // Translate to Camera coordinates
			
			if(newGame.contains(x, y)) { game.setScreen(new GameScreen(game)); }
			else if(resume.contains(x, y)) { 
				if(gamestate != null) {
				game.setScreen(gamestate);
				}
			}
			
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

	}

}
