package com.jprada.core.events;

import com.jprada.core.entity.Actor;

public class SetMapObjectPositionEvent extends Event {

	private float posX;
	private float posY;
	private Actor obj;
	
	public SetMapObjectPositionEvent(Actor obj, float posX, float posY) {
		this.posX = posX;
		this.posY = posY;
		this.obj = obj;
	}
	
	@Override
	public void execute() {
		if(!this.isExecuted()) {
			System.out.println("Me Ejecutaron");
			obj.setPosition(this.posX, this.posY);
			this.executed = true;
		} else {
			this.finished = true;
		}
	}

}
