package com.jprada.core.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.media.opengl.GL;

import com.jprada.core.graphics.Animation;
import com.jprada.core.graphics.Sprite;
import com.jprada.core.graphics.SpriteBatch2;
import com.jprada.core.graphics.SpriteFrame;

/**
 * Created By: Juankprada Date: 10/5/12 Time: 4:43 PM
 */
public abstract class MapObject implements Collidable{

	public enum Direction {
		down, downLeft, downRight, left, right, up, upLeft, upRight
	}

	public static List<MapObject> ENTITY_LIST = new ArrayList<MapObject>();

	protected Map<String, Animation> animations = new HashMap<String, Animation>();
	// protected SpriteFrame currentSprite;
	protected Animation currentAnimation;

	protected Direction facingDirection;

	protected Direction movingDirection;

	protected boolean movingToNextTile = false;

	protected String name;

	protected float posX;

	protected float posY;

	protected float speedX;

	protected float speedY;
	
	protected float drawX;
	
	protected float drawY;
	
	protected boolean collided = false;

	protected Map<String, Sprite> sprites = new HashMap<String, Sprite>();
	
	protected CollideBox collideBox;
	
	protected InteractBox interactBox;

	public Map<String, Animation> getAnimations() {
		return animations;
	}

	public Direction getFacingDirection() {
		return facingDirection;
	}

	public Direction getMovingDirection() {
		return movingDirection;
	}

	public String getName() {
		return name;
	}

	public float getPosX() {
		return posX;
	}

	public float getPosY() {
		return posY;
	}

	public float getSpeedX() {
		return speedX;
	}

	public float getSpeedY() {
		return speedY;
	}

	public Map<String, Sprite> getSprites() {
		return sprites;
	}
	

	public CollideBox getCollideBox() {
		return collideBox;
	}

	public void setCollideBox(CollideBox collideBox) {
		this.collideBox = collideBox;
	}

	public InteractBox getInteractBox() {
		return interactBox;
	}

	public void setInteractBox(InteractBox interactBox) {
		this.interactBox = interactBox;
	}

	protected void move(Direction direction) {
		this.movingDirection = direction;
	}

	public abstract void onDestroy();

	public abstract void onRender(GL gl, SpriteBatch2 batch,
			double interpolation);

	public abstract void onUpdate();

	public void setAnimations(Map<String, Animation> animations) {
		this.animations = animations;
	}

	public void setFacingDirection(Direction facingDirection) {
		this.facingDirection = facingDirection;
	}

	public void setMovingDirection(Direction movingDirection) {
		this.movingDirection = movingDirection;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isCollided() {
		return collided;
	}

	public void setCollided(boolean collided) {
		this.collided = collided;
	}

	public void setPosX(float posX) {
		this.posX = posX;
		this.collideBox.onUpdate(this.posX, this.posY);
	}

	public void setPosY(float posY) {
		this.posY = posY;
		this.collideBox.onUpdate(this.posX, this.posY);
	}

	public void setSpeedX(float speedX) {
		this.speedX = speedX;
	}

	public void setSpeedY(float speedY) {
		this.speedY = speedY;
	}

	public void setSprites(Map<String, Sprite> sprites) {
		this.sprites = sprites;
	}

	
	public void setPosition(float x, float y) {
		this.posX = x;
		this.posY = y;
	}
	
	/*
	 * Hack to get the real y pos, as rigth now, rendering is done top to bottom
	 * so we need to add the currentFrame height to the pos_y variable to get the real
	 * Y position
	 */
	public float getCoordY() {
		float realY = posY;
		
		if(this.currentAnimation != null) {
			realY+=this.currentAnimation.getFrames().get(this.currentAnimation.getCurrentFrame()).getRegionHeight();
		}
		
		return realY;
	}

}
