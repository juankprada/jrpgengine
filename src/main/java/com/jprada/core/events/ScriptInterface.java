package com.jprada.core.events;

import com.jprada.core.GameWindow;
import com.jprada.core.entity.Actor;

public class ScriptInterface {

	
	public static void SayHello() {
		System.out.println("Hola Mundo");
	}
	
	
	public static void StopActorMovement(Actor actor) {
		GameWindow.eventManager.addEvent(new SetPositionEvents.StopActorMovement(actor));
	}
	
	public static void SetPlayerPosition(float x, float y) {
		 GameWindow.eventManager.addEvent(new SetPositionEvents.SetPlayerPositionEvent(x, y));
	}
	
	
	public static void SetActorPosition(Actor actor, float x, float y) {
		GameWindow.eventManager.addEvent(new SetPositionEvents.SetActorPositionEvent(actor, x, y));
	}
	
	
	public static void Wait(int millis) {
		Wait(millis, true);
	}
	
	public static void Wait(int millis, boolean disableInput) {
		Wait(millis, disableInput, true);
	}
	
	public static void Wait(int millis, boolean disableInput, boolean stopEntities ) {
		 GameWindow.eventManager.addEvent(new WaitEvent(millis, disableInput, stopEntities));
	}
}
