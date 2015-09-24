package com.jprada.core.events;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class JythonManager {

	public static ScriptEngine jython;
	
	public static void initJythonInterpreter() {
		JythonManager.jython = new ScriptEngineManager().getEngineByName("python");
		
		try {
			
			jython.eval("from com.jprada.core.events.ScriptInterface import StopActorMovement");
			jython.eval("from com.jprada.core.events.ScriptInterface import SetActorPosition");
			jython.eval("from com.jprada.core.events.ScriptInterface import SetPlayerPosition");
			jython.eval("from com.jprada.core.events.ScriptInterface import Wait");

		} catch (ScriptException e) {
			
			e.printStackTrace();
		}
		
	}
	
}
