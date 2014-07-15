package com.jprada.core.states;

import javax.media.opengl.GL;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseListener;
import com.jprada.core.entity.CharacterXMLImporter;
import com.jprada.core.entity.MapObject.Direction;
import com.jprada.core.entity.PlayableCharacter;
import com.jprada.core.graphics.SpriteBatch2;

public class WorldMapState implements GameState {

	private SpriteBatch2 spriteBatch;

	private PlayableCharacter player;
	
	
	private KeyListener keyListener = new KeyListener() {
		
		private static final int DOWN_KEY = 0;
		private static final int UP_KEY = 1;
		private static final int LEFT_KEY = 2;
		private static final int RIGHT_KEY = 3;
		
		private boolean[] keyStates = new boolean[4];
				
		@Override
		public void keyReleased(KeyEvent e) {
			if (!e.isAutoRepeat() ) {
				int keyCode = e.getKeyCode();
								
				if (keyCode == KeyEvent.VK_LEFT) {
					this.keyStates[LEFT_KEY] = false;
					if(keyStates[DOWN_KEY]) { player.setMovingDirection(Direction.down); }
					else if(keyStates[UP_KEY]) { player.setMovingDirection(Direction.up); }
					else if(keyStates[RIGHT_KEY]) { player.setMovingDirection(Direction.right); }
					else { player.setMovingDirection(null); }
				} else if (keyCode == KeyEvent.VK_RIGHT) {
					this.keyStates[RIGHT_KEY] = false;
					if(keyStates[DOWN_KEY]) { player.setMovingDirection(Direction.down); }
					else if(keyStates[UP_KEY]) { player.setMovingDirection(Direction.up); }
					else if(keyStates[LEFT_KEY]) { player.setMovingDirection(Direction.left); }
					else { player.setMovingDirection(null); }	
				} else if (keyCode == KeyEvent.VK_UP) {
					this.keyStates[UP_KEY] = false;
					if(keyStates[DOWN_KEY]) { player.setMovingDirection(Direction.down); }
					else if(keyStates[RIGHT_KEY]) { player.setMovingDirection(Direction.right); }
					else if(keyStates[LEFT_KEY]) { player.setMovingDirection(Direction.left); }
					else { player.setMovingDirection(null); }
				} else if (keyCode == KeyEvent.VK_DOWN) {
					this.keyStates[DOWN_KEY] = false;
					if(keyStates[UP_KEY]) { player.setMovingDirection(Direction.up); }
					else if(keyStates[RIGHT_KEY]) { player.setMovingDirection(Direction.right); }
					else if(keyStates[LEFT_KEY]) { player.setMovingDirection(Direction.left); }
					else { player.setMovingDirection(null); }
				}
			}
		}
		
		@Override
		public void keyPressed(KeyEvent e) {
			if (!e.isAutoRepeat()) {
				int keyCode = e.getKeyCode();
				if (keyCode == KeyEvent.VK_LEFT) {
					this.keyStates[LEFT_KEY] = true;
					if(player.getMovingDirection() != Direction.left) { 
						player.setMovingDirection(Direction.left); 
					}
				} else if (keyCode == KeyEvent.VK_RIGHT) {
					this.keyStates[RIGHT_KEY] = true;
					if(player.getMovingDirection() != Direction.right) {
						player.setMovingDirection(Direction.right);
					}
				} else if (keyCode == KeyEvent.VK_UP ) {
					this.keyStates[UP_KEY] = true;
					if( player.getMovingDirection() != Direction.up) {
						player.setMovingDirection(Direction.up);
					}
				} else if (keyCode == KeyEvent.VK_DOWN ) { 
					this.keyStates[DOWN_KEY] = true;
					if(player.getMovingDirection() != Direction.down) {
						player.setMovingDirection(Direction.down);
					}
				}
			}
		}
	};
	
	
	@Override
	public KeyListener getKeyListener()  {
		return this.keyListener;
	}
	
	@Override
	public MouseListener getMouseListener() {
		return null;
	}
	

	@Override
	public void onInit(GL gl) {
		spriteBatch = new SpriteBatch2();
		spriteBatch.setup(gl);

		player = (PlayableCharacter) CharacterXMLImporter.loadCharacterSheet("main-player.xml", PlayableCharacter.class);
		
	}

	@Override
	public void onUpdate(GL gl) {
		// TODO Auto-generated method stub
		player.onUpdate();
	}

	@Override
	public void onRender(GL gl, double interpolation) {
		// TODO Auto-generated method stub
		spriteBatch.begin(gl);

		player.onRender(gl, spriteBatch, interpolation);

		spriteBatch.end(gl);
	}

	@Override
	public void onFinish(GL gl) {
		// TODO Auto-generated method stub

	}

}



