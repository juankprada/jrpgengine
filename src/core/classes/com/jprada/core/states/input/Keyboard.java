package com.jprada.core.states.input;

import com.jogamp.newt.event.KeyListener;
import com.jprada.core.entity.GameCharacter;

public abstract class Keyboard implements KeyListener{
	
	protected static final int DOWN_KEY = 0;
	protected static final int UP_KEY = 1;
	protected static final int LEFT_KEY = 2;
	protected static final int RIGHT_KEY = 3;
	
	protected GameCharacter player; 
	
	protected boolean[] keyStates = new boolean[4];
	
	
	public void flushStates() {
		for(int i=0; i< 4; i++) {
			keyStates[i] = false;
		}
	}
	
	
}
