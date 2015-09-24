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
			
			oi.a.onInteract(oi.b);
			
			float topAX = oi.a.getInteractBox().getX();
			float topAY = oi.a.getInteractBox().getY();
			
			float topBX = oi.b.getInteractBox().getX();
			float topBY = oi.b.getInteractBox().getY();
			
			boolean executeInteraction = false;
			
			if(oi.a.getFacingDirection().isOpposed(oi.b.getFacingDirection())) {
				
				switch(oi.a.getFacingDirection()) {
					case down:
						if (topAY < topBY) { executeInteraction = true; }
						break;
					case downLeft:
						break;
					case downRight:
						break;
					case left:
						if (topAX > topBX) { executeInteraction = true; }
						break;
					case right:
						if (topAX < topBX) { executeInteraction = true; }
						break;
					case up:
						if (topAY > topBY) { executeInteraction = true; }
						break;
					case upLeft:
						break;
					case upRight:
						break;
					default:
						break;
					
				}
				
				if (executeInteraction) {
					oi.b.onInteract(oi.a);
				}
				
			}
			
//			if(oi.a.onInteract(oi.b)) {
//				oi.b.onInteract(oi.b);
//			}
		}
		
		ObjectInteractionList.clear();
	}

}
