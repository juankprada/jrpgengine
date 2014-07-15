package com.jprada.core.graphics;

import com.jogamp.opengl.util.texture.Texture;
import com.jprada.core.util.GLColor;

import javax.media.opengl.GL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Sprite {

    private String path;
    private Texture texture;
    private GLColor renderColor;

    private float rotation;

    private float scaleX;
    private float scaleY;

	public Sprite(String path) {
		this.path = path;
		this.texture = TextureManager.loadTexture(path);
        this.rotation = 0;
        this.scaleX = 1;
        this.scaleY = 1;
	}

    public SpriteFrame getFrame(int xGrid, int yGrid, float width, float height) {
        return new SpriteFrame(this.texture, xGrid, yGrid, width, height);
    }

    public List<SpriteFrame> getFrames(int frameWidth, int frameHeight, int frameDuration) {

        int imgHeight = this.texture.getHeight();
        int imgWidth = this.texture.getWidth();

        int horizontalFrames = imgWidth / frameWidth;
        int verticalFrames = imgHeight / frameHeight;

        int totalFrames = horizontalFrames * verticalFrames;

        List<SpriteFrame> frames = new ArrayList<SpriteFrame>();

        int currentRow = 0;
        for(int i = 0; i< totalFrames; i++) {

            int x = 0;
            int y = 0;

            // Check if in currentRow
            if(i >= (horizontalFrames * (currentRow + 1))) {
                currentRow++;
            }
            x = (i - (horizontalFrames * (currentRow))) * frameWidth;
            y = currentRow * frameHeight;



            frames.add(new SpriteFrame(this.texture, x, y, frameWidth, frameHeight, frameDuration));

        }

        return frames;

    }
	
//	public void Draw( GL gl, SpriteBatch2 batch, int posX, int posY) {
//
//        batch.draw(gl, texture, posX, posY);
//
//	}

    public void setPath(String path) {
        this.path = path;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public GLColor getRenderColor() {
        return renderColor;
    }

    public void setRenderColor(GLColor renderColor) {
        this.renderColor = renderColor;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public float getScaleX() {
        return scaleX;
    }

    public void setScaleX(float scaleX) {
        if(scaleX < 0) { scaleX = 0; }
        this.scaleX = scaleX;
    }

    public float getScaleY() {
        return scaleY;
    }

    public void setScaleY(float scaleY) {
        if(scaleY < 0) { scaleY = 0; }
        this.scaleY = scaleY;
    }

    public String getPath() {
		return path;
	}
}
