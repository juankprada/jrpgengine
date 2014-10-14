package com.jprada.core.graphics;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.util.texture.Texture;
import com.jprada.core.GameWindow;
import com.jprada.core.util.GLColor;
import com.jprada.core.util.ResourceManager;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;

/**
 * Created by Juan Camilo Prada on 25/06/2014.
 */
public class SpriteBatch {

    private static final int VERTICES = 0;
    private static final int INDICES = 1;
    private static final int NUM_BUFFERS = 2;
    private static final int MAX_QUADS = 1000;
    private static final int VERTEX_ELEMENTS = 8;
    private static final int VERTEX_ARRAY_ELEMENTS = 32;
    private static final int INDICES_PER_QUAD = 4;

    private static final int VBO_STRIDE = 32;

//    private GLColor renderColor = new GLColor(1, 1, 1, 1);



    private Matrix projectionMatrix = new Matrix();
    private Matrix viewMatrix = new Matrix();
    private Matrix modelMatrix = new Matrix();
    private Matrix MVP = new Matrix();


    // VBO buffers
    private int[] buffers = new int[NUM_BUFFERS];
    private static final int VERTICES_BUFFER = 0;
    private static final int INDICES_BUFFER = 1;

    private ShaderProgram shader;

    private boolean blendingDisabled = false;

    /* Interleaved Vertex Array (VVCCCCTT) */
    private float[]  vertices = new float[MAX_QUADS * VERTEX_ELEMENTS];
    private int numOfVertices;
    private int[] indices;

    private int numOfIndices=0;
    public static int renderCalls=0;
    private int numOfSpritesInBatch=0;

    private int in_position_location;
    private int in_color_location;
    private int in_text_coord_location;

    private int matrix_location;
    private int texture_location;

    private Texture currentTexture = null;

    private boolean drawing = false;

//    public void setRenderColor(GLColor color) {
//        this.renderColor = color;
//    }
//
//    public void setRenderColor(float r, float g, float b, float a) {
//        this.renderColor.setA(a);
//        this.renderColor.setR(r);
//        this.renderColor.setG(g);
//        this.renderColor.setB(b);
//    }

    public void enableBlending() {

    }

    public void disableBlending() {

    }

    public void setup(GL gl, ShaderProgram shader) {
        this.shader = shader;
        numOfVertices = 0;
        numOfIndices = 0;
        numOfSpritesInBatch = 0;

        gl.getGL2().glGenBuffers(1, buffers, VERTICES_BUFFER);
        gl.getGL2().glGenBuffers(1, buffers, INDICES_BUFFER);

        in_position_location = this.shader.getAttributeLocation(gl, "in_Position");
        in_color_location = this.shader.getAttributeLocation(gl, "in_Color");
        in_text_coord_location = this.shader.getAttributeLocation(gl, "in_TextureCoord");

        texture_location = this.shader.getUniformLocation(gl, "texture_diffuse");
        matrix_location = this.shader.getUniformLocation(gl, "u_projectionViewMatrix");

        int len = (MAX_QUADS * 4);
        indices = new int[len];
        int j=0;
        for(int i=0; i< len; i+=4, j+=4) {
            indices[i] = j;
            indices[i + 1] = (j + 1);
            indices[i + 2] = (j + 2);
            indices[i + 3] = (j + 3);
        }

        int w= GameWindow.getWindowWidth();
        int h= GameWindow.getWindowHeight();

        MVP.setIdentity();

        /* Sets the projection matrix to Ortho mode */
        projectionMatrix.setIdentity();
        projectionMatrix.setToOrtho(0, w, h, 0, 1, -1);

        /* Sets the view Matrix */
        viewMatrix.setIdentity();
        OrthoCamera camera = OrthoCamera.getInstance();
        viewMatrix.translate(camera.getPosx(), camera.getPosy(), 0);

        /* Sets the model matrix as the identity */
        modelMatrix.setIdentity();

        Matrix PV = new Matrix();
        PV.setIdentity();
        Matrix.multiply(projectionMatrix, viewMatrix, PV);
        Matrix.multiply(PV, modelMatrix, MVP);
    }

    public void setup(GL gl) {
        setup(gl, setupDefaultShader(gl));

    }


    private ShaderProgram setupDefaultShader(GL gl) {
        try {
            String defaultVertexSh = ResourceManager.loadShader("vertex.glsl");
            String defaultFragmentSh = ResourceManager.loadShader("fragment.glsl");
            return new ShaderProgram(gl, defaultVertexSh, defaultFragmentSh);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        return null;
    }

    public void begin(GL gl) {

        if (drawing)
            throw new IllegalStateException(
                    "you have to call SpriteBatch.end() first");



        drawing = true;
    }

    public void switchTexture(GL gl, Texture texture) {
        if(currentTexture != null) {
            render(gl);
        }
        currentTexture = texture;


    }
    
    public void draw(GL gl, SpriteFrame region, float posx, float posy) {
    	this.draw(gl, region, posx, posy, null);
    }

    public void draw(GL gl, SpriteFrame region, float posx, float posy, GLColor color) {
    	this.draw(gl, region, posx, posy, color, 1.0f, 1.0f);
    }
    
    public void draw(GL gl, SpriteFrame region, float posx, float posy, GLColor color, float scaleX, float scaleY) {
    	this.draw(gl, region, posx, posy, color, scaleX, scaleY, 0.0f);
    }
    
    public void draw(GL gl, SpriteFrame region, float posx, float posy, GLColor color, float scaleX, float scaleY, float angle) {
    	float originX = region.getRegionWidth() / 2;
    	float originY = region.getRegionHeight() / 2;
    	this.draw(gl, region, posx, posy, color, scaleX, scaleY, angle, originX, originY);
    }
    
    public void draw(GL gl, SpriteFrame region, float posx, float posy, GLColor color, float scaleX, float scaleY, float angle, float originX, float originY) {
    	this.draw(gl, region, posx, posy, color, scaleX, scaleY, angle, originX, originY, 1.0f);
    }

    public void draw(GL gl, SpriteFrame region, float posx, float posy, GLColor color, float scaleX, float scaleY, float angle, float originX, float originY, float size) {
        if (!drawing)
            throw new IllegalStateException(
                    "SpriteBatch.begin() must be called before draw ");

        Texture texture = region.getTexture();

        if (texture != currentTexture) {
            switchTexture(gl, texture);
        } else if (numOfVertices >= (MAX_QUADS * VERTEX_ELEMENTS) - 1 ) {
            render(gl);
        }
        
        if(color == null) {
        	color = new GLColor(1f, 1f, 1f, 1f);
        }

        final float worldOriginX = posx + originX;
        final float worldOriginY = posy + originY;
        float fx = -originX;
        float fy = -originY;
        float fx2 = region.getRegionWidth() - originX;
        float fy2 = region.getRegionHeight() - originY;
        
        if(scaleX != 1 || scaleY !=1) {
        	fx *= scaleX;
        	fy *= scaleY;
        	fx2 *= scaleX;
        	fy2 *= scaleY;
        }
        
        final float p1x = fx;
        final float p1y = fy;
        final float p2x = fx;
        final float p2y = fy2;
        final float p3x = fx2;
        final float p3y = fy2;
        final float p4x = fx2;
        final float p4y = fy;
        
        float x1, y1, x2, y2, x3, y3, x4, y4;
        
        float rotation = toRadians(angle);
        
        if(rotation != 0) {
        	float cosV = (float)Math.cos(rotation);
        	float sinV = (float)Math.sin(rotation);
        	
        	x1 = cosV * p1x - sinV * p1y;
        	y1 = sinV * p1x + cosV * p1y;
            
            x2 = cosV * p2x - sinV * p2y;
            y2 = sinV * p2x + cosV * p2y;
            
            x3 = cosV * p3x - sinV * p3y;
            y3 = sinV * p3x + cosV * p3y;
            
            x4 = x1 + (x3 - x2);
            y4 = y3 - (y2 - y1);
        } else {
        	 x1 = p1x;
             y1 = p1y;

             x2 = p2x;
             y2 = p2y;

             x3 = p3x;
             y3 = p3y;

             x4 = p4x;
             y4 = p4y;
        }
        
        
        x1 += worldOriginX;
        y1 += worldOriginY;
        x2 += worldOriginX;
        y2 += worldOriginY;
        x3 += worldOriginX;
        y3 += worldOriginY;
        x4 += worldOriginX;
        y4 += worldOriginY;
        
        
//        final float fx2 = (posx + region.getRegionWidth());
//        final float fy2 = (posy + region.getRegionHeight());

        int i = numOfVertices;

        /* vertex 1 */
        vertices[i++] = x1;
        vertices[i++] = y1;
        vertices[i++] = color.getR();
        vertices[i++] = color.getG();
        vertices[i++] = color.getB();
        vertices[i++] = color.getA();
        vertices[i++] = region.getU();
        vertices[i++] = region.getV2();


         /* vertex 2 */
        vertices[i++] = x2;
        vertices[i++] = y2;
        vertices[i++] = color.getR();
        vertices[i++] = color.getG();
        vertices[i++] = color.getB();
        vertices[i++] = color.getA();
        vertices[i++] = region.getU();
        vertices[i++] = region.getV();


        /* vertex 3 */
        vertices[i++] = x3;
        vertices[i++] = y3;
        vertices[i++] = color.getR();
        vertices[i++] = color.getG();
        vertices[i++] = color.getB();
        vertices[i++] = color.getA();
        vertices[i++] = region.getU2();
        vertices[i++] = region.getV();

         /* vertex 4 */
        vertices[i++] = x4;
        vertices[i++] = y4;
        vertices[i++] = color.getR();
        vertices[i++] = color.getG();
        vertices[i++] = color.getB();
        vertices[i++] = color.getA();
        vertices[i++] = region.getU2();
        vertices[i++] = region.getV2();


        numOfVertices += VERTEX_ARRAY_ELEMENTS;
        numOfIndices += INDICES_PER_QUAD;
        numOfSpritesInBatch++;
    }
    
    
    private static float toRadians(float degree) {
    	return (float)(degree * (Math.PI/180.0f));
    }
    
    public void draw(GL gl, Sprite sprite, float posx, float posy) {
    	this.draw(gl, sprite, posx, posy, null);
    }

    public void draw(GL gl,  Sprite sprite, float posx, float posy, GLColor color) {
    	this.draw(gl, sprite, posx, posy, color, 1.0f, 1.0f);
    }
    
    public void draw(GL gl,  Sprite sprite, float posx, float posy, GLColor color, float scaleX, float scaleY) {
    	this.draw(gl, sprite, posx, posy, color, scaleX, scaleY,  0.0f);
    }
    
    public void draw(GL gl,  Sprite sprite, float posx, float posy, GLColor color, float scaleX, float scaleY, float angle) {
    	float originX = sprite.getTexture().getImageWidth() / 2;
    	float originY = sprite.getTexture().getImageHeight() / 2;
    	this.draw(gl, sprite, posx, posy, color, scaleX, scaleY, angle, originX, originY);
    }
    
    public void draw(GL gl,  Sprite sprite, float posx, float posy, GLColor color, float scaleX, float scaleY, float angle, float originX, float originY) {
    	this.draw(gl, sprite.getFrame(0, 0, sprite.getTexture().getImageWidth(), sprite.getTexture().getImageHeight()), posx, posy, color, scaleX, scaleY, angle, originX, originY, 1.0f);
    }

    public void end(GL gl) {
        if (!drawing)
            throw new IllegalStateException(
                    "you have to call SpriteBatch.begin() first");


        if (vertices.length > 0)
            render(gl);

        
        gl.getGL2().glDisableVertexAttribArray(in_position_location);
        gl.getGL2().glDisableVertexAttribArray(in_color_location);
        gl.getGL2().glDisableVertexAttribArray(in_text_coord_location);
        gl.getGL2().glBindBuffer(GL2.GL_ARRAY_BUFFER, 0);
        gl.getGL2().glBindBuffer(GL2.GL_ELEMENT_ARRAY_BUFFER, 0);


        shader.unbind(gl);
        
        gl.glDisable(GL2.GL_BLEND);
        gl.glDisable(GL2.GL_TEXTURE_2D);
        
        currentTexture = null;


        drawing = false;

        renderCalls = 0;

    }


    public void render(GL gl) {

        if (vertices.length <= 0)
            return;

        gl.getGL2().glEnable(GL2.GL_TEXTURE_2D);
        gl.getGL2().glEnable(GL2.GL_BLEND);
        gl.getGL2().glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);

        if (currentTexture != null) {
            currentTexture.bind(gl);
        }

        float[] vertices = Arrays.copyOfRange(this.vertices, 0, numOfVertices);

        // Float buffer for vertex data
        FloatBuffer realVertex = Buffers.newDirectFloatBuffer(numOfVertices);
        realVertex.put(vertices);
        realVertex.position(0);


        // Int buffer for indices data
        IntBuffer indicesBuffer = Buffers.newDirectIntBuffer(indices.length);
        indicesBuffer.put(indices);
        indicesBuffer.position(0);

        shader.use(gl);
        shader.setUniformMatrix(gl, matrix_location, false, MVP);

        if(currentTexture != null) {
            gl.glActiveTexture(GL.GL_TEXTURE0);
        }

        int numBytes = realVertex.capacity() * Buffers.SIZEOF_FLOAT;

        gl.getGL2().glBindBuffer(GL2.GL_ARRAY_BUFFER, buffers[VERTICES_BUFFER]);
        gl.getGL2().glBindBuffer(GL2.GL_ELEMENT_ARRAY_BUFFER, buffers[INDICES_BUFFER]);

//        gl.getGL2().glEnableClientState(GL2.GL_TEXTURE_COORD_ARRAY);
//        gl.getGL2().glEnableClientState(GL2.GL_NORMAL_ARRAY);
//        gl.getGL2().glEnableClientState(GL2.GL_VERTEX_ARRAY);

        gl.getGL2().glBufferData(GL2.GL_ARRAY_BUFFER, numBytes, realVertex, GL2.GL_STATIC_DRAW);

        gl.getGL2().glVertexAttribPointer(in_position_location, 2, GL2.GL_FLOAT, false, VBO_STRIDE, 0);
        gl.getGL2().glEnableVertexAttribArray(in_position_location);
//
        gl.getGL2().glVertexAttribPointer(in_color_location, 4, GL2.GL_FLOAT, false, VBO_STRIDE, 8);
        gl.getGL2().glEnableVertexAttribArray(in_color_location);

        gl.getGL2().glVertexAttribPointer(in_text_coord_location, 2, GL2.GL_FLOAT, false, VBO_STRIDE, 24);
        gl.getGL2().glEnableVertexAttribArray(in_text_coord_location);


        int bnumBytes = indicesBuffer.capacity() * Buffers.SIZEOF_INT;


        gl.getGL2().glBufferData(GL2.GL_ELEMENT_ARRAY_BUFFER, bnumBytes, indicesBuffer, GL2.GL_STATIC_DRAW);

        gl.getGL2().glDrawElements(GL2.GL_QUADS, numOfIndices, GL2.GL_UNSIGNED_INT, 0L);


//        gl.getGL2().glDisableClientState(GL2.GL_VERTEX_ARRAY);
//        gl.getGL2().glDisableClientState(GL2.GL_NORMAL_ARRAY);
//        gl.getGL2().glDisableClientState(GL2.GL_TEXTURE_COORD_ARRAY);

      

        vertices = new float[MAX_QUADS * VERTEX_ELEMENTS];
        numOfSpritesInBatch = 0;
        numOfIndices = 0;
        numOfVertices = 0;
        renderCalls++;

       
    }

    public void setCurrentTexture() {}



}
