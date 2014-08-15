package com.jprada.core.entity.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.jprada.core.entity.Interactable;

public class ObjectInteraction {

	public static List<ObjectInteraction> ObjectInteractionList = new ArrayList<ObjectInteraction>();

	private Interactable a;
	
	private Interactable b;
	

	public ObjectInteraction(Interactable a, Interactable b) {
		this.a = a;
		this.b = b;
		
	}
	
	public static void executeInteractions() {
		
		for(ObjectInteraction oi : ObjectInteractionList) {
			if(oi.a == null || oi.b == null) continue;
			
			if(oi.a.onInteract(oi.b)) {
				oi.b.onInteract(oi.b);
			}
		}
		
		ObjectInteractionList.clear();
	}

}
