package com.devTalk.devMaze.maze;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.devTalk.devMaze.actors.ItemHandler;
import com.devTalk.devMaze.actors.ActorHandler;
import com.devTalk.devMaze.actors.Player;
import com.devTalk.devMaze.gui.GameScreen;
import com.devTalk.devMaze.gui.LevelFinishScreen;
import com.devTalk.devMaze.gui.MainMenuScreen;
import com.devTalk.devMaze.maze.Level.LEVEL;

public class DevMaze extends Game {

	public static final boolean DEBUG = true;

	public static final int EDGE_SIZE_PX = 128;      /* The square size of maze tiles must be power of 2 */
	public static final int PLAYER_SIZE_PX = 64;     /* The square size of the player, must be  power of 2 */
	public static final int MONSTER_SIZE_PX = 64;    /* The square size of the monster, must be power of 2 */
	public static final int KEY_VEL_PxPer60S = 6;    /* DEPRECATED: The speed in pixels of the player */
	public static final int SPEED_LATCH_PX = 32;     /* DEPRECATED: Fastest speed when dragging */
                                                     
	public SpriteBatch batch;                        /* Canvas to draw/display game elements */
	public BitmapFont font;                          /* Canvas to draw/display debug text */
	public OrthographicCamera camera;                /* Our view of the SpriteBatch */
	public ShapeRenderer shapeRenderer;              /* Renders shapes */
		                                                    
	public Maze maze;                                /* The current maze tileset */
	public Player player;                            /* The player object */
	public ActorHandler monsterHandler;              /* Monster Controller object */
	public ItemHandler itemHandler;                  /* Item Controller object */
		                                                    
	public MainMenuScreen mainMenuScreen;            /* Main Menu Screen */
	public GameScreen gameScreen;                    /* Gameplay screen */
	public LevelFinishScreen levelFinishScreen;      /* TODO: Level information screen */
		                                             
	public List<Level> levels;                       /* List of available levels TODO: expand */
	public Level currentLevel;                       /* Our current level being played */
		                                             
	public boolean pause;                            /* Is the game paused */

	public void create() {
		// Create libGDX objects
		batch = new SpriteBatch();
		font = new BitmapFont();
		shapeRenderer = new ShapeRenderer();

		// Create Camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		// Create game objects
		maze = new Maze(this);
		player = new Player(this);
		monsterHandler = new ActorHandler(this);
		itemHandler = new ItemHandler(this);
		levels = new ArrayList<Level>();

		// Create screens
		mainMenuScreen = new MainMenuScreen(this);
		gameScreen = new GameScreen(this);
		levelFinishScreen = new LevelFinishScreen(this);

		// Start at menu
		setScreen(mainMenuScreen);
		pause = false;
	}

	public void dispose() {
		// Dispose LibGDX stuff
		batch.dispose();
		font.dispose();

		// Dispose game objects
		maze.dispose();
		player.dispose();
		monsterHandler.dispose();
		itemHandler.dispose();

		// Dispose Screens
		mainMenuScreen.dispose();
		gameScreen.dispose();
	}


	public void newGame() {
		// Reset levels
		levels.clear();
		levels.add(new Level(LEVEL.LEVEL_1));
		levels.add(new Level(LEVEL.LEVEL_2));
		levels.add(new Level(LEVEL.LEVEL_3));
		levels.add(new Level(LEVEL.LEVEL_4));
		levels.add(new Level(LEVEL.LEVEL_5));
		levels.add(new Level(LEVEL.LEVEL_6));
		levels.add(new Level(LEVEL.LEVEL_6));
		levels.add(new Level(LEVEL.LEVEL_6));

		// Start at current level
		currentLevel = this.levels.remove(0);

		// Set game objects
		maze.create(currentLevel.mazeHeight, currentLevel.mazeWidth);
		player.reset(EDGE_SIZE_PX + 2, EDGE_SIZE_PX + 2, true);
		monsterHandler.set(currentLevel.numMonsters, currentLevel.monsterDifficulty);
		itemHandler.set(currentLevel.numItems);
	}

	public void newLevel() {
		// Set game objects
		maze.create(currentLevel.mazeHeight, currentLevel.mazeWidth);
		player.reset(EDGE_SIZE_PX + 2, EDGE_SIZE_PX + 2, false);
		monsterHandler.set(currentLevel.numMonsters, currentLevel.monsterDifficulty);
		itemHandler.set(currentLevel.numItems);
	}

	public void render() {
		super.render();
	}

}