package com.jprada.core.events;

import com.jprada.core.Clock;
import com.jprada.core.states.GameState;

public class WaitEvent extends Event {

	private int millisecs;
	private boolean stopEntities;
	private boolean disableInput;
	
	private Clock waitClock;
	private float updateDelay;
	
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
				if(disableInput) {
					// Enable user input back
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
		}
		
		setUpdateDelay(millis);
	}

}
