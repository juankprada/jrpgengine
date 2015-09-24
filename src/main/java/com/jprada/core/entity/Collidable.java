package com.jprada.core.entity;

import com.jprada.core.entity.utils.CollideBox;

public interface Collidable {
	
	public void onCollision(Collidable other);
	
	public boolean collides(Collidable other);

	public CollideBox getCollideBox();
}
