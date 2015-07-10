package com.jprada.core.events;

import com.jprada.core.GameWindow;
import com.jprada.core.entity.Actor;

public interface SetPositionEvents {
	
	
	public class StopActorMovement extends Event {
		
		protected Actor obj;
		
		public StopActorMovement(Actor obj) {
			this.obj = obj;
		}
		
		@Override
		public void execute() {
			if (!this.isExecuted()) {
				obj.setMovingDirection(null);
				this.executed = true;
			} else {
				this.finished = true;
			}
		}
	}
	
	public class StopPlayerEvent extends StopActorMovement {

		public StopPlayerEvent() {
			
			super(GameWindow.currentGameState.PLAYER);

			
		}

		@Override
		public void execute() {
			if (!this.isExecuted()) {
				obj.setMovingDirection(null);
				obj.setSpeed(0, 0);
				GameWindow.currentGameState.getKeyListener().flushStates();
				
			} else {
				this.finished = true;
			}
		}
		
	}
	
	

	public class SetActorPositionEvent extends Event {

		private float posX;
		private float posY;
		private Actor obj;

		public SetActorPositionEvent(Actor obj, float posX, float posY) {
			this.posX = posX;
			this.posY = posY;
			this.obj = obj;
		}

		@Override
		public void execute() {
			if (!this.isExecuted()) {
				System.out.println("Me Ejecutaron");
				obj.setPosition(this.posX, this.posY);
				this.executed = true;
			} else {
				this.finished = true;
			}
		}

	}

	public class SetPlayerPositionEvent extends SetActorPositionEvent {

		public SetPlayerPositionEvent(float posX, float posY) {
			super(GameWindow.currentGameState.PLAYER, posX, posY);

		}
	}

}
