package com.devtalk.gui;

import com.badlogic.gdx.input.GestureDetector;

public class DirectionalGestureDetector extends GestureDetector{
	
	public interface DirectionListener {
		void left();

		void right();

		void up();

		void down();
	}

	public DirectionalGestureDetector(DirectionListener directionListener) {
		super(new DirectionalGestureListener(directionListener));
	}
	
	private static class DirectionalGestureListener extends GestureAdapter{
		DirectionListener directionListener;
		
		public DirectionalGestureListener(DirectionListener directionListener){
			this.directionListener = directionListener;
		}
		
        public boolean fling(float velocityX, float velocityY, int button) {
			if(Math.abs(velocityX) > Math.abs(velocityY)){
				if(velocityX > 0){
						directionListener.right();
				}else{
						directionListener.left();
				}
			}else{
				if(velocityY > 0){
						directionListener.down();
				}else{                                  
						directionListener.up();
				}
			}
			return super.fling(velocityX, velocityY, button);
        }

	}

}
