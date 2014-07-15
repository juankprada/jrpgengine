package com.jprada.core.states;

import javax.media.opengl.GL;

import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseListener;

/**
 * Created by Juan Camilo Prada on 25/06/2014.
 */
public interface GameState {

		
    public void onInit(GL gl);
    public void onUpdate(GL gl);
    public void onRender(GL gl, double interpolation);
    public void onFinish(GL gl);
    
    public KeyListener getKeyListener();
    public MouseListener getMouseListener();
}
