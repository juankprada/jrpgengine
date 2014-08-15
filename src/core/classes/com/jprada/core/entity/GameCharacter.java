package com.jprada.core.entity;

import javax.media.opengl.GL;

import com.jprada.core.entity.utils.InteractBox;
import com.jprada.core.graphics.SpriteBatch2;
import com.jprada.core.states.WorldMapState;

/**
 * Created By: Juankprada Date: 10/5/12 Time: 4:44 PM
 */
public class GameCharacter extends Entity {

	@Override
	public void onUpdate() {

		if (this.speedX != 0 || this.speedY != 0) {

			this.collided = false;
			this.posX += this.speedX;
			this.posY += this.speedY;
			this.collideBox.onUpdate(this.posX, this.posY);

			if (!posValid()) {
				if (this.speedX != 0) {
					this.posX -= this.speedX;
				}

				if (this.speedY != 0) {
					this.posY -= this.speedY;
				}

				this.collided = true;
			}

			this.collideBox.onUpdate(this.posX, this.posY);

			this.interactBox.onUpdate(this.posX, this.posY);

		}

		// if(this.interacting == false && this.wantToInteract) {
		//
		// this.checkInteraction();
		// this.wantToInteract = false;
		// }

	}

	@Override
	public void onRender(GL gl, SpriteBatch2 batch, double interpolation) {

		this.drawX = posX;
		this.drawY = posY;

		// if it hasn't collided
		if (!this.collided) {
			this.drawX += (this.speedX * interpolation);
			this.drawY += (this.speedY * interpolation);
		}
		batch.draw(gl, this.currentAnimation.getNextFrame(), this.drawX,
				this.drawY);
		// batch.draw(texture, 100, 100);

	}

	@Override
	public void onDestroy() {

	}

	@Override
	public void onCollision(Collidable other) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean collides(Collidable other) {

		return this.collideBox.collides(other.getCollideBox());
	}

	@Override
	public boolean onInteract(Interactable other) {

		other.getInteractBox().checkCuadrants(this.getCollideBox());
		boolean[] cuadrant = other.getInteractBox().getCuadrants();
		boolean intrecated = false;

		if (cuadrant[InteractBox.TOP_Q]
				&& this.getFacingDirection().equals(Direction.down)) {
			other.setFacingDirection(facingDirection.up);
			intrecated = true;
		} else if (cuadrant[InteractBox.RIGHT_Q]
				&& this.getFacingDirection().equals(Direction.left)) {
			other.setFacingDirection(facingDirection.right);
			intrecated = true;
		} else if (cuadrant[InteractBox.DOWN_Q]
				&& this.getFacingDirection().equals(Direction.up)) {
			other.setFacingDirection(facingDirection.down);
			intrecated = true;
		} else if (cuadrant[InteractBox.LEFT_Q]
				&& this.getFacingDirection().equals(Direction.right)) {
			other.setFacingDirection(facingDirection.left);
			intrecated = true;
		} else if (cuadrant[InteractBox.TOP_RIGHT_Q]
				&& this.getFacingDirection().equals(Direction.downLeft)) {
			other.setFacingDirection(facingDirection.upRight);
			intrecated = true;
		} else if (cuadrant[InteractBox.TOP_LEFT_Q]
				&& this.getFacingDirection().equals(Direction.downRight)) {
			other.setFacingDirection(facingDirection.upLeft);
			intrecated = true;
		} else if (cuadrant[InteractBox.DOWN_RIGHT_Q]
				&& this.getFacingDirection().equals(Direction.upLeft)) {
			other.setFacingDirection(facingDirection.downRight);
			intrecated = true;
		} else if (cuadrant[InteractBox.DOWN_LEFT_Q]
				&& this.getFacingDirection().equals(Direction.upRight)) {
			other.setFacingDirection(facingDirection.downLeft);
			intrecated = true;
		}

		if (intrecated) {
			System.out.println("I: " + this + ", am interacting with " + other);
			
		}

		this.interacting = false;
		this.wantToInteract = false;

		return false;
	}
}
