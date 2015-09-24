package com.jprada.core.graphics;

import com.jogamp.opengl.util.texture.Texture;


public class SpriteFrame {

	private Texture texture;

	private float u;
	private float v;
	private float u2;
	private float v2;

	private float regionWidth;
	private float regionHeight;

    private float duration;


	public SpriteFrame(Texture texture) {
		this(texture, 0, 0, texture.getImageWidth(), texture.getImageHeight());
	}

	public SpriteFrame(Texture texture, int x, int y, float width, float height) {
		this.texture = texture;
        this.set(x, y, width, height);
	}


    public SpriteFrame(Texture texture, int x, int y, int width, int height, int animFrameDuration) {
        this.texture = texture;
        this.duration = animFrameDuration;
        this.set(x, y, width, height);

    }

	public SpriteFrame(Texture texture, float u, float v, float u2, float v2,
                       float regionWidth, float regionHeight) {
		this.texture = texture;
		this.u = u;
		this.v = v;
		this.regionWidth = regionWidth;
		this.regionHeight = regionHeight;

		this.u2 = u2;
		this.v2 = v2;
	}

	private void set(float x, float y, float width, float height) {

		float y2 = (texture.getHeight() - y) - height;
		float invTexWidth = 1f / texture.getWidth();
		float invTexHeight = 1f / texture.getHeight();

		this.u = (x * invTexWidth);
		this.v = (y2 * invTexHeight);

		this.u2 = (x + width) * invTexWidth;
		this.v2 = (y2 + height) * invTexHeight;


		this.regionHeight = Math.round(Math.abs(v2 - v) * texture.getHeight());
		this.regionWidth = Math.round(Math.abs(u2 - u) * texture.getWidth());
		
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public float getU() {
		return u;
	}

	public void setU(float u) {
		this.u = u;
	}

	public float getV() {
		return v;
	}

	public void setV(float v) {
		this.v = v;
	}

	public float getU2() {
		return u2;
	}

	public void setU2(float u2) {
		this.u2 = u2;
	}

	public float getV2() {
		return v2;
	}

	public void setV2(float v2) {
		this.v2 = v2;
	}

	public float getRegionWidth() {
		return regionWidth;
	}

	public void setRegionWidth(float regionWidth) {
		this.regionWidth = regionWidth;
	}

	public float getRegionHeight() {
		return regionHeight;
	}

	public void setRegionHeight(float regionHeight) {
		this.regionHeight = regionHeight;
	}

	@Override
	public boolean equals(Object obj) {
		SpriteFrame texReg = null;

		if (obj instanceof SpriteFrame) {
			texReg = (SpriteFrame) obj;

			if (texReg.getTexture().equals(this.getTexture())
					&& this.regionWidth == texReg.regionWidth
					&& this.regionHeight == texReg.regionHeight
					&& this.u == texReg.u && this.u2 == texReg.u2
					&& this.v == texReg.v && this.v2 == texReg.v2) {
				return true;
			}

		}

		return false;
	}

    @Override
    public int hashCode() {
        int result = texture.hashCode();
        result = 31 * result + (u != +0.0f ? Float.floatToIntBits(u) : 0);
        result = 31 * result + (v != +0.0f ? Float.floatToIntBits(v) : 0);
        result = 31 * result + (u2 != +0.0f ? Float.floatToIntBits(u2) : 0);
        result = 31 * result + (v2 != +0.0f ? Float.floatToIntBits(v2) : 0);
        result = 31 * result + (regionWidth != +0.0f ? Float.floatToIntBits(regionWidth) : 0);
        result = 31 * result + (regionHeight != +0.0f ? Float.floatToIntBits(regionHeight) : 0);
        return result;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }
}
