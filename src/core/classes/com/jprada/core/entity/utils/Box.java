package com.jprada.core.entity.utils;

public class Box {

	protected float x;
	protected float y;
	protected float w;
	protected float h;

	protected float xOffset;
	protected float yOffset;
	protected float hOffset;
	protected float wOffset;

	protected float left;
	protected float right;
	protected float top;
	protected float bottom;

	public Box(float x, float y, float w, float h) {
		this(x, y, w, h, 0, 0, 0, 0);
	}

	public Box(float x, float y, float w, float h, float offsetX,
			float offsetY, float offsetW, float offsetH) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;

		this.xOffset = offsetX;
		this.yOffset = offsetY;
		this.wOffset = offsetW;
		this.hOffset = offsetH;

		this.left = x + offsetX;
		this.right = x + w - offsetW - 1;
		this.top = y + offsetY;
		this.bottom = y + h - offsetH - 1;

	}

	public void onUpdate(float x, float y) {
		this.x = x;
		this.y = y;

		this.left = this.x + this.xOffset;
		this.right = this.x + this.w - this.wOffset - 1;
		this.top = this.y + this.yOffset;
		this.bottom = this.y + this.h - this.hOffset - 1;
	}
	
	public void setSizes(float w, float h) {
		this.w = w;
		this.h = h;
		onUpdate(this.x, this.y);
	}
	
	public boolean collides(Box other) {
		if(this.top > other.bottom) { return false; }
		if(this.bottom < other.top) { return false; }
		if(this.right < other.left) { return false; }
		if(this.left > other.right) { return false; }
		
		return true;
		
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getW() {
		return w;
	}

	public void setW(float w) {
		this.w = w;
	}

	public float getH() {
		return h;
	}

	public void setH(float h) {
		this.h = h;
	}

	public float getxOffset() {
		return xOffset;
	}

	public void setxOffset(float xOffset) {
		this.xOffset = xOffset;
	}

	public float getyOffset() {
		return yOffset;
	}

	public void setyOffset(float yOffset) {
		this.yOffset = yOffset;
	}

	public float gethOffset() {
		return hOffset;
	}

	public void sethOffset(float hOffset) {
		this.hOffset = hOffset;
	}

	public float getwOffset() {
		return wOffset;
	}

	public void setwOffset(float wOffset) {
		this.wOffset = wOffset;
	}

	public float getLeft() {
		return left;
	}

	public void setLeft(float left) {
		this.left = left;
	}

	public float getRight() {
		return right;
	}

	public void setRight(float right) {
		this.right = right;
	}

	public float getTop() {
		return top;
	}

	public void setTop(float top) {
		this.top = top;
	}

	public float getBottom() {
		return bottom;
	}

	public void setBottom(float bottom) {
		this.bottom = bottom;
	}
	
	
}
