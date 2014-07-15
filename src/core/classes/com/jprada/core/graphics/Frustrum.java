package com.jprada.core.graphics;

import java.awt.*;

public class Frustrum {

	private int x;
	private int y;
	private int x2;
	private int y2;

	private int width;
	private int height;

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX2() {
		return x2;
	}

	public void setX2(int x2) {
		this.x2 = x2;
	}

	public int getY2() {
		return y2;
	}

	public void setY2(int y2) {
		this.y2 = y2;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Frustrum(int x, int y, int width, int height) {
		this.reset(x, y, width, height);
	}

	public void reset(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.x2 = x + width;
		this.y2 = y + height;
	}

	public void update(int x, int y) {
		this.x = x;
		this.y = y;
		this.x2 = this.x + this.width;
		this.y2 = this.y + this.height;
	}

	public boolean IsInFrustrum(int cx, int cy, int width, int height) {
		Rectangle rect1 = new Rectangle(this.x, this.y, this.width, this.height);
		Rectangle rect2 = new Rectangle(cx, cy, width, height);

		if (rect1.intersects(rect2)) {
			return true;
		} else {
			return false;
		}
	}

}
