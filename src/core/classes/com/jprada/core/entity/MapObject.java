package com.jprada.core.entity;

import com.jprada.core.entity.utils.CollideBox;
import com.jprada.core.entity.utils.InteractBox;
import com.jprada.core.graphics.Animation;
import com.jprada.core.graphics.LineBatch;
import com.jprada.core.graphics.Sprite;
import com.jprada.core.graphics.SpriteBatch;

import javax.media.opengl.GL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created By: Juankprada Date: 10/5/12 Time: 4:43 PM
 */
public abstract class MapObject implements Collidable, Interactable{

	public enum Direction {
		down, downLeft, downRight, left, right, up, upLeft, upRight
	}

	public static List<MapObject> ENTITY_LIST = new ArrayList<MapObject>();

	protected Map<String, Animation> animations = new HashMap<String, Animation>();
	protected CollideBox collideBox;

	protected boolean collided = false;

	// protected SpriteFrame currentSprite;
	protected Animation currentAnimation;

	protected Direction facingDirection;

	protected InteractBox interactBox;

	protected boolean interacting = false;

	protected Direction movingDirection;
	
	protected boolean movingToNextTile = false;
	
	protected String name;
	
	protected float posX;
	protected float posY;
	
	protected float speedX;	
	protected float speedY;
	
	protected float drawX;
	protected float drawY;
	
//	protected Map<String, Sprite> sprites = new HashMap<String, Sprite>();
	
	protected boolean wantToInteract = false;

	public Map<String, Animation> getAnimations() {
		return animations;
	}

	public CollideBox getCollideBox() {
		return collideBox;
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

//	public Map<String, Sprite> getSprites() {
//		return sprites;
//	}

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

	public abstract void onDestroy();

	public abstract void onRender(GL gl, SpriteBatch batch,
			double interpolation);

	public abstract void onRenderDebug(GL gl, LineBatch batch, double interpolation);

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
		if(!this.facingDirection.equals(facingDirection)) {
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

	public void setPosition(float x, float y) {
		this.posX = x;
		this.posY = y;

        if(this.collideBox != null)
		    this.collideBox.onUpdate(this.posX, this.posY);

        if(this.interactBox != null)
		    this.interactBox.onUpdate(this.posX, this.posY);
	}

	public void setPosX(float posX) {
		this.posX = posX;
        if(this.collideBox != null)
		    this.collideBox.onUpdate(this.posX, this.posY);

        if(this.interactBox != null)
		    this.interactBox.onUpdate(this.posX, this.posY);
	}

	public void setPosY(float posY) {
		this.posY = posY;
        if(this.collideBox != null)
		    this.collideBox.onUpdate(this.posX, this.posY);

        if(this.interactBox != null)
		    this.interactBox.onUpdate(this.posX, this.posY);
	}

	public void setSpeedX(float speedX) {
		this.speedX = speedX;
	}

	public void setSpeedY(float speedY) {
		this.speedY = speedY;
	}

	
//	public void setSprites(Map<String, Sprite> sprites) {
//		this.sprites = sprites;
//	}
	
	public void setWantToInteract(boolean wantToInteract) {
		this.wantToInteract = wantToInteract;
	}

	
//	protected void checkInteraction() {
//	
//			for (MapObject mo2 : WorldMapState.worldMapObjects) {
//
//				if (!this.equals(mo2)
//						&& mo2.getInteractBox().collides(this.getCollideBox()) && (this.isWantToInteract() || mo2.isWantToInteract()) ) {
//					ObjectInteraction.ObjectInteractionList
//							.add(new ObjectInteraction(this, mo2));
//					System.out.println("We Interacted");
//					this.interacting = true;
//				}
//			}
//		
//		
//		
//	}
}
