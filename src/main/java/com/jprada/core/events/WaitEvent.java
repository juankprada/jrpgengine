package com.jprada.core.events;

import com.jprada.core.Clock;
import com.jprada.core.states.GameState;

public class WaitEvent extends Event {

	private int millisecs;
	private boolean stopEntities;
	private boolean disableInput;
	
	private Clock waitClock;
	private float updateDelay;
	
	public WaitEvent(int millis,  boolean disableInput, boolean stopEntities) {
		this.millisecs = millis;
		this.stopEntities = stopEntities;
		this.disableInput = disableInput;
		this.waitClock = new Clock();
		this.lockEvent = true;
	}
	
	public void setUpdateDelay(float millis) {
		waitClock.initClock();
		this.updateDelay = millis;
	}
	
	@Override
	public void execute() {

		if(!this.isExecuted()) {
			executed = true;
			wait(millisecs);
		}
		else {
			// Find if game has waited long enough before enabling 
			// user input again and release this event
			if(waitClock.isReseted()) {
				this.finished = true;
				System.out.println("Finished Waiting");
				if(disableInput) {
					// Enable user input back
					GameState.INPUT_DISABLED = false;
				}
				
			} else {
				waitClock.updateClock();
				if(waitClock.getMillis() >= updateDelay) {
					updateDelay = 0;
					waitClock.resetClock();
				}
			}
		}
	}
	
	private void wait(int millis) {
		if(stopEntities) {
			// Stop All entities
			GameState.stopAllEntities();
		}
		if(disableInput) {
			// disable user input
			GameState.INPUT_DISABLED = true;
		}
		
		setUpdateDelay(millis);
	}

}
