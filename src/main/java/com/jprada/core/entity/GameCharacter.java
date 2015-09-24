package com.jprada.core.entity;

import com.jogamp.opengl.GL;
import com.jprada.core.entity.utils.InteractBox;
import com.jprada.core.graphics.RenderBatch;
import com.jprada.core.states.WorldMapState;
import com.jprada.core.util.GLColor;

/**
 * Created By: Juankprada Date: 10/5/12 Time: 4:44 PM
 */
public class GameCharacter extends Entity {

	@Override
	public void onUpdate() {
		
		onMove();
		if (this.speed.x != 0 || this.speed.y != 0) {

			this.collided = false;
			this.position.x += this.speed.x;
			this.position.y += this.speed.y;
			this.collideBox.onUpdate(this.position.x, this.position.y);

			if (!WorldMapState.currentMap.isPosValid(this)) {
				if (this.speed.x != 0) {
					this.position.x -= this.speed.x;
				}

				if (this.speed.y != 0) {
					this.position.y -= this.speed.y;
				}

				this.collided = true;
			}

			this.collideBox.onUpdate(this.position.x, this.position.y);

			this.interactBox.onUpdate(this.position.x, this.position.y);

		}

		// if(this.interacting == false && this.wantToInteract) {
		//
		// this.checkInteraction();
		// this.wantToInteract = false;
		// }

	}
	
	@Override
	public void onRender(RenderBatch batch, double interpolation) {

		
		
		float drawX = position.x;
		float drawY = position.y;

		// if it hasn't collided
		if (!this.collided) {
			drawX += (this.speed.x * interpolation);
			drawY += (this.speed.y * interpolation);
		}
		
		batch.draw(this.currentAnimation.getNextFrame(), drawX, drawY);
		// batch.draw(texture, 100, 100);

	}
	
	@Override
	public void onRenderDebug(GL gl, RenderBatch batch, double interpolation) {
		
		GLColor color = new GLColor(1.0f, 0.0f, 0.0f);
		
		// Collide Box
		{
			float x1 = this.getCollideBox().getX()
					+ this.getCollideBox().getxOffset();
			float y1 = this.getCollideBox().getY()
					+ this.getCollideBox().getyOffset();
			float x2 = x1 + this.getCollideBox().getW()
					- this.getCollideBox().getwOffset()
					- this.getCollideBox().getxOffset();
			float y2 = y1 + this.getCollideBox().getH()
					- this.getCollideBox().gethOffset()
					- this.getCollideBox().getyOffset();
	
			if (!this.collided) {
				x1 += (this.speed.x * interpolation);
				x2 += (this.speed.x * interpolation);
				y1 += (this.speed.y * interpolation);
				y2 += (this.speed.y * interpolation);
			}
			
			batch.draw(x1, y1, x1, y2, color);
			batch.draw(x1, y1, x2, y1, color);
			batch.draw(x2, y2, x1, y2, color);
			batch.draw(x2, y2, x2, y1, color);
		}
		
		
		//Interact Box
		color = new GLColor(0, 1.0f, 0);
		{
			float x1 = this.getInteractBox().getX()
					+ this.getInteractBox().getxOffset();
			float y1 = this.getInteractBox().getY()
					+ this.getInteractBox().getyOffset();
			float x2 = x1 + this.getInteractBox().getW()
					- this.getInteractBox().getwOffset()
					- this.getInteractBox().getxOffset();
			float y2 = y1 + this.getInteractBox().getH()
					- this.getInteractBox().gethOffset()
					- this.getInteractBox().getyOffset();
	
			if (!this.collided) {
				x1 += (this.speed.x * interpolation);
				x2 += (this.speed.x * interpolation);
				y1 += (this.speed.y * interpolation);
				y2 += (this.speed.y * interpolation);
			}
			
			batch.draw(x1, y1, x1, y2, color);
			batch.draw(x1, y1, x2, y1, color);
			batch.draw(x2, y2, x1, y2, color);
			batch.draw(x2, y2, x2, y1, color);
		}
		
	}
	
	

	@Override
	public void onDestroy() {

	}

	@Override
	public void onCollision(Collidable other) {


	}

	@Override
	public boolean collides(Collidable other) {
		if (this.collideBox != null && other.getCollideBox() != null) {
			return this.collideBox.collides(other.getCollideBox());
		} else {
			return false;
		}
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

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		
	}
}
