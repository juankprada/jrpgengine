package com.jprada.core.entity;

import javax.media.opengl.GL;

import com.jprada.core.graphics.SpriteBatch2;

/**
 * Created By: Juankprada Date: 10/5/12 Time: 4:44 PM
 */
public class PlayableCharacter extends Entity {

	
	@Override
	public void onUpdate() {
		
			this.posX += this.speedX;
			this.posY += this.speedY;
	}

	@Override
	public void onRender(GL gl, SpriteBatch2 batch, double interpolation) {
		
		this.drawX = posX;
		this.drawY = posY;
		
		// if it hasn't collided
		this.drawX += (this.speedX * interpolation);
		this.drawY += (this.speedY * interpolation);
		
		batch.draw(gl, this.currentAnimation.getNextFrame(), this.drawX, this.drawY);
		// batch.draw(texture, 100, 100);

	}

	@Override
	public void onDestroy() {
		
	}
}
