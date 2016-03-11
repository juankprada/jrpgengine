package com.jprada.core.graphics;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.math.Matrix4;
import com.jogamp.opengl.util.texture.Texture;
import com.jprada.core.GameWindow;
import com.jprada.core.util.GLColor;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;

/**
 * Created by JuanCamilo on 26/09/2015.
 */
public class LineBatch {

    public static int renderCalls = 0;

    private static final String LineVertexShaderCode
            = // This matrix member variable provides a hook to manipulate
            // the coordinates of the objects that use this vertex shader
            "#version 110\n"
            + "uniform mat4 u_projectionViewMatrix;"
            + "attribute vec4 in_Position;"
            + "attribute vec4 in_Color;"
            + "varying vec4 pass_Color;"
            + "void main() {"
            + "  gl_Position = u_projectionViewMatrix * in_Position;"
            + "  pass_Color = in_Color;"
            + "}";

    private static final String LineFragmentShaderCode
            = "#version 110\n"
            + "varying vec4 pass_Color;"
            + "void main() {"
            + "  gl_FragColor = pass_Color;"
            + "}";

    private static final int VERTEX_BUFFER_INDEX = 0;
    private static final int INDICES_BUFFER_INDEX = 1;
    private static final int MAX_BUFFERS = 2;

    private static final int MAX_VERTEX = 2000;

    private int[] buffers;


    /**
     * Total number of elements in a single vertex
     */
    private int elementsInVertex;

    /**
     * Total number of vertex in a figure E.j. A quad has 4 vertex
     */
    private int vertexInFigure;

    /**
     * Total number of elements in a Figure composed by vertex. E.j. A quad has
     * elementsInVertex * 4
     */
    private int elementsInVertexArray;

    /**
     * Total number of indices to form a Figure
     */
    private int indicesNumber;


    private int vboStride;

    private int[] indices;
    private float[] vertices;

    private ShaderProgram shaderProgram;

    private Matrix4 projectionMatrix = new Matrix4();
    private Matrix4 viewMatrix = new Matrix4();
    private Matrix4 modelMatrix = new Matrix4();
    private Matrix4 MVP = new Matrix4();

    private int in_position_location;
    private int in_color_location;
    private int in_text_coord_location;

    private int matrix_location;
    private int texture_location;

    private GL glInstance;

    private Texture currentTexture = null;
    private boolean drawing = false;

    private int vertexCount;
    private int vertexElementsCount;
    private int indicesCount;

    private Integer sfactor;
    private Integer dfactor;

    public LineBatch(GL gl) {
        buffers = new int[MAX_BUFFERS];

        gl.getGL2().glGenBuffers(1, buffers, VERTEX_BUFFER_INDEX);
        gl.getGL2().glGenBuffers(1, buffers, INDICES_BUFFER_INDEX);
    }


    public void setup(GL gl) {
        this.setup(gl, new ShaderProgram(gl, LineVertexShaderCode, LineFragmentShaderCode));
    }


    public void setup(GL gl, ShaderProgram shaderProgram) {
        this.shaderProgram = shaderProgram;
        this.vertexCount = 0;

        setupLineRender();
        this.indicesCount = 0;
        this.vertexCount = 0;
        this.vertexElementsCount = 0;

        in_position_location = this.shaderProgram.getAttributeLocation(gl, "in_Position");
        in_color_location = this.shaderProgram.getAttributeLocation(gl, "in_Color");
        in_text_coord_location = this.shaderProgram.getAttributeLocation(gl, "in_TextureCoord");

        texture_location = this.shaderProgram.getUniformLocation(gl, "texture_diffuse");
        matrix_location = this.shaderProgram.getUniformLocation(gl, "u_projectionViewMatrix");

        setupMatrixes(GameWindow.getWindowWidth(), GameWindow.getWindowHeight());
    }

    private void setupLineRender() {
        this.vertexInFigure = 2;
        this.elementsInVertex = 6;
        this.elementsInVertexArray = this.elementsInVertex * this.vertexInFigure;
        this.indicesNumber = 2;
        this.vboStride = 24;
        
        // Float byte size is 4 (32 bit)
        
        this.vertices = new float[MAX_VERTEX * elementsInVertex];

        int maxFigures = MAX_VERTEX / vertexInFigure;
        int indicesLength = 2 * maxFigures;

        this.indices = new int[indicesLength];

        
        int j = 0;
        for (int i = 0; i < indicesLength; i += 2, j += 2) {
            indices[i] = j;
            indices[i + 1] = (j + 1);
           
        }
    }


    private void setupMatrixes(int w, int h) {

        // Sets the projection matrix to Ortho mode
        projectionMatrix.loadIdentity();
        //projectionMatrix.makeOrtho(0, w, h, 0, 1, -1);
        projectionMatrix.makeOrtho(0, 640, 480, 0, 1, -1);
        
        // Sets the view Matrix
        viewMatrix.loadIdentity();
        OrthoCamera camera = OrthoCamera.getInstance();
        viewMatrix.translate(camera.getPosx(), camera.getPosy(), 0);

        // Sets the model matrix as the identity
        modelMatrix.loadIdentity();

        projectionMatrix.multMatrix(viewMatrix);
        projectionMatrix.multMatrix(modelMatrix);
        MVP = projectionMatrix;

    }


    public void draw(float x1, float y1, float x2, float y2, GLColor color) {
        if (!drawing) {
            throw new IllegalStateException(
                    "LineBatch.begin() must be called before draw ");
        }

        // Texture texture = region.getTexture();
        if ((vertexCount) == (MAX_VERTEX - 1)) {

            render();
        }

        int i = vertexElementsCount;

        /* vertex 1 */
        vertices[i++] = x1;
        vertices[i++] = y1;
        vertices[i++] = color.getR();
        vertices[i++] = color.getG();
        vertices[i++] = color.getB();
        vertices[i++] = color.getA();
        // vertices[i++] = region.getU();
        // vertices[i++] = region.getV2();

        /* vertex 2 */
        vertices[i++] = x2;
        vertices[i++] = y2;
        vertices[i++] = color.getR();
        vertices[i++] = color.getG();
        vertices[i++] = color.getB();
        vertices[i++] = color.getA();

        vertexElementsCount += this.elementsInVertexArray;
        vertexCount += this.vertexInFigure;
        indicesCount += 2;


    }

    public void begin(GL gl) {

        if (drawing) {
            throw new IllegalStateException("you have to call SpriteBatch.end() first");
        }

        this.glInstance = gl;

        this.drawing = true;

        // Push client attrib bits used by the pipelined quad renderer
        gl.getGL2().glPushClientAttrib((int) GL2.GL_ALL_CLIENT_ATTRIB_BITS);
    }

    public void render() {
        if (vertexCount <= 0) {
            return;
        }

        GL gl = this.glInstance;
        
        
//        System.arraycopy(this.vertices, 0, verticesArray, 0, this.vertexElementsCount);
//        System.arraycopy(this.indices, 0, indicesArray, 0, this.indicesCount);
//        
//        
//        FloatBuffer vertexBufferData = FloatBuffer.wrap(verticesArray, 0, this.vertexElementsCount);
//        IntBuffer indexBufferData = IntBuffer.wrap(indicesArray, 0, this.indicesCount);

        
        FloatBuffer vertexBufferData = FloatBuffer.allocate(this.vertexElementsCount);
        System.arraycopy(this.vertices, 0, vertexBufferData.array(), 0, this.vertexElementsCount);
                
                //FloatBuffer.wrap(verticesArray, 0, this.vertexElementsCount);
        IntBuffer indexBufferData = IntBuffer.allocate(this.indicesCount);
        System.arraycopy(this.indices, 0, indexBufferData.array(), 0, this.indicesCount);
        
        
        shaderProgram.use(gl);
        shaderProgram.setUniformMatrix(gl, matrix_location, false, MVP);


        //    gl.getGL2().glDisable(GL2.GL_DEPTH_BUFFER_BIT);
            //gl.getGL2().glEnable(GL2.GL_TEXTURE_2D);
            //gl.getGL2().glEnable(GL2.GL_BLEND);

//            if (this.sfactor == null || this.dfactor == null) {
//                gl.getGL2().glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
//            } else {
//                gl.getGL2().glBlendFunc(this.sfactor, this.dfactor);
//            }

//           gl.glActiveTexture(GL.GL_TEXTURE0);


        int numBytes = vertexBufferData.capacity() * Buffers.SIZEOF_FLOAT;
        int bnumBytes = indexBufferData.capacity() * Buffers.SIZEOF_INT;

        gl.getGL2().glBindBuffer(GL2.GL_ARRAY_BUFFER, buffers[VERTEX_BUFFER_INDEX]);
        gl.getGL2().glBufferData(GL2.GL_ARRAY_BUFFER, numBytes, vertexBufferData, GL2.GL_STATIC_DRAW);

        gl.getGL2().glBindBuffer(GL2.GL_ELEMENT_ARRAY_BUFFER, buffers[INDICES_BUFFER_INDEX]);
        gl.getGL2().glBufferData(GL2.GL_ELEMENT_ARRAY_BUFFER, bnumBytes, indexBufferData, GL2.GL_STATIC_DRAW);

        gl.getGL2().glVertexAttribPointer(in_position_location, 2, GL2.GL_FLOAT, false, this.vboStride, 0);
        gl.getGL2().glEnableVertexAttribArray(in_position_location);

        gl.getGL2().glVertexAttribPointer(in_color_location, 4, GL2.GL_FLOAT, false, this.vboStride, 8);
        gl.getGL2().glEnableVertexAttribArray(in_color_location);


//       gl.getGL2().glVertexAttribPointer(in_text_coord_location, 2, GL2.GL_FLOAT, false, this.vboStride, 24);
//       gl.getGL2().glEnableVertexAttribArray(in_text_coord_location);
        gl.getGL2().glDrawElements(GL2.GL_LINES, this.indicesCount, GL2.GL_UNSIGNED_INT, 0L);

        
        //this.vertices = new float[MAX_VERTEX * elementsInVertex];
        Arrays.fill(this.vertices, 0.0f);
        this.indicesCount = 0;
        this.vertexCount = 0;
        this.vertexElementsCount = 0;

        vertexBufferData.clear();
        indexBufferData.clear();

        this.renderCalls++;

    }


    public void end() {
        if (!drawing) {
            throw new IllegalStateException("you have to call SpriteBatch.begin() first");
        }

        if (this.vertexCount > 0) {
            render();
        }
        GL gl = this.glInstance;

        // Pop client attrib bits used by the pipelined quad renderer
        gl.getGL2().glPopClientAttrib();

        gl.getGL2().glDisableVertexAttribArray(in_position_location);
        gl.getGL2().glDisableVertexAttribArray(in_color_location);
        gl.getGL2().glDisableVertexAttribArray(in_text_coord_location);
        gl.getGL2().glBindBuffer(GL2.GL_ARRAY_BUFFER, 0);
        gl.getGL2().glBindBuffer(GL2.GL_ELEMENT_ARRAY_BUFFER, 0);

        shaderProgram.unbind(gl);

        gl.glDisable(GL2.GL_BLEND);
        gl.glDisable(GL2.GL_TEXTURE_2D);

        currentTexture = null;

        drawing = false;


        renderCalls = 0;

        this.glInstance = null;
    }

    public void setBlendFunc(int sfactor, int dfactor) {
        render();
        this.sfactor = sfactor;
        this.dfactor = dfactor;

    }
}
