package com.jprada.core.particleengine;

import javax.media.opengl.GL;

import com.jprada.core.graphics.Sprite;
import com.jprada.core.graphics.SpriteBatch;
import com.jprada.core.util.GLColor;

public class Particle {

	private Sprite sprite;
	private float posX;
	private float posY;
	private float speedX;
	private float speedY;
	private float angle;
	private float angularVelocity;
	private GLColor color;
	private float size;
	private int ttl;
	private float scaleX;
	private float scaleY;
	
	
	public Particle(Sprite sprite, float posx, float posy, float speedX, float speedY, float angle, float angularVelocity, GLColor color, float size, int ttl) {
		this.sprite = sprite;
		this.posX = posx;
		this.posY = posy;
		this.speedX = speedX;
		this.speedY = speedY;
		this.angle = angle;
		this.angularVelocity = angularVelocity;
		this.color = color;
		this.size = size;
		this.ttl = ttl;
		this.scaleX = 0.0f;
		this.scaleY = 0.0f;
	}
	
	public void update() {
		ttl --;
		posX+=speedX;
		posY+= speedY;
		angle+=angularVelocity +10;
		scaleX+=0.03f;
		scaleY+=0.03f;
		
	}
	
	public void draw(GL gl, SpriteBatch batch) {
		float originX = this.sprite.getTexture().getImageWidth() / 2;
		float originY = this.sprite.getTexture().getImageHeight() / 2;
		
		
		batch.draw(gl, sprite, posX, posY, color, scaleX, scaleY, this.angle);
		

	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	public float getPosX() {
		return posX;
	}

	public void setPosX(float posX) {
		this.posX = posX;
	}

	public float getPosY() {
		return posY;
	}

	public void setPosY(float posY) {
		this.posY = posY;
	}

	public float getSpeedX() {
		return speedX;
	}

	public void setSpeedX(float speedX) {
		this.speedX = speedX;
	}

	public float getSpeedY() {
		return speedY;
	}

	public void setSpeedY(float speedY) {
		this.speedY = speedY;
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	public float getAngularVelocity() {
		return angularVelocity;
	}

	public void setAngularVelocity(float angularVelocity) {
		this.angularVelocity = angularVelocity;
	}

	public GLColor getColor() {
		return color;
	}

	public void setColor(GLColor color) {
		this.color = color;
	}

	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.size = size;
	}

	public int getTtl() {
		return ttl;
	}

	public void setTtl(int ttl) {
		this.ttl = ttl;
	}
	
	
}
