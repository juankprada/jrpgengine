package com.jprada.core.entity;

import java.util.Arrays;

public class InteractBox extends Box{
	
	public static final int TOP_LEFT_Q = 0;
	public static final int TOP_Q = 1;
	public static final int TOP_RIGHT_Q = 2;
	public static final int RIGHT_Q = 3;
	public static final int DOWN_RIGHT_Q = 4;
	public static final int DOWN_Q = 5;
	public static final int DOWN_LEFT_Q = 6;
	public static final int LEFT_Q = 7;
	
	
	private float cuadWidth;
	private float cuadHeight;
	
	private float width;
	private float height;
	
	public InteractBox(float x, float y, float w, float h, float offsetX,
			float offsetY, float offsetW, float offsetH) {
		super(x, y, w, h, offsetX, offsetY, offsetW, offsetH);
		
	}

	public InteractBox(float x, float y, float w, float h) {
		super(x, y, w, h);
		// TODO Auto-generated constructor stub
	}
	
	private void setCuadrantInfo() {
		float wOff = this.right - this.left;
		float hOff = this.bottom - this.top;
		
		this.cuadWidth = (wOff / 3);
		this.cuadHeight = (hOff/ 3);
		
		this.width = this.w;
		this.height = this.h;
	}

	
	public boolean[] checkCuadrants(Box colbox) {
		boolean[] collidesWith = new boolean[8];
		
		Arrays.fill(collidesWith, false);
		
		// Creates cuadrants
		Box topLeftQ = new Box(this.left, this.top, this.cuadWidth, this.cuadHeight);
		Box topQ = new Box(this.left+ this.cuadWidth, this.top, this.cuadWidth, this.cuadHeight);
		Box topRightQ = new Box(this.left+ (2*this.cuadWidth), this.top, this.cuadWidth, this.cuadHeight);
		Box rightQ = new Box(this.left+ (2*this.cuadWidth), this.top + this.cuadHeight, this.cuadWidth, this.cuadHeight);
		Box downRightQ = new Box(this.left+ (2*this.cuadWidth), this.top + (2 * this.cuadHeight), this.cuadWidth, this.cuadHeight);
		Box downQ = new Box(this.left+ this.cuadWidth, this.top + (2 * this.cuadHeight), this.cuadWidth, this.cuadHeight);
		Box downLeftQ = new Box(this.left, this.top + (2 * this.cuadHeight), this.cuadWidth, this.cuadHeight);
		Box leftQ = new Box(this.left, this.top + this.cuadHeight, this.cuadWidth, this.cuadHeight);
		
		
		if(topLeftQ.collides(colbox)) { collidesWith[TOP_LEFT_Q] = true; }
		if(topQ.collides(colbox)) { collidesWith[TOP_Q] = true; }
		if(topRightQ.collides(colbox)) { collidesWith[TOP_RIGHT_Q] = true; }
		if(rightQ.collides(colbox)) { collidesWith[RIGHT_Q] = true; }
		if(downRightQ.collides(colbox)) { collidesWith[DOWN_RIGHT_Q] = true; }
		if(downQ.collides(colbox)) { collidesWith[DOWN_Q] = true; }
		if(downLeftQ.collides(colbox)) { collidesWith[DOWN_LEFT_Q] = true; }
		if(leftQ.collides(colbox)) { collidesWith[LEFT_Q] = true; }
		
		
		return collidesWith;
	}
}
