package com.jprada.core.entity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.media.opengl.GL;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.jprada.core.graphics.Animation;
import com.jprada.core.graphics.Sprite;
import com.jprada.core.graphics.SpriteBatch;
import com.jprada.core.graphics.SpriteFrame;
import com.jprada.core.util.ResourceManager;

public abstract class Entity extends MapObject {

	protected int moveSpeed = 3;
	
	protected boolean movingUp;
	protected boolean movingLeft;
	protected boolean movingDown;
	protected boolean movingRight;
	protected boolean movingUpLeft;
	protected boolean movingUpRight;
	protected boolean movingDownLeft;
	protected boolean movingDownRight;
	
	protected void onMove() {
		
		if (this.movingDirection != null) {
			if (this.movingDirection == Direction.up) {
				this.speedY = -moveSpeed;
				this.speedX = 0;
				this.currentAnimation = animations.get("walk-up");
				this.facingDirection = Direction.up;

			} 
			if (this.movingDirection == Direction.down) {
				this.speedX = 0;
				this.speedY = moveSpeed;
				this.currentAnimation = animations.get("walk-down");
				this.facingDirection = Direction.down;

			} 
			if (this.movingDirection == Direction.right) {
				this.speedY = 0;
				this.speedX = moveSpeed;
				this.currentAnimation = animations.get("walk-right");
				this.facingDirection = Direction.right;

			} 
			if (this.movingDirection == Direction.left) {
				this.speedY = 0;
				this.speedX = -moveSpeed;
				this.currentAnimation = animations.get("walk-left");
				this.facingDirection = Direction.left;

			} 
			if (this.movingDirection == Direction.upLeft) {
				this.speedY = -moveSpeed / 1.5f;
				this.speedX = -moveSpeed / 1.5f;
				this.currentAnimation = animations.get("walk-up-left");
				this.facingDirection = Direction.upLeft;

			} 
			if (this.movingDirection == Direction.upRight) {
				this.speedY = -moveSpeed / 1.5f;
				this.speedX = moveSpeed / 1.5f;
				this.currentAnimation = animations.get("walk-up-right");
				this.facingDirection = Direction.upRight;

			} 
			if (this.movingDirection == Direction.downLeft) {
				this.speedY = moveSpeed / 1.5f;
				this.speedX = -moveSpeed / 1.5f;
				this.currentAnimation = animations.get("walk-down-left");
				this.facingDirection = Direction.downLeft;

			} 
			if (this.movingDirection == Direction.downRight) {
				this.speedY = moveSpeed / 1.5f;
				this.speedX = moveSpeed / 1.5f;
				this.currentAnimation = animations.get("walk-down-right");
				this.facingDirection = Direction.downRight;
			}
		} else {
			this.stopMoving();
		}
	}

	
	public void fixStandingDireciton() {
		if (this.facingDirection == Direction.up) {
			this.currentAnimation = animations.get("standing-up");
		} else if (this.facingDirection == Direction.down) {
			this.currentAnimation = animations.get("standing-down");
		} else if (this.facingDirection == Direction.right) {
			this.currentAnimation = animations.get("standing-right");
		} else if (this.facingDirection == Direction.left) {
			this.currentAnimation = animations.get("standing-left");
		} else if (this.facingDirection == Direction.upLeft) {
			this.currentAnimation = animations.get("standing-up-left");
		} else if (this.facingDirection == Direction.upRight) {
			this.currentAnimation = animations.get("standing-up-right");
		} else if (this.facingDirection == Direction.downLeft) {
			this.currentAnimation = animations.get("standing-down-left");
		} else if (this.facingDirection == Direction.downRight) {
			this.currentAnimation = animations.get("standing-down-right");
		} else {
			this.facingDirection = Direction.down;
			this.currentAnimation = animations.get("standing-down");
		}
	}
	
	public void stopMoving() {
		this.speedX = 0;
		this.speedY = 0;
		this.movingDirection = null;
		
		fixStandingDireciton();

	}
	
	@Override
	public void setMovingDirection(Direction movingDirection) {
		// TODO Auto-generated method stub
		super.setMovingDirection(movingDirection);
		onMove();
	}
	
	@Override
	public void setFacingDirection(Direction facingDirection) {
		super.setFacingDirection(facingDirection);
		fixStandingDireciton();
	}

}
