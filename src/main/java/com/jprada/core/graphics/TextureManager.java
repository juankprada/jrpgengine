package com.jprada.core.graphics;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import com.jprada.core.util.ResourceManager;

public class TextureManager {

	private static Map<String, Texture> loadedTextures = new HashMap<String, Texture>();

	
	public static Texture loadTexture(File image) {
		if (loadedTextures.containsKey(image.getName())) {
			return loadedTextures.get(image.getName());
		}
		
		Texture texture = null;
		try {
			final GL gl = GLContext.getCurrentGL();
			InputStream is = new FileInputStream(image);
		  
			texture = TextureIO.newTexture(is, true,"png");
			texture.setTexParameterf(gl, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
			texture.setTexParameterf(gl, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
			texture.setTexParameterf(gl, GL2.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP_TO_EDGE);
			texture.setTexParameterf(gl, GL2.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP_TO_EDGE);
			 
			loadedTextures.put(image.getName(), texture);
		} catch (GLException | IOException e) {
			e.printStackTrace();
		}

		return texture;
	}
	
	
	public static Texture loadTexture(String textureName) {
		if (loadedTextures.containsKey(textureName)) {
			return loadedTextures.get(textureName);
		}
		final GL gl = GLContext.getCurrentGL();
		Texture texture = null;
		try {
		    
			texture = TextureIO.newTexture(
					ResourceManager.loadSprite(textureName), true,
					"png");
			texture.setTexParameterf(gl, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
			texture.setTexParameterf(gl, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
			texture.setTexParameterf(gl, GL2.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP_TO_EDGE);
			texture.setTexParameterf(gl, GL2.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP_TO_EDGE);
			loadedTextures.put(textureName, texture);
		} catch (GLException | IOException e) {
			e.printStackTrace();
		}

		return texture;
	}

    public static void disposeTexture(GL gl, String textureName) {
        if (loadedTextures.containsKey(textureName)) {
            disposeTexture(gl,loadedTextures.get(textureName));
        }
    }

    public static void disposeTexture(GL gl, Texture texture) {
        texture.destroy(gl);
    }
}
