package com.jprada.core.entity;

import javax.media.opengl.GL;
import javax.script.ScriptException;

import com.jprada.core.events.JythonManager;
import com.jprada.core.graphics.SpriteBatch;

public class Npc extends GameCharacter {


	private String onInteractScript;
	
	@Override
	public boolean onInteract(Interactable other) {
		
		try {
			JythonManager.jython.put("other", other);
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
