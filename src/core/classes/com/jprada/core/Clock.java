package com.jprada.core;

public class Clock {

	private int hours = 0;
	private int minutes = 0;
	private int seconds = 0;

	private double millis = 0;

	private double timeLapsed = 0;
	private double oldTime = 0;

	private boolean reseted = false;

	public void resetClock() {
		this.hours = 0;
		this.minutes = 0;
		this.seconds = 0;
		this.millis = 0;
		this.reseted = true;
	}

	public static double getMilliSeconds() {
		return System.nanoTime() / 1000000.0;
	}

	public void initClock() {
		timeLapsed = getMilliSeconds();
		oldTime = timeLapsed;
		reseted = false;
	}

	public void updateClock() {
		timeLapsed = getMilliSeconds();
		millis += (timeLapsed - oldTime);

		if (timeLapsed - oldTime >= 1000) // A second has passed
		{
			seconds++;

			if (seconds >= 60) {
				seconds = 0;
				minutes++;

			}
			
			if(minutes >= 60) {
				minutes = 0;
				hours++;
			}

		}
		
		oldTime = timeLapsed;
	}

	@Override
	public String toString() {
		
		String hourS;
		String minsS;
		String secsS;
		
		if(hours < 10) { hourS = "0"+hours;	} else { hourS = "" + hours; }
		if(minutes < 10) { minsS = "0"+minutes;	} else { minsS = "" + minutes; }
		if(seconds < 10) { secsS = "0"+seconds;	} else { secsS = "" + seconds; }
		
		return hourS + ":"+minsS+":"+secsS;
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public int getSeconds() {
		return seconds;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}

	public double getMillis() {
		return millis;
	}

	public void setMillis(double millis) {
		this.millis = millis;
	}

	public boolean isReseted() {
		return reseted;
	}

	public void setReseted(boolean reseted) {
		this.reseted = reseted;
	}
	
	
	
}
