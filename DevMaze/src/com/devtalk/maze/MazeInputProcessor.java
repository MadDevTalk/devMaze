package com.devtalk.maze;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;

public class MazeInputProcessor implements InputProcessor {

    Camera camera;
    Vector3 touch_down = new Vector3();
    
    public MazeInputProcessor(Camera camera) {
    	this.camera = camera;
    }

	@Override
	public boolean keyDown(int keycode) {
	    
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (button == Buttons.LEFT)
			touch_down = new Vector3(screenX, screenY, 0);
			
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
    public boolean touchDragged(int x, int y, int pointer) {
        moveCamera( x, y );
        
        return false;
    }

    private void moveCamera( int touch_x, int touch_y ) {
        
        Vector3 new_position = new Vector3(touch_x, touch_y, 0);
        
        new_position.sub( touch_down );
        camera.translate( -new_position.x, new_position.y, 0 );
        
        touch_down.add( new_position );
    }

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
