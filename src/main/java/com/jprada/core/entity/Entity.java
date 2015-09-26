package com.jprada.core.entity;

public abstract class Entity extends Actor {

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

			this.speed.x = speedX;
			this.speed.y = speedY;
		} else {
			this.stopMoving();
		}

	}

	protected void fixStandingDireciton() {
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
		this.speed.x = 0;
		this.speed.y= 0;
		this.movingDirection = null;

		fixStandingDireciton();

	}

	@Override
	public void setMovingDirection(Direction movingDirection) {
		// TODO Auto-generated method stub
		super.setMovingDirection(movingDirection);
		
	}

	@Override
	public void setFacingDirection(Direction facingDirection) {
		super.setFacingDirection(facingDirection);
		fixStandingDireciton();
	}


}
