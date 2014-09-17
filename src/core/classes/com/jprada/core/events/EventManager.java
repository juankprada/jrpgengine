package com.jprada.core.events;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class EventManager {

	private static EventManager _instance;
	
	private LinkedList<Event> events;
	
//	private LinkedList<Event> lockEvents;
//	private LinkedList<Event> commonEvents;
	
	private EventManager() {
//		lockEvents = new LinkedList<Event>();
//		commonEvents = new LinkedList<Event>();
		events = new LinkedList<Event>();
	}
	
	public static EventManager getEventManager() {
		if(EventManager._instance == null) {
			EventManager._instance = new EventManager();
		}
		
		return EventManager._instance;
	}
	
	public void update() {
		
		ListIterator<Event> it = events.listIterator();
		while(it.hasNext()) {
			Event event = it.next();
			
			event.execute();
			
			if(event.isLockEvent()) {				
				if(event.isFinished()) {
					it.remove();
				} else {
					break;
				}
			}
			else {
				if(event.isFinished()) {
					it.remove();
				}
			}
		}
		
//		if(!this.lockEvents.isEmpty()) {
//			Event currentLockEvent = lockEvents.getFirst();
//			if(currentLockEvent != null) {
//				currentLockEvent.execute();
//				
//				if(currentLockEvent.isFinished()) {
//					lockEvents.removeFirst();
//					if(!lockEvents.isEmpty()) {
//						currentLockEvent = lockEvents.getFirst();
//						if(currentLockEvent != null) {
//							currentLockEvent.execute();
//						}
//					}
//				}
//			}
//		} else { // Only after executing all locking events we execut non locking events 
//			ListIterator<Event> it = commonEvents.listIterator(commonEvents.size());
//			while(it.hasPrevious()) {
//				Event event = it.previous();
//				
//				if(event != null) {
//					event.execute();
//					if(event.isFinished()) {
//						it.remove();
//					}
//				}
//			}
//		}
		
	}
	
	public void addEvent(Event ev) {
		this.events.addLast(ev);
//		if(ev.isLockEvent()) {
//			this.lockEvents.addLast(ev);
//		} else {
//			this.commonEvents.addLast(ev);
//		}
	}
	
}
