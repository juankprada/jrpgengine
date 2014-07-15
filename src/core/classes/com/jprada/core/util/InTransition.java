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
import java.nio.IntBuffer;



/**
 * 
 * @author Juan Camilo Prada
 */
public class InTransition implements Transition {

	/** The color we are fading from */
	private Color col;

	/* Total time for fade out */
	private int total = 500;

	private int Renderedtexture;

	/** The current timeout value */
	private double value;

	public InTransition() {
		this(Color.BLACK);
	}

	public InTransition(Color fadeFrom) {
		this.col = fadeFrom;
	}

	public void executeTransition(GL gl, int delta, SpriteBatch batch) {
		if (delta > 50) {
			delta = 50;
		}
		
		GL2 gl2 = gl.getGL2();

		// use Math.abs for inverse effect
		float alpha = Math.abs(((float) value) / ((float) total));

		// float alpha = ((float) value) / ((float) total);
		value -= delta;
		gl2.glEnable(GL.GL_BLEND);
		gl2.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);

		// FIXME: Use an empty texture to draw the screen black and fade instead of setting color for every other texture
		batch.begin(gl);
		batch.setRenderColor((col.getRed() / 255) + alpha, (col.getGreen() / 255)
				+ alpha, (col.getBlue() / 255) + alpha, 1);
		

		batch.end(gl);

	}

	private int genFBO(GL gl) {
	    int[] array = new int[1];
	    IntBuffer ib = IntBuffer.wrap(array);
	    gl.glGenFramebuffers(1, ib);
	    return ib.get(0);
	}
	
	private int genTexture(GL gl) {
		final int[] tmp = new int[1];
	    gl.glGenTextures(1, tmp, 0);
	    return tmp[0];
	}
	
	@Override
	public void init(GL gl, SpriteBatch batch) {
		value = 0;

		this.Renderedtexture = genTexture(gl);
		gl.glBindTexture(GL.GL_TEXTURE_2D, Renderedtexture);
		gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGB, 1024, 768, 0, GL.GL_RGB, GL.GL_UNSIGNED_BYTE, null);
		
		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
		
//		GL gl = batch.getGl();
//		
//		
//		this.GLFrameBufferId = genFBO(gl);
//		
//		gl.glBindFramebuffer(GL.GL_FRAMEBUFFER, GLFrameBufferId);
//		
//		
//		
//		
//		this.Renderedtexture = genTexture(gl);
//		gl.glBindTexture(GL.GL_TEXTURE_2D, Renderedtexture);
//		gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGB, 1024, 768, 0, GL.GL_RGB, GL.GL_UNSIGNED_BYTE, null);
//		
//		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
//		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
//		
//		// Set "renderedTexture" as our colour attachement #0
//		gl.glFramebufferTexture2D(GL.GL_FRAMEBUFFER, GL.GL_COLOR_ATTACHMENT0, GL.GL_TEXTURE_2D, Renderedtexture, 0);
//		
//		//(GL.GL_FRAMEBUFFER, GL.GL_COLOR_ATTACHMENT0, Renderedtexture, 0);
//		 
//		gl.glBindFramebuffer(GL.GL_FRAMEBUFFER, 0);
		
	}

	@Override
	public boolean transitionComplete() {
		return Math.abs(value) >= total;
	}
}

// ~ Formatted by Jindent --- http://www.jindent.com
