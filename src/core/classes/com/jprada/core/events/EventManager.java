package com.jprada.core.events;

import java.util.Iterator;
import java.util.LinkedList;

public class EventManager {

	private static EventManager _instance;
	
	private LinkedList<Event> lockEvents;
	private LinkedList<Event> commonEvents;
	
	private EventManager() {
		lockEvents = new LinkedList<Event>();
		commonEvents = new LinkedList<Event>();
	}
	
	public static EventManager getEventManager() {
		if(EventManager._instance == null) {
			EventManager._instance = new EventManager();
		}
		
		return EventManager._instance;
	}
	
	public void update() {
		
		if(!this.lockEvents.isEmpty()) {
			Event currentLockEvent = lockEvents.getFirst();
			if(currentLockEvent != null) {
				currentLockEvent.execute();
				
				if(currentLockEvent.isFinished()) {
					lockEvents.removeFirst();
					currentLockEvent = lockEvents.getFirst();
					if(currentLockEvent != null) {
						currentLockEvent.execute();
					}
				}
			}
		} else { // Only after executing all locking events we execut non locking events 
			Iterator<Event> it = commonEvents.iterator();
			while(it.hasNext()) {
				Event event = it.next();
				
				if(event != null) {
					event.execute();
					if(event.isFinished()) {
						it.remove();
					}
				}
			}
		}
		
	}
	
	public void addEvent(Event ev) {
		if(ev.isLockEvent()) {
			this.lockEvents.add(ev);
		} else {
			this.commonEvents.add(ev);
		}
	}
	
}
