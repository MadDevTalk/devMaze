package com.devtalk.maze;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;

public class MazeInputProcessor extends GameFrame implements InputProcessor {

    Vector3 last_touch_down = new Vector3();
    Camera camera;
    
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

    public boolean touchDragged(int x, int y, int pointer) {
        moveCamera( x, y );     
        return false;
    }

    private void moveCamera( int touch_x, int touch_y ) {
        Vector3 new_position = getNewCameraPosition( touch_x, touch_y );
        
        camera.translate( new_position.sub( camera.position ) );

        last_touch_down.set( touch_x, touch_y, 0);
    }

    private Vector3 getNewCameraPosition( int x, int y ) {
        Vector3 new_position = last_touch_down;
        new_position.sub(x, y, 0);
        new_position.y = -new_position.y;
        new_position.add( camera.position );

        return new_position;
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
