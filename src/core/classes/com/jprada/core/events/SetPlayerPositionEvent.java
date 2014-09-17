package com.jprada.core.events;

import com.jprada.core.GameWindow;
import com.jprada.core.entity.MapObject;

public class SetPlayerPositionEvent extends SetMapObjectPositionEvent {

	public SetPlayerPositionEvent(float posX, float posY) {
		super(GameWindow.currentGameState.PLAYER, posX, posY);
		
	}
}
