package com.jprada.core.entity;

import java.util.ArrayList;
import java.util.List;

public class ObjectInteraction {

	public static List<ObjectInteraction> ObjectInteractionList = new ArrayList<ObjectInteraction>();

	private Collidable a;
	private Collidable b;

	public ObjectInteraction(Collidable a, Collidable b) {
		this.a = a;
		this.b = b;
	}

}
