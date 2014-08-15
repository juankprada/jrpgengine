package com.jprada.core.events;

import com.jprada.core.entity.MapObject;

public class SetMapObjectPositionEvent extends Event {

	private float posX;
	private float posY;
	private MapObject obj;
	
	public SetMapObjectPositionEvent(MapObject obj, float posX, float posY) {
		this.posX = posX;
		this.posY = posY;
		this.obj = obj;
	}
	
	@Override
	public void execute() {
		if(!this.isExecuted()) {
			obj.setPosition(this.posX, this.posY);
			this.executed = true;
		} else {
			this.finished = true;
		}
	}

}
