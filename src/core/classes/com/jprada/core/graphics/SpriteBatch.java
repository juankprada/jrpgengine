package com.jprada.core.graphics;

import com.jogamp.opengl.util.texture.Texture;
import com.jprada.core.util.ResourceManager;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created By: juankprada Date: 10/3/12 Time: 10:45 PM
 */
public class SpriteBatch {


	private Mesh mesh;
	private Texture currentTexture;
	private int currentTextureId;

	private float zoomLevel;

	private ShaderProgram shaderProgram = null;
	private boolean blending = true;

	private List<Vertex> vertices;
	private boolean drawing = false;

	private int windowWidth;
	private int windowHeight;

	private int numberOfIndices;
	private int renderCalls;
	private int numberOfSpritesInBatch;

	private Matrix projectionMatrix = new Matrix();
	private Matrix viewMatrix = new Matrix();
	private Matrix modelMatrix = new Matrix();
	private Matrix MVP = new Matrix();






    private Matrix camera = new Matrix();
	
	private static final int MAX_VERTEX = 100;

	private float renderColor[] = new float[] { 1f, 1f, 1f, 1f };
	
	public void resetRenderColor() {
		renderColor[0] = 1f;
		renderColor[1] = 1f;
		renderColor[2] = 1f;
		renderColor[3] = 1f;
	}

	public void setRenderColor(float r, float g, float b, float a) {
		renderColor[0] = r;
		renderColor[1] = g;
		renderColor[2] = b;
		renderColor[3] = a;
	}

	
	
	public SpriteBatch(GL gl) {

		numberOfIndices = 0;
		numberOfSpritesInBatch = 0;

		this.mesh = new Mesh(gl);

		vertices = new ArrayList<Vertex>(); 
	}

	public Matrix getCamera() {
		return camera;
	}

	public void setCamera(Matrix camera) {
		this.camera = camera;
	}

	private void setupMatrices(GL gl, int width, int height) {
		

		MVP.setIdentity();

		/* Sets the projection matrix to Ortho mode */
		projectionMatrix.setToOrtho(0, width / zoomLevel, height / zoomLevel, 0, 1, -1);

		/* Sets the view Matrix */
		OrthoCamera camera = OrthoCamera.getInstance();
		viewMatrix.translate(camera.getPosx(), camera.getPosy(), 0);


		/* Sets the model matrix as the identity */
		modelMatrix.setIdentity();


		Matrix PV = new Matrix();
		PV.setIdentity();
		Matrix.multiply(projectionMatrix, viewMatrix, PV);
		Matrix.multiply(PV, modelMatrix, MVP);

        int matrix_location = shaderProgram.getUniformLocation(gl, "u_projectionViewMatrix");
        shaderProgram.setUniformMatrix(gl,matrix_location, false, MVP );

		

//		/*
//		 * TODO: Change this to use my own matrix instead of old OpenGL 1/2
//		 * MAtrix stack
//		 */
//		gl.getGL2().glViewport(0, 0, width, height);
//		gl.getGL2().glMatrixMode(GL2.GL_PROJECTION);
//		gl.getGL2().glLoadIdentity();
//		gl.getGL2().glOrtho(0, width / zoomLevel, height / zoomLevel, 0, 1, -1);
//
//		// coordinate system origin at lower left with width and height same as
//		// the window
//		gl.getGL2().glMatrixMode(GL2.GL_MODELVIEW);
//		gl.getGL2().glLoadIdentity();
		
		

	}

	public void flush(GL gl) {
		renderMesh(gl);
	}


	public void begin(GL gl) {
		if (drawing)
			throw new IllegalStateException(
					"you have to call SpriteBatch.end() first");


		shaderProgram.use(gl);

		setupMatrices(gl, windowWidth, windowHeight);

		numberOfSpritesInBatch = 0;
		currentTexture = null;
		drawing = true;

	}

	public void switchTexture(GL gl, Texture texture) {
		renderMesh(gl);
		currentTexture = texture;
		currentTextureId = 0;

	}
	
	public void switchTexture(GL gl, int textureId) {
		renderMesh(gl);
		currentTexture = null;
		currentTextureId = textureId;

	}

	public void draw(GL gl, SpriteFrame region, float posx, float posy) {
		if (!drawing)
			throw new IllegalStateException(
					"SpriteBatch.begin() must be called before draw ");

		Texture texture = region.getTexture();

		if (texture != currentTexture) {
			switchTexture(gl, texture);
		} else if (vertices.size() >= MAX_VERTEX)
			renderMesh(gl); // if the batch is already filled up

		final float fx2 = (posx + region.getRegionWidth());
		final float fy2 = (posy + region.getRegionHeight());

		Vertex a = new Vertex();
		Vertex b = new Vertex();
		Vertex c = new Vertex();
		Vertex d = new Vertex();

		a.setXY(posx, posy);
		a.setRGBA(renderColor[0], renderColor[1], renderColor[2],renderColor[3]);
		a.setST(region.getU(), region.getV2());

		b.setXY(posx, fy2);
		b.setRGBA(renderColor[0], renderColor[1], renderColor[2], renderColor[3]);
		b.setST(region.getU(), region.getV());

		c.setXY(fx2, fy2);
		c.setRGBA(renderColor[0], renderColor[1], renderColor[2], renderColor[3]);
		c.setST(region.getU2(), region.getV());

		d.setXY(fx2, posy);
		d.setRGBA(renderColor[0], renderColor[1], renderColor[2], renderColor[3]);
		d.setST(region.getU2(), region.getV2());

		vertices.add(a);
		vertices.add(b);
		vertices.add(c);
		vertices.add(d);

		numberOfSpritesInBatch++;
	}



	public void draw(GL gl, Texture texture, float x, float y) {
		if (!drawing)
			throw new IllegalStateException(
					"SpriteBatch.begin() must be called before draw ");

		if (texture != currentTexture) {
			switchTexture(gl, texture);
		} else if (vertices.size() == MAX_VERTEX)
			renderMesh(gl); // if the batch is already filled up

		final float fx2 = x + texture.getWidth();
		final float fy2 = y + texture.getHeight();

		Vertex a = new Vertex();
		Vertex b = new Vertex();
		Vertex c = new Vertex();
		Vertex d = new Vertex();

		a.setXY(x, fy2);
		a.setRGBA(renderColor[0], renderColor[1], renderColor[2], renderColor[3]);
		a.setST(0, 0);

		b.setXY(x, y);
		b.setRGBA(renderColor[0], renderColor[1], renderColor[2], renderColor[3]);
		b.setST(0, 1);

		c.setXY(fx2, y);
		c.setRGBA(renderColor[0], renderColor[1], renderColor[2], renderColor[3]);
		c.setST(1, 1);

		d.setXY(fx2, fy2);
		d.setRGBA(renderColor[0], renderColor[1], renderColor[2], renderColor[3]);
		d.setST(1, 0);

		vertices.add(a);
		vertices.add(b);
		vertices.add(c);
		vertices.add(d);

		numberOfSpritesInBatch++;

	}
	
	
	
	public void draw(GL gl, int textureId, float x, float y, float width, float height) {
		if (!drawing)
			throw new IllegalStateException(
					"SpriteBatch.begin() must be called before draw ");

		switchTexture(gl, textureId);
		
		
		final float fx2 = x + width;
		final float fy2 = y + height;

		Vertex a = new Vertex();
		Vertex b = new Vertex();
		Vertex c = new Vertex();
		Vertex d = new Vertex();

		a.setXY(x, fy2);
		a.setRGBA(renderColor[0], renderColor[1], renderColor[2], renderColor[3]);
		a.setST(0, 0);

		b.setXY(x, y);
		b.setRGBA(renderColor[0], renderColor[1], renderColor[2], renderColor[3]);
		b.setST(0, 1);

		c.setXY(fx2, y);
		c.setRGBA(renderColor[0], renderColor[1], renderColor[2], renderColor[3]);
		c.setST(1, 1);

		d.setXY(fx2, fy2);
		d.setRGBA(renderColor[0], renderColor[1], renderColor[2], renderColor[3]);
		d.setST(1, 0);

		vertices.add(a);
		vertices.add(b);
		vertices.add(c);
		vertices.add(d);

		numberOfSpritesInBatch++;

	}

	
	public void drawStretched(GL gl, Texture texture, float x, float y, float width, float height) {
		if (!drawing)
			throw new IllegalStateException(
					"SpriteBatch.begin() must be called before draw ");

		if (texture != currentTexture) {
			switchTexture(gl, texture);
		} else if (vertices.size() == MAX_VERTEX)
			renderMesh(gl); // if the batch is already filled up

		final float fx2 = x + width;
		final float fy2 = y + height;

		Vertex a = new Vertex();
		Vertex b = new Vertex();
		Vertex c = new Vertex();
		Vertex d = new Vertex();

		a.setXY(x, fy2);
		a.setRGBA(renderColor[0], renderColor[1], renderColor[2], renderColor[3]);
		a.setST(0, 0);

		b.setXY(x, y);
		b.setRGBA(renderColor[0], renderColor[1], renderColor[2], renderColor[3]);
		b.setST(0, 1);

		c.setXY(fx2, y);
		c.setRGBA(renderColor[0], renderColor[1], renderColor[2], renderColor[3]);
		c.setST(1, 1);

		d.setXY(fx2, fy2);
		d.setRGBA(renderColor[0], renderColor[1], renderColor[2], renderColor[3]);
		d.setST(1, 0);

		vertices.add(a);
		vertices.add(b);
		vertices.add(c);
		vertices.add(d);

		numberOfSpritesInBatch++;

	}
	
	public void end(GL gl) {
		if (!drawing)
			throw new IllegalStateException(
					"you have to call SpriteBatch.begin() first");



		if (vertices.size() > 0)
			renderMesh(gl);
		currentTexture = null;
		vertices.clear();

		drawing = false;

		shaderProgram.unbind(gl);

		gl.glDisable(GL2.GL_BLEND);
		gl.glDisable(GL2.GL_TEXTURE_2D);

		numberOfSpritesInBatch = 0;
		
		renderCalls = 0;

	}

	public void renderMesh(GL gl) {
		if (vertices.size() <= 0)
			return;

		GL2 gl2 = gl.getGL2();

		renderCalls++;
		
		gl2.glEnable(GL2.GL_TEXTURE_2D);

		if (!blending) {
			gl2.glDisable(GL2.GL_BLEND);
		} else {
			gl2.glEnable(GL2.GL_BLEND);
			gl2.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
		}

		mesh.setVertices(vertices);
//
		if(currentTexture != null)
			currentTexture.bind(gl);
		else if(currentTextureId != 0)
			gl2.glBindTexture(GL.GL_TEXTURE_2D, currentTextureId);

		mesh.render(shaderProgram, gl);

		vertices.clear();
	}

	
	public void setup( GL gl, int width, int height, float zoomLvl) {
		if (this.shaderProgram == null) {
            try {
                String defaultVertexSh = ResourceManager.loadShader("com/jprada/game/resources/vertex.glsl");
                String defaultFragmentSh = ResourceManager.loadShader("com/jprada/game/resources/fragment.glsl");
                setup(new ShaderProgram(gl, defaultVertexSh, defaultFragmentSh));
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }



        }

		this.windowWidth = width;
		this.windowHeight = height;
		this.zoomLevel = zoomLvl;
	}

	
	private void setup(ShaderProgram shader) {
		shaderProgram = shader;
	}

}
