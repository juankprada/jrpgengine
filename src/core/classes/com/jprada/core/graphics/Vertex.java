package com.jprada.core.graphics;

/**
 * Created with IntelliJ IDEA. User: juankprada Date: 10/3/12 Time: 9:29 PM To
 * change this template use File | Settings | File Templates.
 */
public class Vertex {

	private float[] xyzw = new float[] { 0f, 0f, 0f, 1f };
	private float[] rgba = new float[] { 1f, 1f, 1f, 1f };
	private float[] st = new float[] { 0f, 0f };

	private float[] out = new float[Vertex.elementCount];
	public static final int elementBytes = 4;

	// Elements per parameter
	public static final int positionElementCount = 4;
	public static final int colorElementCount = 4;
	public static final int textureElementCount = 2;

	// Bytes per parameter
	public static final int positionBytesCount = positionElementCount
			* elementBytes;
	public static final int colorByteCount = colorElementCount * elementBytes;
	public static final int textureByteCount = textureElementCount
			* elementBytes;

	// Byte offsets per parameter
	public static final int positionByteOffset = 0;
	public static final int colorByteOffset = positionByteOffset
			+ positionBytesCount;
	public static final int textureByteOffset = colorByteOffset
			+ colorByteCount;

	// The amount of elements that a vertex has
	public static final int elementCount = positionElementCount
			+ colorElementCount + textureElementCount;

	// the size of a vertex in bytes
	public static final int stride = positionBytesCount + colorByteCount
			+ textureByteCount;

	// Setters
	public void setXY(float x, float y) {
		this.setXYZ(x, y, 0);
	}

	public void setXYZ(float x, float y, float z) {
		this.setXYZW(x, y, z, 1f);
	}

	public void setRGB(float r, float g, float b) {
		this.setRGBA(r, g, b, 1f);
	}

	public void setST(float s, float t) {
		this.st = new float[] { s, t };
	}

	public void setXYZW(float x, float y, float z, float w) {
		this.xyzw = new float[] { x, y, z, w };
	}

	public void setRGBA(float r, float g, float b, float a) {
		this.rgba = new float[] { r, g, b, a };
	}

	// Getters
	public float[] getElements() {
		
		int i = 0;

		// insert XYZW elements
		out[i++] = this.xyzw[0];
		out[i++] = this.xyzw[1];
		out[i++] = this.xyzw[2];
		out[i++] = this.xyzw[3];
		// insert RGBA elements
		out[i++] = this.rgba[0];
		out[i++] = this.rgba[1];
		out[i++] = this.rgba[2];
		out[i++] = this.rgba[3];
		// insert ST elements
		out[i++] = this.st[0];
		out[i++] = this.st[1];

		return out;
	}

	public float[] getXYZW() {
		return new float[] { this.xyzw[0], this.xyzw[1], this.xyzw[2],
				this.xyzw[3] };
	}

	public float[] getRGBA() {
		return new float[] { this.rgba[0], this.rgba[1], this.rgba[2],
				this.rgba[3] };
	}

	public float[] getST() {
		return new float[] { this.st[0], this.st[1] };
	}
}
