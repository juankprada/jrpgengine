package com.jprada.core.entity;

import java.util.ArrayList;
import java.util.List;

public class ObjectCollision {
	
	public static List<ObjectCollision> ObjectCollisionList = new ArrayList<ObjectCollision>();
	
	private Collidable a;
	private Collidable b;
	
	
	public ObjectCollision(Collidable a, Collidable b) {
		this.a = a;
		this.b = b;
	}

}
