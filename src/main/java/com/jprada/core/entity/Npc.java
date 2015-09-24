package com.jprada.core.entity;

import javax.script.ScriptException;

import com.jprada.core.events.JythonManager;

public class Npc extends GameCharacter {


	private String onInteractScript;
	
	@Override
	public boolean onInteract(Interactable other) {
		
		try {
			JythonManager.jython.put("Me", this);
			JythonManager.jython.put("Other", other);
			JythonManager.jython.eval("StopActorMovement(Me)");
			JythonManager.jython.eval("StopActorMovement(Other)");
			JythonManager.jython.eval(onInteractScript);
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
		
	}

	public String getOnInteractScript() {
		return onInteractScript;
	}

	public void setOnInteractScript(String onInteractScript) {
		this.onInteractScript = onInteractScript;
	}
	

}
