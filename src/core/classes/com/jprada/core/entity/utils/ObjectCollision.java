package com.jprada.core.entity.utils;

import java.util.ArrayList;
import java.util.List;

import com.jprada.core.entity.Collidable;

public class ObjectCollision {
	
	public static List<ObjectCollision> ObjectCollisionList = new ArrayList<ObjectCollision>();
	
	private Collidable a;
	private Collidable b;
	
	
	public ObjectCollision(Collidable a, Collidable b) {
		this.a = a;
		this.b = b;
	}

}
