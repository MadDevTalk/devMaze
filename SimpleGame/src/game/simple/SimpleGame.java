package game.simple;

import java.util.Iterator;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

// https://github.com/libgdx/libgdx/wiki/A-simple-game

public class SimpleGame implements ApplicationListener {
	
	Texture 			imgDrop;
	Texture 			imgBucket;
	Sound				sndDrop;
	Music				musRain;
	OrthographicCamera 	window;
	SpriteBatch 		page;
	Rectangle 			bucket;
	Array<Rectangle> 	raindrops;
	long				lastDropTime;
	
	@Override
	public void create() {	
		
		// Load the assets stored in *project name*-android\assets
		imgDrop = new Texture(Gdx.files.internal("Droplet.png"));
		imgBucket = new Texture(Gdx.files.internal("Bucket.png"));
		sndDrop = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
		musRain = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
		
		// Start playing background music
		musRain.setLooping(true);
		musRain.play();
		
		// The camera is our virtual window into the game
		window = new OrthographicCamera();
		window.setToOrtho(false, 800, 400);
		
		// SpriteBatch can draw 2D images (i.e. Textures)
		page = new SpriteBatch();
		
		// (0,0) of window and rectangle is bottom left
		bucket = new Rectangle();
		bucket.width = 64;				
		bucket.height = 64;
		bucket.x = 800 / 2 - 64 / 2;	// center horizontally
		bucket.y = 20;					// 20 pixels above the bottom
		
		raindrops = new Array<Rectangle>();
		spawnRaindrop();
	}

	@Override
	public void dispose() {	
		
		// Dispose of the objects that implement Disposable
		imgDrop.dispose();
	    imgBucket.dispose();
	    sndDrop.dispose();
	    musRain.dispose();
	    page.dispose();
	    /* Once you dispose of a resource, you should not access it in any way. 
	     * 
	     * Disposables are usually native resources which are not handled by 
	     * the Java garbage collector. This is the reason why we need to 
	     * manually dispose of them. Libgdx provides various ways to help with 
	     * asset management. Read the rest of the development guide to discover 
	     * them.
	     */
	}

	@Override
	public void render() {		

		// Clear screen with a dark blue
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);			// Set color (R,G,B,Alpha)
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);	// Clear the screen
	    
	    window.update();
	    
	    // Render the Bucket
	 	page.setProjectionMatrix(window.combined);	// use the cameras coords
	 	page.begin();
			/* Tell the SpriteBatch to start a new batch. Why do we need this and 
	 		 * what is a batch? OpenGL hates nothing more than telling it about 
	 		 * individual images. It wants to be told about as many images to render 
	 		 * as possible at once. 
	 		 * 
	 		 * The SpriteBatch class helps making OpenGL happy. It will record all 
	 		 * drawing commands in between SpriteBatch.begin() and SpriteBatch.end(). 
	 		 * Once we call SpriteBatch.end() it will submit all drawing requests we 
	 		 * made at once, speeding up rendering quite a bit. This all might look 
	 		 * cumbersome in the beginning, but it is what makes the difference 
	 		 * between rendering 500 sprites at 60 frames per second and rendering 
	 		 * 100 sprites at 20 frames per second.
	 		 */
	 	page.draw(imgBucket, bucket.x, bucket.y);
	 	for(Rectangle raindrop: raindrops) {
	 	    page.draw(imgDrop, raindrop.x, raindrop.y);
	 	}
	 	page.end();

	    // Move the bucket to where the touch/click happens
	    if(Gdx.input.isTouched()) {
	        Vector3 touchPos = new Vector3();
	        touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
	        window.unproject(touchPos);
	        bucket.x = touchPos.x - 64 / 2;
	     }
	    
	    if(Gdx.input.isKeyPressed(Keys.LEFT)) {
	    	bucket.x -= 200 * Gdx.graphics.getDeltaTime();
	    }
	    if(Gdx.input.isKeyPressed(Keys.RIGHT)) {
	    	bucket.x += 200 * Gdx.graphics.getDeltaTime();
	    }
	      
	    // Make sure the bucket stays within the screen bounds
	    if(bucket.x < 0) bucket.x = 0;
	    if(bucket.x > 800 - 64) bucket.x = 800 - 64;
	    
	    // Spawn new raindrop every second	
	    if(TimeUtils.nanoTime() - lastDropTime > 1000000000) {
	    	spawnRaindrop();
	    }
	      
	    // Move the raindrops, remove any that are beneath the bottom edge of
	    // the screen or that hit the bucket.
	    Iterator<Rectangle> iter = raindrops.iterator();
	    while(iter.hasNext()) {
	       Rectangle raindrop = iter.next();
	       raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
	       if(raindrop.y + 64 < 0) iter.remove();
	       if(raindrop.overlaps(bucket)) {
	          sndDrop.play();
	          iter.remove();
	       }
	      }
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
	
	private void spawnRaindrop() {
	    Rectangle raindrop = new Rectangle();
	    raindrop.x = MathUtils.random(0, 800-64);
	    raindrop.y = 480;
	    raindrop.width = 64;
	    raindrop.height = 64;
	    raindrops.add(raindrop);
	    lastDropTime = TimeUtils.nanoTime();
	 }
}
