package com.jprada.core.entity;

import javax.media.opengl.GL;

import com.jprada.core.graphics.SpriteBatch2;
import com.jprada.core.states.WorldMapState;

/**
 * Created By: Juankprada Date: 10/5/12 Time: 4:44 PM
 */
public class PlayableCharacter extends Entity {

	private boolean posValid() {
		boolean posValid = true;
		for (MapObject mo : WorldMapState.worldMapObjects) {
			for (MapObject mo2 : WorldMapState.worldMapObjects) {

				if (!mo.equals(mo2) && mo.collides(mo2)) {
					ObjectCollision.ObjectCollisionList.add(new ObjectCollision(mo, mo2));
					posValid = false;
				}
			}
		}

		return posValid;
	}

	@Override
	public void onUpdate() {

		if (this.speedX == 0 && this.speedY == 0) {
			return;
		}

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
}
