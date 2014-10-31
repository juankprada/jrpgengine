package com.jprada.core.states;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;

import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseListener;
import com.jprada.core.entity.Entity;
import com.jprada.core.entity.GameCharacter;
import com.jprada.core.entity.Actor;

/**
 * Created by Juan Camilo Prada on 25/06/2014.
 */
public abstract class GameState {

	public static GameCharacter PLAYER;	
	public static List<Actor> OBJECTS_LIST = new ArrayList<Actor>();
	public static List<Entity> ENTITIES_LIST = new ArrayList<Entity>();
	public static boolean INPUT_DISABLED = false;
	
	public static void stopAllEntities() {
		for(Entity ent : ENTITIES_LIST) {
			ent.stopMoving();
		}
	}
		
    public abstract void onInit(GL gl);
    public abstract void onUpdate(GL gl);
    public abstract void onRender(GL gl, double interpolation);
    public abstract void onFinish(GL gl);
    
    public abstract KeyListener getKeyListener();
    public abstract MouseListener getMouseListener();
    
    // util methods for scripting engine
    public abstract GameCharacter getPlayer();
}
