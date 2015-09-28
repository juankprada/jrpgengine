package com.jprada.core.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jogamp.opengl.GL;
import com.jprada.core.entity.utils.CollideBox;
import com.jprada.core.entity.utils.InteractBox;
import com.jprada.core.graphics.Animation;
import com.jprada.core.graphics.RenderBatch;
import com.jprada.core.graphics.SpriteBatch;
import com.jprada.core.util.Vector2;

/**
 * Created By: Juankprada Date: 10/5/12 Time: 4:43 PM
 */
public abstract class Actor implements Collidable, Interactable {

	public enum Direction {
		down(1),
		up(-1),
		left(-2), 
		right(2),
		downLeft(3), 
		upRight(-3),
		downRight(4), 
		upLeft(-4);
		
		
		private final int val;
		
		Direction(int val) {
			this.val = val;
		}
		
		public int val() { return val; }
		
		public boolean isOpposed(Direction other) {
			if(other.val()+val == 0) {
				return true;
			} else {
				return false;
			}
			
		}
		
	}
	


	public static List<Actor> ENTITY_LIST = new ArrayList<Actor>();

	protected String name;
	protected int currentLayer;

	/* Direction and movement */
	protected Direction facingDirection;
	protected Direction movingDirection;

	/* Animation and Frames data */
	protected Map<String, Animation> animations = new HashMap<String, Animation>();
	protected Animation currentAnimation;

	/* Collision and Interaction data */
	protected CollideBox collideBox;
	protected boolean collided = false;

	protected InteractBox interactBox;
	protected boolean interacting = false;
	protected boolean wantToInteract = false;
	// protected SpriteFrame currentSprite;

	protected Vector2 position = new Vector2();
	protected Vector2 speed = new Vector2();
	

	public Map<String, Animation> getAnimations() {
		return animations;
	}

	public CollideBox getCollideBox() {
		return collideBox;
	}

	/*
	 * Hack to get the real y position, as right now, rendering is done top to bottom
	 * so we need to add the currentFrame height to the pos_y variable to get
	 * the real Y position
	 */
	public float getCoordY() {
		float realY = this.position.y;

		if (this.currentAnimation != null) {
			realY += this.currentAnimation.getFrames()
					.get(this.currentAnimation.getCurrentFrame())
					.getRegionHeight();
		}

		return realY;
	}

	public Direction getFacingDirection() {
		return facingDirection;
	}

	public InteractBox getInteractBox() {
		return interactBox;
	}

	public Direction getMovingDirection() {
		return movingDirection;
	}

	public String getName() {
		return name;
	}

	public int getCurrentLayer() {
		return currentLayer;
	}

	public void setCurrentLayer(int currentLayer) {
		this.currentLayer = currentLayer;
	}

	public boolean isCollided() {
		return collided;
	}

	public boolean isInteracting() {
		return interacting;
	}

	public boolean isWantToInteract() {
		return wantToInteract;
	}

	protected void move(Direction direction) {
		this.movingDirection = direction;
	}
	
	public abstract void onCreate();

	public abstract void onDestroy();

	public abstract void onRender(SpriteBatch batch, double interpolation);

	public abstract void onRenderDebug(GL gl, RenderBatch batch, double interpolation);

	public abstract void onUpdate();

	public void setAnimations(Map<String, Animation> animations) {
		this.animations = animations;
	}

	public void setCollideBox(CollideBox collideBox) {
		this.collideBox = collideBox;
	}

	public void setCollided(boolean collided) {
		this.collided = collided;
	}

	public void setFacingDirection(Direction facingDirection) {
		if (!this.facingDirection.equals(facingDirection)) {
			this.facingDirection = facingDirection;
		}
	}

	public void setInteractBox(InteractBox interactBox) {
		this.interactBox = interactBox;
	}

	public void setInteracting(boolean interacting) {
		this.interacting = interacting;
	}

	public void setMovingDirection(Direction movingDirection) {
		this.movingDirection = movingDirection;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void updateInteractBox(float x, float y) {
		if (this.interactBox != null)
			this.interactBox.onUpdate(x, y);
	}

	public void updateCollideBox(float x, float y) {
		if (this.collideBox != null)
			this.collideBox.onUpdate(x, y);
	}

	public void setPosition(float x, float y) {
		this.position.x = x;
		this.position.y = y;

		updateCollideBox(this.position.x, this.position.y);
		updateInteractBox(this.position.x, this.position.y);

	}

	public void setPosX(float x) {
		this.position.x = x;

		updateCollideBox(this.position.x, this.position.y);
		updateInteractBox(this.position.x, this.position.y);
	}

	public void setPosY(float y) {
		this.position.y = y;
		updateCollideBox(this.position.x, this.position.y);
		updateInteractBox(this.position.x, this.position.y);
	}
	
	public void setSpeed(Vector2 speed) {
		this.speed = speed;
	}
	
	public void setSpeed(float x, float y) {
		this.speed.x =x;
		this.speed.y = y;
	}
	
	public void setSpeedX(float x) {
		this.speed.x = x;
	}
	
	public void setSpeedY(float y) {
		this.speed.y = y;
	}



	public Animation getCurrentAnimation() {
		return currentAnimation;
	}

	public void setCurrentAnimation(Animation currentAnimation) {
		this.currentAnimation = currentAnimation;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public Vector2 getSpeed() {
		return speed;
	}

	public void setWantToInteract(boolean wantToInteract) {
		this.wantToInteract = wantToInteract;
	}


}
