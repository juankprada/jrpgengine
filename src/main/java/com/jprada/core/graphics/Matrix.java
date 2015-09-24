package com.jprada.core.graphics;

import java.nio.FloatBuffer;

/**
 * Created with IntelliJ IDEA. User: juankprada Date: 10/3/12 Time: 9:26 PM To
 * change this template use File | Settings | File Templates.
 */
public class Matrix {

	private static final double PI = 3.14159265358979323846;

	private static final float K180PI = 57.295780F;

	// private float mat4[] = new float[16];

	public float m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23,
			m30, m31, m32, m33;

	/**
	 * Construct a new matrix, initialized to the identity.
	 */
	public Matrix() {
		setIdentity();
	}

	public Matrix(final Matrix src) {
		super();
		load(src);
	}

	/**
	 * Returns a string representation of this matrix
	 */
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append(m00).append(' ').append(m10).append(' ').append(m20)
				.append(' ').append(m30).append('\n');
		buf.append(m01).append(' ').append(m11).append(' ').append(m21)
				.append(' ').append(m31).append('\n');
		buf.append(m02).append(' ').append(m12).append(' ').append(m22)
				.append(' ').append(m32).append('\n');
		buf.append(m03).append(' ').append(m13).append(' ').append(m23)
				.append(' ').append(m33).append('\n');
		return buf.toString();
	}

	/**
	 * Set this matrix to be the identity matrix.
	 * 
	 * @return this
	 */
	public Matrix setIdentity() {
		return setIdentity(this);
	}

	/**
	 * Set the given matrix to be the identity matrix.
	 * 
	 * @param m
	 *            The matrix to set to the identity
	 * @return m
	 */
	public static Matrix setIdentity(Matrix m) {
		m.m00 = 1.0f; // 0
		m.m01 = 0.0f; // 1
		m.m02 = 0.0f; // 2
		m.m03 = 0.0f; // 3
		m.m10 = 0.0f; // 4
		m.m11 = 1.0f; // 5
		m.m12 = 0.0f; // 6
		m.m13 = 0.0f; // 7
		m.m20 = 0.0f; // 8
		m.m21 = 0.0f; // 9
		m.m22 = 1.0f; // 10
		m.m23 = 0.0f; // 11
		m.m30 = 0.0f; // 12
		m.m31 = 0.0f; // 13
		m.m32 = 0.0f; // 14
		m.m33 = 1.0f; // 15

		return m;
	}

	public Matrix translate(float x, float y, float z) {

		this.setIdentity();

		this.m30 = x;
		this.m31 = y;
		this.m32 = z;

		return this;

	}

	public Matrix scale(float sx, float sy, float sz) {

		this.setIdentity();

		this.m00 = sx;
		this.m11 = sy;
		this.m22 = sz;

		return this;
	}

	public Matrix rotateX(float degrees) {

		float radians = degreesToRadians(degrees);

		this.setIdentity();

		this.m11 = (float) Math.cos(radians);
		this.m12 = (float) Math.sin(radians);
		this.m20 = -this.m12;
		this.m22 = this.m11;

		return this;
	}

	public Matrix rotateY(float degrees) {

		float radians = degreesToRadians(degrees);

		this.setIdentity();

		this.m00 = (float) Math.cos(radians);
		this.m02 = (float) -Math.sin(radians);
		this.m21 = -this.m02;
		this.m22 = this.m00;

		return this;
	}

	public Matrix rotateZ(float degrees) {

		float radians = degreesToRadians(degrees);

		this.setIdentity();

		this.m00 = (float) Math.cos(radians);
		this.m01 = (float) Math.sin(radians);
		this.m10 = -this.m01;
		this.m11 = this.m00;

		return this;
	}

	public static float degreesToRadians(float deg) {
		return deg * K180PI;
	}

	/**
	 * Set this matrix to 0.
	 * 
	 * @return this
	 */
	public Matrix setZero() {
		return setZero(this);
	}

	/**
	 * Set the given matrix to 0.
	 * 
	 * @param m
	 *            The matrix to set to 0
	 * @return m
	 */
	public static Matrix setZero(Matrix m) {
		m.m00 = 0.0f;
		m.m01 = 0.0f;
		m.m02 = 0.0f;
		m.m03 = 0.0f;
		m.m10 = 0.0f;
		m.m11 = 0.0f;
		m.m12 = 0.0f;
		m.m13 = 0.0f;
		m.m20 = 0.0f;
		m.m21 = 0.0f;
		m.m22 = 0.0f;
		m.m23 = 0.0f;
		m.m30 = 0.0f;
		m.m31 = 0.0f;
		m.m32 = 0.0f;
		m.m33 = 0.0f;

		return m;
	}

	/**
	 * Load from another Matrix
	 * 
	 * @param src
	 *            The source matrix
	 * @return this
	 */
	public Matrix load(Matrix src) {
		return load(src, this);
	}

	/**
	 * Copy the source matrix to the destination matrix
	 * 
	 * @param src
	 *            The source matrix
	 * @param dest
	 *            The destination matrix, or null of a new one is to be created
	 * @return The copied matrix
	 */
	public static Matrix load(Matrix src, Matrix dest) {
		if (dest == null)
			dest = new Matrix();
		dest.m00 = src.m00;
		dest.m01 = src.m01;
		dest.m02 = src.m02;
		dest.m03 = src.m03;
		dest.m10 = src.m10;
		dest.m11 = src.m11;
		dest.m12 = src.m12;
		dest.m13 = src.m13;
		dest.m20 = src.m20;
		dest.m21 = src.m21;
		dest.m22 = src.m22;
		dest.m23 = src.m23;
		dest.m30 = src.m30;
		dest.m31 = src.m31;
		dest.m32 = src.m32;
		dest.m33 = src.m33;

		return dest;
	}

	/**
	 * Load from a float buffer. The buffer stores the matrix in column major
	 * (OpenGL) order.
	 * 
	 * @param buf
	 *            A float buffer to read from
	 * @return this
	 */
	public Matrix load(FloatBuffer buf) {

		m00 = buf.get();
		m01 = buf.get();
		m02 = buf.get();
		m03 = buf.get();
		m10 = buf.get();
		m11 = buf.get();
		m12 = buf.get();
		m13 = buf.get();
		m20 = buf.get();
		m21 = buf.get();
		m22 = buf.get();
		m23 = buf.get();
		m30 = buf.get();
		m31 = buf.get();
		m32 = buf.get();
		m33 = buf.get();

		return this;
	}

	/**
	 * Load from a float buffer. The buffer stores the matrix in row major
	 * (maths) order.
	 * 
	 * @param buf
	 *            A float buffer to read from
	 * @return this
	 */
	public Matrix loadTranspose(FloatBuffer buf) {

		m00 = buf.get();
		m10 = buf.get();
		m20 = buf.get();
		m30 = buf.get();
		m01 = buf.get();
		m11 = buf.get();
		m21 = buf.get();
		m31 = buf.get();
		m02 = buf.get();
		m12 = buf.get();
		m22 = buf.get();
		m32 = buf.get();
		m03 = buf.get();
		m13 = buf.get();
		m23 = buf.get();
		m33 = buf.get();

		return this;
	}

	/**
	 * Store this matrix in a float buffer. The matrix is stored in column major
	 * (openGL) order.
	 * 
	 * @param buf
	 *            The buffer to store this matrix in
	 */
	public Matrix store(FloatBuffer buf) {
		buf.put(m00);
		buf.put(m01);
		buf.put(m02);
		buf.put(m03);
		buf.put(m10);
		buf.put(m11);
		buf.put(m12);
		buf.put(m13);
		buf.put(m20);
		buf.put(m21);
		buf.put(m22);
		buf.put(m23);
		buf.put(m30);
		buf.put(m31);
		buf.put(m32);
		buf.put(m33);
		return this;
	}

	/**
	 * Store this matrix in a float buffer. The matrix is stored in row major
	 * (maths) order.
	 * 
	 * @param buf
	 *            The buffer to store this matrix in
	 */
	public Matrix storeTranspose(FloatBuffer buf) {
		buf.put(m00);
		buf.put(m10);
		buf.put(m20);
		buf.put(m30);
		buf.put(m01);
		buf.put(m11);
		buf.put(m21);
		buf.put(m31);
		buf.put(m02);
		buf.put(m12);
		buf.put(m22);
		buf.put(m32);
		buf.put(m03);
		buf.put(m13);
		buf.put(m23);
		buf.put(m33);
		return this;
	}

	/**
	 * Store the rotation portion of this matrix in a float buffer. The matrix
	 * is stored in column major (openGL) order.
	 * 
	 * @param buf
	 *            The buffer to store this matrix in
	 */
	public Matrix store3f(FloatBuffer buf) {
		buf.put(m00);
		buf.put(m01);
		buf.put(m02);
		buf.put(m10);
		buf.put(m11);
		buf.put(m12);
		buf.put(m20);
		buf.put(m21);
		buf.put(m22);
		return this;
	}

	/**
	 * Add two matrices together and place the result in a third matrix.
	 * 
	 * @param left
	 *            The left source matrix
	 * @param right
	 *            The right source matrix
	 * @param dest
	 *            The destination matrix, or null if a new one is to be created
	 * @return the destination matrix
	 */
	public static Matrix add(Matrix left, Matrix right, Matrix dest) {
		if (dest == null)
			dest = new Matrix();

		dest.m00 = left.m00 + right.m00;
		dest.m01 = left.m01 + right.m01;
		dest.m02 = left.m02 + right.m02;
		dest.m03 = left.m03 + right.m03;
		dest.m10 = left.m10 + right.m10;
		dest.m11 = left.m11 + right.m11;
		dest.m12 = left.m12 + right.m12;
		dest.m13 = left.m13 + right.m13;
		dest.m20 = left.m20 + right.m20;
		dest.m21 = left.m21 + right.m21;
		dest.m22 = left.m22 + right.m22;
		dest.m23 = left.m23 + right.m23;
		dest.m30 = left.m30 + right.m30;
		dest.m31 = left.m31 + right.m31;
		dest.m32 = left.m32 + right.m32;
		dest.m33 = left.m33 + right.m33;

		return dest;
	}

	/**
	 * Subtract the right matrix from the left and place the result in a third
	 * matrix.
	 * 
	 * @param left
	 *            The left source matrix
	 * @param right
	 *            The right source matrix
	 * @param dest
	 *            The destination matrix, or null if a new one is to be created
	 * @return the destination matrix
	 */
	public static Matrix substract(Matrix left, Matrix right, Matrix dest) {
		if (dest == null)
			dest = new Matrix();

		dest.m00 = left.m00 - right.m00;
		dest.m01 = left.m01 - right.m01;
		dest.m02 = left.m02 - right.m02;
		dest.m03 = left.m03 - right.m03;
		dest.m10 = left.m10 - right.m10;
		dest.m11 = left.m11 - right.m11;
		dest.m12 = left.m12 - right.m12;
		dest.m13 = left.m13 - right.m13;
		dest.m20 = left.m20 - right.m20;
		dest.m21 = left.m21 - right.m21;
		dest.m22 = left.m22 - right.m22;
		dest.m23 = left.m23 - right.m23;
		dest.m30 = left.m30 - right.m30;
		dest.m31 = left.m31 - right.m31;
		dest.m32 = left.m32 - right.m32;
		dest.m33 = left.m33 - right.m33;

		return dest;
	}

	/**
	 * Multiply the right matrix by the left and place the result in a third
	 * matrix.
	 * 
	 * @param left
	 *            The left source matrix
	 * @param right
	 *            The right source matrix
	 * @param dest
	 *            The destination matrix, or null if a new one is to be created
	 * @return the destination matrix
	 */
	public static Matrix multiply(Matrix left, Matrix right, Matrix dest) {
		if (dest == null)
			dest = new Matrix();

		float m00 = left.m00 * right.m00 + left.m10 * right.m01 + left.m20
				* right.m02 + left.m30 * right.m03;
		float m01 = left.m01 * right.m00 + left.m11 * right.m01 + left.m21
				* right.m02 + left.m31 * right.m03;
		float m02 = left.m02 * right.m00 + left.m12 * right.m01 + left.m22
				* right.m02 + left.m32 * right.m03;
		float m03 = left.m03 * right.m00 + left.m13 * right.m01 + left.m23
				* right.m02 + left.m33 * right.m03;
		float m10 = left.m00 * right.m10 + left.m10 * right.m11 + left.m20
				* right.m12 + left.m30 * right.m13;
		float m11 = left.m01 * right.m10 + left.m11 * right.m11 + left.m21
				* right.m12 + left.m31 * right.m13;
		float m12 = left.m02 * right.m10 + left.m12 * right.m11 + left.m22
				* right.m12 + left.m32 * right.m13;
		float m13 = left.m03 * right.m10 + left.m13 * right.m11 + left.m23
				* right.m12 + left.m33 * right.m13;
		float m20 = left.m00 * right.m20 + left.m10 * right.m21 + left.m20
				* right.m22 + left.m30 * right.m23;
		float m21 = left.m01 * right.m20 + left.m11 * right.m21 + left.m21
				* right.m22 + left.m31 * right.m23;
		float m22 = left.m02 * right.m20 + left.m12 * right.m21 + left.m22
				* right.m22 + left.m32 * right.m23;
		float m23 = left.m03 * right.m20 + left.m13 * right.m21 + left.m23
				* right.m22 + left.m33 * right.m23;
		float m30 = left.m00 * right.m30 + left.m10 * right.m31 + left.m20
				* right.m32 + left.m30 * right.m33;
		float m31 = left.m01 * right.m30 + left.m11 * right.m31 + left.m21
				* right.m32 + left.m31 * right.m33;
		float m32 = left.m02 * right.m30 + left.m12 * right.m31 + left.m22
				* right.m32 + left.m32 * right.m33;
		float m33 = left.m03 * right.m30 + left.m13 * right.m31 + left.m23
				* right.m32 + left.m33 * right.m33;

		dest.m00 = m00;
		dest.m01 = m01;
		dest.m02 = m02;
		dest.m03 = m03;
		dest.m10 = m10;
		dest.m11 = m11;
		dest.m12 = m12;
		dest.m13 = m13;
		dest.m20 = m20;
		dest.m21 = m21;
		dest.m22 = m22;
		dest.m23 = m23;
		dest.m30 = m30;
		dest.m31 = m31;
		dest.m32 = m32;
		dest.m33 = m33;

		return dest;
	}

	public void set(Matrix matrix) {
		this.m00 = matrix.m00;
		this.m10 = matrix.m10;
		this.m20 = matrix.m20;
		this.m30 = matrix.m30;
		this.m01 = matrix.m01;
		this.m11 = matrix.m11;
		this.m21 = matrix.m21;
		this.m31 = matrix.m31;
		this.m02 = matrix.m02;
		this.m12 = matrix.m12;
		this.m22 = matrix.m22;
		this.m32 = matrix.m32;
		this.m03 = matrix.m03;
		this.m13 = matrix.m13;
		this.m23 = matrix.m23;
		this.m33 = matrix.m33;
	}

	/**
	 * Transpose this matrix
	 * 
	 * @return this
	 */
	public Matrix transpose() {
		return transpose(this);
	}

	/**
	 * Transpose this matrix and place the result in another matrix
	 * 
	 * @param dest
	 *            The destination matrix or null if a new matrix is to be
	 *            created
	 * @return the transposed matrix
	 */
	public Matrix transpose(Matrix dest) {
		return transpose(this, dest);
	}

	/**
	 * Transpose the source matrix and place the result in the destination
	 * matrix
	 * 
	 * @param src
	 *            The source matrix
	 * @param dest
	 *            The destination matrix or null if a new matrix is to be
	 *            created
	 * @return the transposed matrix
	 */
	public static Matrix transpose(Matrix src, Matrix dest) {
		if (dest == null)
			dest = new Matrix();
		float m00 = src.m00;
		float m01 = src.m10;
		float m02 = src.m20;
		float m03 = src.m30;
		float m10 = src.m01;
		float m11 = src.m11;
		float m12 = src.m21;
		float m13 = src.m31;
		float m20 = src.m02;
		float m21 = src.m12;
		float m22 = src.m22;
		float m23 = src.m32;
		float m30 = src.m03;
		float m31 = src.m13;
		float m32 = src.m23;
		float m33 = src.m33;

		dest.m00 = m00;
		dest.m01 = m01;
		dest.m02 = m02;
		dest.m03 = m03;
		dest.m10 = m10;
		dest.m11 = m11;
		dest.m12 = m12;
		dest.m13 = m13;
		dest.m20 = m20;
		dest.m21 = m21;
		dest.m22 = m22;
		dest.m23 = m23;
		dest.m30 = m30;
		dest.m31 = m31;
		dest.m32 = m32;
		dest.m33 = m33;

		return dest;
	}

	/**
	 * @return the determinant of the matrix
	 */
	public float determinant() {
		float f = m00
				* ((m11 * m22 * m33 + m12 * m23 * m31 + m13 * m21 * m32) - m13
						* m22 * m31 - m11 * m23 * m32 - m12 * m21 * m33);
		f -= m01
				* ((m10 * m22 * m33 + m12 * m23 * m30 + m13 * m20 * m32) - m13
						* m22 * m30 - m10 * m23 * m32 - m12 * m20 * m33);
		f += m02
				* ((m10 * m21 * m33 + m11 * m23 * m30 + m13 * m20 * m31) - m13
						* m21 * m30 - m10 * m23 * m31 - m11 * m20 * m33);
		f -= m03
				* ((m10 * m21 * m32 + m11 * m22 * m30 + m12 * m20 * m31) - m12
						* m21 * m30 - m10 * m22 * m31 - m11 * m20 * m32);
		return f;
	}

	/**
	 * Calculate the determinant of a 3x3 matrix
	 * 
	 * @return result
	 */

	private static float determinant3x3(float t00, float t01, float t02,
			float t10, float t11, float t12, float t20, float t21, float t22) {
		return t00 * (t11 * t22 - t12 * t21) + t01 * (t12 * t20 - t10 * t22)
				+ t02 * (t10 * t21 - t11 * t20);
	}

	/**
	 * Invert this matrix
	 * 
	 * @return this if successful, null otherwise
	 */
	public Matrix invert() {
		return invert(this, this);
	}

	/**
	 * Invert the source matrix and put the result in the destination
	 * 
	 * @param src
	 *            The source matrix
	 * @param dest
	 *            The destination matrix, or null if a new matrix is to be
	 *            created
	 * @return The inverted matrix if successful, null otherwise
	 */
	public static Matrix invert(Matrix src, Matrix dest) {
		float determinant = src.determinant();

		if (determinant != 0) {
			/*
			 * m00 m01 m02 m03 m10 m11 m12 m13 m20 m21 m22 m23 m30 m31 m32 m33
			 */
			if (dest == null)
				dest = new Matrix();
			float determinant_inv = 1f / determinant;

			// first row
			float t00 = determinant3x3(src.m11, src.m12, src.m13, src.m21,
					src.m22, src.m23, src.m31, src.m32, src.m33);
			float t01 = -determinant3x3(src.m10, src.m12, src.m13, src.m20,
					src.m22, src.m23, src.m30, src.m32, src.m33);
			float t02 = determinant3x3(src.m10, src.m11, src.m13, src.m20,
					src.m21, src.m23, src.m30, src.m31, src.m33);
			float t03 = -determinant3x3(src.m10, src.m11, src.m12, src.m20,
					src.m21, src.m22, src.m30, src.m31, src.m32);
			// second row
			float t10 = -determinant3x3(src.m01, src.m02, src.m03, src.m21,
					src.m22, src.m23, src.m31, src.m32, src.m33);
			float t11 = determinant3x3(src.m00, src.m02, src.m03, src.m20,
					src.m22, src.m23, src.m30, src.m32, src.m33);
			float t12 = -determinant3x3(src.m00, src.m01, src.m03, src.m20,
					src.m21, src.m23, src.m30, src.m31, src.m33);
			float t13 = determinant3x3(src.m00, src.m01, src.m02, src.m20,
					src.m21, src.m22, src.m30, src.m31, src.m32);
			// third row
			float t20 = determinant3x3(src.m01, src.m02, src.m03, src.m11,
					src.m12, src.m13, src.m31, src.m32, src.m33);
			float t21 = -determinant3x3(src.m00, src.m02, src.m03, src.m10,
					src.m12, src.m13, src.m30, src.m32, src.m33);
			float t22 = determinant3x3(src.m00, src.m01, src.m03, src.m10,
					src.m11, src.m13, src.m30, src.m31, src.m33);
			float t23 = -determinant3x3(src.m00, src.m01, src.m02, src.m10,
					src.m11, src.m12, src.m30, src.m31, src.m32);
			// fourth row
			float t30 = -determinant3x3(src.m01, src.m02, src.m03, src.m11,
					src.m12, src.m13, src.m21, src.m22, src.m23);
			float t31 = determinant3x3(src.m00, src.m02, src.m03, src.m10,
					src.m12, src.m13, src.m20, src.m22, src.m23);
			float t32 = -determinant3x3(src.m00, src.m01, src.m03, src.m10,
					src.m11, src.m13, src.m20, src.m21, src.m23);
			float t33 = determinant3x3(src.m00, src.m01, src.m02, src.m10,
					src.m11, src.m12, src.m20, src.m21, src.m22);

			// transpose and divide by the determinant
			dest.m00 = t00 * determinant_inv;
			dest.m11 = t11 * determinant_inv;
			dest.m22 = t22 * determinant_inv;
			dest.m33 = t33 * determinant_inv;
			dest.m01 = t10 * determinant_inv;
			dest.m10 = t01 * determinant_inv;
			dest.m20 = t02 * determinant_inv;
			dest.m02 = t20 * determinant_inv;
			dest.m12 = t21 * determinant_inv;
			dest.m21 = t12 * determinant_inv;
			dest.m03 = t30 * determinant_inv;
			dest.m30 = t03 * determinant_inv;
			dest.m13 = t31 * determinant_inv;
			dest.m31 = t13 * determinant_inv;
			dest.m32 = t23 * determinant_inv;
			dest.m23 = t32 * determinant_inv;
			return dest;
		} else
			return null;
	}

	/**
	 * Negate this matrix
	 * 
	 * @return this
	 */
	public Matrix negate() {
		return negate(this);
	}

	/**
	 * Negate this matrix and place the result in a destination matrix.
	 * 
	 * @param dest
	 *            The destination matrix, or null if a new matrix is to be
	 *            created
	 * @return the negated matrix
	 */
	public Matrix negate(Matrix dest) {
		return negate(this, this);
	}

	/**
	 * Negate this matrix and place the result in a destination matrix.
	 * 
	 * @param src
	 *            The source matrix
	 * @param dest
	 *            The destination matrix, or null if a new matrix is to be
	 *            created
	 * @return The negated matrix
	 */
	public static Matrix negate(Matrix src, Matrix dest) {
		if (dest == null)
			dest = new Matrix();

		dest.m00 = -src.m00;
		dest.m01 = -src.m01;
		dest.m02 = -src.m02;
		dest.m03 = -src.m03;
		dest.m10 = -src.m10;
		dest.m11 = -src.m11;
		dest.m12 = -src.m12;
		dest.m13 = -src.m13;
		dest.m20 = -src.m20;
		dest.m21 = -src.m21;
		dest.m22 = -src.m22;
		dest.m23 = -src.m23;
		dest.m30 = -src.m30;
		dest.m31 = -src.m31;
		dest.m32 = -src.m32;
		dest.m33 = -src.m33;

		return dest;
	}

	@Deprecated
	public void setToOrtho2D(float x, float y, float width, float height) {
		setToOrtho(x, width, y, height, 1, -1);
	}

	@Deprecated
	public void setToOrtho2D(float x, float y, float width, float height,
			float near, float far) {
		setToOrtho(x, x + width, y, y + height, near, far);

	}

	public void setToOrtho(float left, float right, float bottom, float top,
			float near, float far) {
		this.setIdentity();

		this.m00 = 2.0f / (right - left);
		this.m01 = 0;
		this.m02 = 0;
		this.m03 = 0;

		this.m10 = 0;
		this.m11 = 2.0f / (top - bottom);
		this.m12 = 0;
		this.m13 = 0;

		this.m20 = 0;
		this.m21 = 0;
		this.m22 = 2.0f / (far - near);
		this.m23 = 0;

		this.m30 = -(right + left) / (right - left);

		this.m31 = -(top + bottom) / (top - bottom);
		this.m32 = -(far + near) / (far - near);
		this.m33 = 1;

	}

	public void setToProjection(float near, float far, float fov,
			float aspectRatio) {
		this.setIdentity();

		float y_scale = this.coTangent(degreesToRadians(fov / 2f));
		float x_scale = y_scale / aspectRatio;
		float frustum_length = far - near;

		this.m00 = x_scale;
		this.m11 = y_scale;
		this.m22 = -((far + near) / frustum_length);
		this.m32 = -1;
		this.m23 = -((2 * near * far) / frustum_length);
	}

	// private float degreesToRadians(float degrees) {
	//
	// return degrees * (float) (PI / 180d);
	//
	// }

	private float coTangent(float angle) {
		return (float) (1f / Math.tan(angle));
	}

}
