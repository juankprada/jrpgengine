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

import com.jprada.core.entity.utils.ObjectCollision;
import com.jprada.core.graphics.Animation;
import com.jprada.core.graphics.Sprite;
import com.jprada.core.graphics.SpriteBatch;
import com.jprada.core.graphics.SpriteFrame;
import com.jprada.core.states.WorldMapState;
import com.jprada.core.util.ResourceManager;

public abstract class Entity extends MapObject {

	protected int moveSpeed = 5;

	protected boolean movingUp;
	protected boolean movingLeft;
	protected boolean movingDown;
	protected boolean movingRight;
	protected boolean movingUpLeft;
	protected boolean movingUpRight;
	protected boolean movingDownLeft;
	protected boolean movingDownRight;

	protected void onMove() {

		

		float speedX = 0;
		float speedY = 0;
//		Animation currentAnimation = this.currentAnimation;
//		Direction facingDirection = this.facingDirection;
		
			if (this.movingDirection != null) {
				if (this.movingDirection == Direction.up) {
					speedY = -moveSpeed;
					speedX = 0;
					this.currentAnimation = animations.get("walk-up");
					this.facingDirection = Direction.up;

				}
				if (this.movingDirection == Direction.down) {
					speedX = 0;
					speedY = moveSpeed;
					this.currentAnimation = animations.get("walk-down");
					this.facingDirection = Direction.down;

				}
				if (this.movingDirection == Direction.right) {
					speedY = 0;
					speedX = moveSpeed;
					this.currentAnimation = animations.get("walk-right");
					this.facingDirection = Direction.right;

				}
				if (this.movingDirection == Direction.left) {
					speedY = 0;
					speedX = -moveSpeed;
					this.currentAnimation = animations.get("walk-left");
					this.facingDirection = Direction.left;

				}
				if (this.movingDirection == Direction.upLeft) {
					speedY = -moveSpeed / 1.5f;
					speedX = -moveSpeed / 1.5f;
					this.currentAnimation = animations.get("walk-left");
					this.facingDirection = Direction.upLeft;

				}
				if (this.movingDirection == Direction.upRight) {
					speedY = -moveSpeed / 1.5f;
					speedX = moveSpeed / 1.5f;
					this.currentAnimation = animations.get("walk-right");
					this.facingDirection = Direction.upRight;

				}
				if (this.movingDirection == Direction.downLeft) {
					speedY = moveSpeed / 1.5f;
					speedX = -moveSpeed / 1.5f;
					this.currentAnimation = animations.get("walk-left");
					this.facingDirection = Direction.downLeft;

				}
				if (this.movingDirection == Direction.downRight) {
					speedY = moveSpeed / 1.5f;
					speedX = moveSpeed / 1.5f;
					this.currentAnimation = animations.get("walk-right");
					this.facingDirection = Direction.downRight;
				}
				
				this.speedX = speedX;
				this.speedY = speedY;
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
			this.currentAnimation = animations.get("standing-left");
		} else if (this.facingDirection == Direction.upRight) {
			this.currentAnimation = animations.get("standing-right");
		} else if (this.facingDirection == Direction.downLeft) {
			this.currentAnimation = animations.get("standing-left");
		} else if (this.facingDirection == Direction.downRight) {
			this.currentAnimation = animations.get("standing-right");
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

	
	protected boolean posValid() {
		boolean posValid = true;
//		for (MapObject mo : WorldMapState.worldMapObjects) {
			for (MapObject mo2 : WorldMapState.worldMapObjects) {

				if (!this.equals(mo2) && this.collides(mo2)) {
					ObjectCollision.ObjectCollisionList
							.add(new ObjectCollision(this, mo2));
					posValid = false;
				}
			}
//		}

		return posValid;
	}
}
