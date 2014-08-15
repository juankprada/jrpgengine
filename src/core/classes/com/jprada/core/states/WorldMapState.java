package com.jprada.core.states;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseListener;
import com.jprada.core.GameWindow;
import com.jprada.core.entity.GameCharacter;
import com.jprada.core.entity.CharacterXMLImporter;
import com.jprada.core.entity.MapObject;
import com.jprada.core.entity.MapObject.Direction;
import com.jprada.core.entity.utils.ObjectInteraction;
import com.jprada.core.graphics.LineBatch;
import com.jprada.core.graphics.SpriteBatch2;
import com.jprada.core.util.GLColor;
import com.jprada.core.util.algorithm.RadixSort;

public class WorldMapState extends GameState {

	private SpriteBatch2 spriteBatch;
	private LineBatch lineBatch;

	public static GameCharacter player;	
	public static List<MapObject> worldMapObjects = new ArrayList<MapObject>();
	
	
	
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
				
				if(keyCode == KeyEvent.VK_SHIFT) {
					System.out.println("Released Tab");
					GameWindow.ENABLE_DEBUG_INFO = false;
				}
				
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
				
				if(keyCode == KeyEvent.VK_SPACE && !player.isWantToInteract()) {
					player.setWantToInteract(true);
				}
				
				if(keyCode == KeyEvent.VK_SHIFT) {
					System.out.println("Pressed Tab");
					GameWindow.ENABLE_DEBUG_INFO = true;
				}
				
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
		
		lineBatch = new LineBatch();
		lineBatch.setup(gl);
		
		

		player = (GameCharacter) CharacterXMLImporter.loadCharacterSheet("main-player.xml", GameCharacter.class);
		GameCharacter p2 = (GameCharacter) CharacterXMLImporter.loadCharacterSheet("main-player.xml", GameCharacter.class);
		GameCharacter p3 = (GameCharacter) CharacterXMLImporter.loadCharacterSheet("main-player.xml", GameCharacter.class);
		
		
		p2.setPosX(200);
		p2.setPosY(200);
		p3.setPosX(200);
		p3.setPosY(220);
		
		this.worldMapObjects.add(player);
		this.worldMapObjects.add(p2);
		this.worldMapObjects.add(p3);
		
	}

	@Override
	public void onUpdate(GL gl) {
		ObjectInteraction.executeInteractions();
		
		for(MapObject mo : this.worldMapObjects) {
			mo.onUpdate();
		}
		
		for(MapObject mo : WorldMapState.worldMapObjects) {
			for (MapObject mo2 : WorldMapState.worldMapObjects) {
				
				if(!mo.equals(mo2) && mo.isWantToInteract() && mo2.getInteractBox().collides(mo.getCollideBox())) {
					ObjectInteraction.ObjectInteractionList.add(new ObjectInteraction(mo, mo2));
				}
			}
			
			mo.setWantToInteract(false);
		}
	}

	@Override
	public GameCharacter getPlayer() {
		return this.player;
	}
	
	@Override
	public void onRender(GL gl, double interpolation) {
		// TODO Auto-generated method stub
		
		this.worldMapObjects = RadixSort.sortEntities(this.worldMapObjects);
		spriteBatch.begin(gl);

		for(MapObject mo : this.worldMapObjects) {
			mo.onRender(gl, spriteBatch, interpolation);
		}
//		player.onRender(gl, spriteBatch, interpolation);

		spriteBatch.end(gl);
		
		if(GameWindow.ENABLE_DEBUG_INFO) {
			lineBatch.begin(gl);
				lineBatch.setRenderColor(new GLColor(1.0f, 0.0f, 0.0f));
				
				for(MapObject mo : this.worldMapObjects) {
					float x1 = mo.getCollideBox().getX() + mo.getCollideBox().getxOffset();
					float y1 = mo.getCollideBox().getY() + mo.getCollideBox().getyOffset();
					float x2= x1 + mo.getCollideBox().getW() - mo.getCollideBox().getwOffset() - mo.getCollideBox().getxOffset();
					float y2 = y1 + mo.getCollideBox().getH() - mo.getCollideBox().gethOffset() - mo.getCollideBox().getyOffset();
				
					lineBatch.draw(gl, x1, y1, x1, y2);
					lineBatch.draw(gl, x1, y1, x2, y1);
					lineBatch.draw(gl, x2, y2, x1, y2);
					lineBatch.draw(gl, x2, y2, x2, y1);
				}
			
				lineBatch.setRenderColor(new GLColor(0, 1.0f, 0));
				for(MapObject mo : this.worldMapObjects) {
					float x1 = mo.getInteractBox().getX() + mo.getInteractBox().getxOffset();
					float y1 = mo.getInteractBox().getY() + mo.getInteractBox().getyOffset();
					float x2= x1 + mo.getInteractBox().getW() - mo.getInteractBox().getwOffset() - mo.getInteractBox().getxOffset();
					float y2 = y1 + mo.getInteractBox().getH() - mo.getInteractBox().gethOffset() - mo.getInteractBox().getyOffset();
				
					lineBatch.draw(gl, x1, y1, x1, y2);
					lineBatch.draw(gl, x1, y1, x2, y1);
					lineBatch.draw(gl, x2, y2, x1, y2);
					lineBatch.draw(gl, x2, y2, x2, y1);
				}
				
			lineBatch.end(gl);
		}
	}

	@Override
	public void onFinish(GL gl) {
		// TODO Auto-generated method stub

	}

}



