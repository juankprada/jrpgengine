package com.jprada.core.entity;

public interface Collidable {
	
	public void onCollision(Collidable other);
	
	public boolean collides(Collidable other);

	public CollideBox getCollideBox();
}
