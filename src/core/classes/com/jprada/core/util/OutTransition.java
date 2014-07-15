/*
 * To change this template, choose Tools | Templates and open the template in
 * the com.jprada.editor.
 */
package com.jprada.core.util;

// ~--- non-JDK imports --------------------------------------------------------



import com.jprada.core.graphics.SpriteBatch;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import java.awt.*;


/**
 * 
 * @author Juan Camilo Prada
 */
public class OutTransition implements Transition {

	/** The color we are fading from */
	private Color col;

	/* Total time for fade out */
	private int total = 500;

	/** The current timeout value */
	private double value;

	public OutTransition() {
		this(Color.BLACK);
	}

	public OutTransition(Color fadeFrom) {
		this.col = fadeFrom;
	}

	@Override
	public void executeTransition(GL gl, int delay, SpriteBatch batch) {
		if (delay > 50) {
			delay = 50;
		}

		GL2 gl2 = gl.getGL2();
		
		// use Math.abs for inverse effect
		float alpha = ((float) value) / ((float) total);

		// float alpha = ((float) value) / ((float) total);
		value -= delay;
		gl2.glEnable(GL.GL_BLEND);
		gl2.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);

		batch.begin(gl);
		batch.setRenderColor((col.getRed() / 255) + alpha, (col.getGreen() / 255)
				+ alpha, (col.getBlue() / 255) + alpha, 1);
		batch.end(gl);
		// FIXME: Change hard coded vertex
//		gl2.glColor4f((col.getRed() / 255) + alpha, (col.getGreen() / 255)
//				+ alpha, (col.getBlue() / 255) + alpha, 0);
//		gl2.glBegin(GL2.GL_QUADS);
//		gl2.glVertex2f(0, 0);
//		gl2.glVertex2f(0, GameWindow.getWindowHeight());
//		gl2.glVertex2f(GameWindow.getWindowWidth(), GameWindow.getWindowHeight());
//		gl2.glVertex2f(GameWindow.getWindowWidth(), 0);
//		gl2.glEnd();
//		gl2.glDisable(GL.GL_BLEND);
	}

	@Override
	public void init(GL gl, SpriteBatch batch) {
		value = total;
	}

	@Override
	public boolean transitionComplete() {
		return value < 0;
	}
}

// ~ Formatted by Jindent --- http://www.jindent.com
