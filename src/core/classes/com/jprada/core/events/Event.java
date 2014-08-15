package com.jprada.core.events;

public abstract class Event {
	
		protected boolean executed;
		protected boolean finished;
		protected boolean lockEvent;
		
		public boolean isExecuted() {
			return executed;
		}

		public void setExecuted(boolean executed) {
			this.executed = executed;
		}

		public boolean isLockEvent() {
			return lockEvent;
		}

		public void setLockEvent(boolean lockEvent) {
			this.lockEvent = lockEvent;
		}

		public boolean isFinished() {
			return finished;
		}

		public void setFinished(boolean finished) {
			this.finished = finished;
		}

		public abstract void execute();
}
