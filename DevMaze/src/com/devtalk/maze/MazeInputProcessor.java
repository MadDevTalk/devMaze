package com.devtalk.maze;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;

public class MazeInputProcessor extends GameFrame implements InputProcessor {

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
		// the current position of the pointer
        Vector3 new_position = new Vector3(x, y, 0);
        
        // offset of new position from where drag started
        new_position.sub( touch_down );
        
        // move camera by offset, need to invert x
        camera.translate( -new_position.x, new_position.y, 0 );
        
        // move the drag started position to the current position
        touch_down.add( new_position );
        
        return false;
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
