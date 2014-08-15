package com.jprada.core.states;

import java.util.List;

import javax.media.opengl.GL;

import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseListener;
import com.jprada.core.entity.Entity;
import com.jprada.core.entity.GameCharacter;
import com.jprada.core.entity.MapObject;

/**
 * Created by Juan Camilo Prada on 25/06/2014.
 */
public abstract class GameState {

	public static Character PLAYER;
	public static List<MapObject> OBJECTS_LIST;
	public static List<Entity> ENTITIES_LIST;
	
	
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
