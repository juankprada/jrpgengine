package com.jprada.core.graphics;

import com.jogamp.common.nio.Buffers;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;

/**
 * Created with IntelliJ IDEA. User: juankprada Date: 10/3/12 Time: 11:09 PM To
 * change this template use File | Settings | File Templates.
 */
public class Mesh {

	private Vertex[] vertices;
	private int numberOfIndices;
	// private byte[] indices;

	private int[] buffers = new int[2];
	private static final int VERTICES_BUFFER = 0;
	private static final int INDICES_BUFFER = 1;

	private FloatBuffer verticesBuffer;
	private IntBuffer indicesBuffer;

	private static final byte indices[] = { 0, 1, 2, 3 };

	public Mesh(GL gl) {

		// bufferVertices =

		gl.getGL2().glGenBuffers(1, buffers, VERTICES_BUFFER);
		gl.getGL2().glGenBuffers(1, buffers, INDICES_BUFFER);

	}

	public Vertex[] getVertices() {
		return vertices;
	}

	public void setVertices(List<Vertex> vertices) {
		this.vertices = vertices.toArray(new Vertex[vertices.size()]);

		int indicesSize = vertices.size();
		int size = indicesSize * Vertex.elementCount;
		verticesBuffer = Buffers.newDirectFloatBuffer(size); // FloatBuffer.allocate(size);

		for (int i = 0; i < indicesSize; i++) {
			verticesBuffer.put(this.vertices[i].getElements());
		}
		verticesBuffer.rewind();

		indicesBuffer = Buffers.newDirectIntBuffer(indicesSize); // ByteBuffer.allocate(indicesSize);
																	// //BufferUtils.createByteBuffer(indicesSize);
		numberOfIndices = 0;
		for (int i = 0; i < indicesSize; i++) {
			try {
				indicesBuffer.put(i);
			} catch (java.nio.BufferOverflowException ex) {
				ex.printStackTrace();
			}
			numberOfIndices++;
		}
		indicesBuffer.rewind();

	}

	public byte[] getIndices() {
		return indices;
	}

	public IntBuffer getIndicesBuffer() {
		return indicesBuffer;
	}

	public void setIndicesBuffer(IntBuffer indicesBuffer) {
		this.indicesBuffer = indicesBuffer;
	}

	public void render(ShaderProgram shader, GL gl) {

        GL2 gl2 = gl.getGL2();

		int numBytes = verticesBuffer.capacity() * Buffers.SIZEOF_FLOAT;

		gl2.glBindBuffer(GL2.GL_ARRAY_BUFFER, buffers[VERTICES_BUFFER]); // gl2.glBindBuffer(gl2.GL_ARRAY_BUFFER,
																			// bufferVertices);
		gl2.glBufferData(GL2.GL_ARRAY_BUFFER, numBytes, verticesBuffer,
				GL2.GL_STREAM_DRAW);


        int in_position_location = shader.getAttributeLocation(gl, "in_Position");
        int in_color_location = shader.getAttributeLocation(gl, "in_Color");
        int in_text_coord_location = shader.getAttributeLocation(gl, "in_TextureCoord");
//
//        int in_position_location = shader.in_position_location;
//        int in_color_location = shader.in_color_location;
//        int in_text_coord_location = shader.in_text_coord_location;

		gl2.glVertexAttribPointer(in_position_location,
				Vertex.positionElementCount, GL2.GL_FLOAT, false,
				Vertex.stride, Vertex.positionByteOffset);
		gl2.glEnableVertexAttribArray(in_position_location);
//
		gl2.glVertexAttribPointer(in_color_location,
				Vertex.colorElementCount, GL2.GL_FLOAT, false, Vertex.stride,
				Vertex.colorByteOffset);
		gl2.glEnableVertexAttribArray(in_color_location);
//
		gl2.glVertexAttribPointer(in_text_coord_location,
				Vertex.textureElementCount, GL2.GL_FLOAT, false, Vertex.stride,
				Vertex.textureByteOffset);
		gl2.glEnableVertexAttribArray(in_text_coord_location);

		int bnumBytes = indicesBuffer.capacity() * Buffers.SIZEOF_INT;
		gl2.glBindBuffer(GL2.GL_ELEMENT_ARRAY_BUFFER, buffers[INDICES_BUFFER]);
		gl2.glBufferData(GL2.GL_ELEMENT_ARRAY_BUFFER, bnumBytes, indicesBuffer,
				GL2.GL_STREAM_DRAW);

		// Actual drawing occurs here

		gl2.glDrawElements(GL2.GL_QUADS, numberOfIndices, GL2.GL_UNSIGNED_INT, 0L);
//		gl2.glDrawArrays(GL2.GL_QUADS, 0, numberOfIndices);

		gl2.glBindBuffer(GL2.GL_ELEMENT_ARRAY_BUFFER, 0);
		gl2.glDisableVertexAttribArray(in_position_location);
		gl2.glDisableVertexAttribArray(in_color_location);
		gl2.glDisableVertexAttribArray(in_text_coord_location);

	}
}
