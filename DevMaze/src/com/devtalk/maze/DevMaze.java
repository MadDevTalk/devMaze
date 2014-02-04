package com.devtalk.maze;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.devtalk.actors.ItemHandler;
import com.devtalk.actors.MonsterHandler;
import com.devtalk.actors.Player;
import com.devtalk.gui.GameScreen;
import com.devtalk.gui.LevelFinishScreen;
import com.devtalk.gui.MainMenuScreen;
import com.devtalk.gui.PauseScreen;
import com.devtalk.maze.Level.LEVEL;

public class DevMaze extends Game {

	public static final boolean DEBUG = true;

	public static final int EDGE_SIZE_PX = 128;      /* The square size of maze tiles must be power of 2 */
	public static final int PLAYER_SIZE_PX = 32;     /* The square size of the player, must be  power of 2 */
	public static final int MONSTER_SIZE_PX = 64;    /* The square size of the monster, must be power of 2 */
	public static final int KEY_VEL_PxPer60S = 6;    /* DEPRECATED: The speed in pixels of the player */
	public static final int SPEED_LATCH_PX = 32;     /* DEPRECATED: Fastest speed when dragging */
                                                     
	public SpriteBatch batch;                     /* Canvas to draw/display game elements */
	public BitmapFont font;                       /* Canvas to draw/display debug text */
	public OrthographicCamera camera;             /* Our view of the SpriteBatch */
                                                     
	public Maze maze;                             /* The current maze tileset */
	public Player player;                         /* The player object */
	public MonsterHandler monsterHandler;           /* Monster Controller object */
	public ItemHandler itemHandler;               /* Item Controller object */
                                                     
	public MainMenuScreen mainMenuScreen;         /* Main Menu Screen */
	public GameScreen gameScreen;                 /* Gameplay screen */
	public PauseScreen pauseScreen;               /* DEPRECATED: Pause menu screen */
	public LevelFinishScreen levelFinishScreen;   /* TODO: Level information screen */

	public List<Level> levels;                    /* List of available levels TODO: expand */
	public Level currentLevel;                    /* Our current level being played */

	public boolean pause;                         /* Is the game paused */

	
	/* (non-Javadoc)
	 * @see com.badlogic.gdx.ApplicationListener#create()
	 */
	public void create() {

		// Create batch and font
		this.batch = new SpriteBatch();
		this.font = new BitmapFont();

		// Create Camera
		this.camera = new OrthographicCamera();
		this.camera.setToOrtho(false, 800, 480);

		// Create game objects
		this.maze = new Maze(this);
		this.player = new Player(this);
		this.monsterHandler = new MonsterHandler(this);
		this.itemHandler = new ItemHandler(this);
		this.levels = new ArrayList<Level>();

		// Create screens
		this.mainMenuScreen = new MainMenuScreen(this);
		this.gameScreen = new GameScreen(this);
		this.pauseScreen = new PauseScreen(this);
		this.levelFinishScreen = new LevelFinishScreen(this);

		// Start at menu
		this.setScreen(mainMenuScreen);
		this.pause = false;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.Game#dispose()
	 */
	public void dispose() {
		// Dispose LibGDX stuff
		this.batch.dispose();
		this.font.dispose();

		// Dispose game objects
		this.maze.dispose();
		this.player.dispose();
		this.monsterHandler.dispose();
		this.itemHandler.dispose();

		// Dispose Screens
		this.mainMenuScreen.dispose();
		this.gameScreen.dispose();
	}


	public void newGame() {
		// Reset levels
		this.levels.clear();
		this.levels.add(new Level(LEVEL.LEVEL_1));
		this.levels.add(new Level(LEVEL.LEVEL_2));
		this.levels.add(new Level(LEVEL.LEVEL_3));
		this.levels.add(new Level(LEVEL.LEVEL_4));
		this.levels.add(new Level(LEVEL.LEVEL_5));
		this.levels.add(new Level(LEVEL.LEVEL_6));
		this.levels.add(new Level(LEVEL.LEVEL_6));
		this.levels.add(new Level(LEVEL.LEVEL_6));

		// Start at current level
		this.currentLevel = this.levels.remove(0);

		// Set game objects
		this.maze.create(currentLevel.mazeHeight, currentLevel.mazeWidth);
		this.player.reset(EDGE_SIZE_PX + 2, EDGE_SIZE_PX + 2, true);
		this.monsterHandler.set(currentLevel.numMonsters,
				currentLevel.monsterDifficulty);
		//this.itemHandler.set(currentLevel.numItems);
	}

	public void newLevel() {
		// Set game objects
		this.maze.create(currentLevel.mazeHeight, currentLevel.mazeWidth);
		this.player.reset(EDGE_SIZE_PX + 2, EDGE_SIZE_PX + 2, false);
		this.monsterHandler.set(currentLevel.numMonsters,
				currentLevel.monsterDifficulty);
		//this.itemHandler.set(currentLevel.numItems);
	}

	public void render() {
		super.render(); // Do not delete
	}

}