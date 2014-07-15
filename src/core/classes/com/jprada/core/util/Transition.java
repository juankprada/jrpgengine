/*
 * To change this template, choose Tools | Templates and open the template in
 * the com.jprada.editor.
 */
package com.jprada.core.util;


import com.jprada.core.graphics.SpriteBatch;

import javax.media.opengl.GL;

/**
 * 
 * @author Juan Camilo Prada
 */
public interface Transition {

	public void executeTransition(GL gl, int delay, SpriteBatch batch);

	public void init(GL gl, SpriteBatch batch);

	public boolean transitionComplete();
}

// ~ Formatted by Jindent --- http://www.jindent.com
