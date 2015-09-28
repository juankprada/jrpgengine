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

/**
 * Created by JuanCamilo on 26/09/2015.
 */
public class SpriteBatch {

    private static final String VertexShaderCode
            = "#version 110\n"
            + "uniform mat4 u_projectionViewMatrix;"
            + "attribute vec4 in_Position;"
            + "attribute vec4 in_Color;"
            + "attribute vec2 in_TextureCoord;"
            + "varying vec4 pass_Color;"
            + "varying vec2 pass_TextureCoord;"
            + "void main() {"
            + "  gl_Position = u_projectionViewMatrix * in_Position;"
            + "  pass_Color = in_Color;"
            + "  pass_TextureCoord = in_TextureCoord;"
            + "}";

    private static final String FragmentShaderCode
            = "#version 110\n"
            + "uniform sampler2D texture_diffuse;"
            + "varying vec4 pass_Color;"
            + "varying vec2 pass_TextureCoord;"
            + "void main() {"
            + "  gl_FragColor = pass_Color * texture2D(texture_diffuse, pass_TextureCoord);"
            + "}";

    private static final int VERTEX_BUFFER_INDEX = 0;
    private static final int INDICES_BUFFER_INDEX = 1;
    private static final int MAX_BUFFERS = 2;

    private static final int MAX_VERTEX = 2000;

    public static int renderCalls = 0;

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

    public SpriteBatch(GL gl) {
        buffers = new int[MAX_BUFFERS];

        gl.getGL2().glGenBuffers(1, buffers, VERTEX_BUFFER_INDEX);
        gl.getGL2().glGenBuffers(1, buffers, INDICES_BUFFER_INDEX);
    }

    public void setup(GL gl) {
        this.setup(gl, new ShaderProgram(gl, VertexShaderCode, FragmentShaderCode));
    }


    public void setup(GL gl, ShaderProgram shaderProgram) {
        this.shaderProgram = shaderProgram;
        this.vertexCount = 0;

        setupTextureRender();

        this.vertices = new float[MAX_VERTEX * elementsInVertex];
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

    private void setupTextureRender() {

        this.vertexInFigure = 4;
        this.elementsInVertex = 8;
        this.elementsInVertexArray = this.elementsInVertex * this.vertexInFigure;
        this.indicesNumber = 6;
        this.vboStride = 32;

        this.vertices = new float[MAX_VERTEX * elementsInVertex];

        int maxFigures = MAX_VERTEX / vertexInFigure;
        int indicesLength = 6 * maxFigures;

        this.indices = new int[indicesLength];

        for (int i = 0, j = 0; i < indicesLength - 5; i += 6, j += 4) {
            indices[i] = j;
            indices[i + 1] = j + 1;
            indices[i + 2] = j + 2;

            indices[i + 3] = j + 2;
            indices[i + 4] = j + 3;
            indices[i + 5] = j;
        }
    }

    private void setupMatrixes(int w, int h) {
        // Sets the projection matrix to Ortho mode
        projectionMatrix.loadIdentity();
        projectionMatrix.makeOrtho(0, w, h, 0, 1, -1);

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


    public void switchTexture(Texture texture) {
        if (currentTexture != null) {
            render();
        }
        currentTexture = texture;
    }


    private static float toRadians(float degree) {
        return (float) (degree * (Math.PI / 180.0f));
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

    public void draw(SpriteFrame region, float posx, float posy) {
        this.draw(region, posx, posy, null);
    }

    public void draw(SpriteFrame region, float posx, float posy, GLColor color) {
        this.draw(region, posx, posy, color, 1.0f, 1.0f);
    }

    public void draw(SpriteFrame region, float posx, float posy, GLColor color, float scaleX, float scaleY) {
        this.draw(region, posx, posy, color, scaleX, scaleY, 0.0f);
    }

    public void draw(SpriteFrame region, float posx, float posy, GLColor color, float scaleX, float scaleY, float angle) {
        float originX = region.getRegionWidth() / 2;
        float originY = region.getRegionHeight() / 2;
        this.draw(region, posx, posy, color, scaleX, scaleY, angle, originX, originY);
    }

    public void draw(SpriteFrame region, float posx, float posy, GLColor color, float scaleX, float scaleY, float angle, float originX, float originY) {
        this.draw(region, posx, posy, color, scaleX, scaleY, angle, originX, originY, 1.0f);
    }

    public void draw(SpriteFrame region, float posx, float posy, GLColor color, float scaleX, float scaleY, float angle, float originX, float originY, float size) {
        if (!drawing) {
            throw new IllegalStateException(
                    "SpriteBatch.begin() must be called before draw ");
        }

        Texture texture = region.getTexture();

        if (texture != currentTexture) {
            switchTexture(texture);
        } else if ((vertexCount) == (MAX_VERTEX - 4)) {
            render();
        }

        if (color == null) {
            color = new GLColor(1f, 1f, 1f, 1f);
        }

        final float worldOriginX = posx + originX;
        final float worldOriginY = posy + originY;
        float fx = -originX;
        float fy = -originY;
        float fx2 = region.getRegionWidth() - originX;
        float fy2 = region.getRegionHeight() - originY;

        if (scaleX != 1 || scaleY != 1) {
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

        if (rotation != 0) {
            float cosV = (float) Math.cos(rotation);
            float sinV = (float) Math.sin(rotation);

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
        int i = vertexElementsCount;

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

        vertexElementsCount += this.elementsInVertexArray;
        vertexCount += this.vertexInFigure;
        indicesCount += 6;
    }

    public void draw(Sprite sprite, float posx, float posy) {
        this.draw(sprite, posx, posy, null);
    }

    public void draw(Sprite sprite, float posx, float posy, GLColor color) {
        this.draw(sprite, posx, posy, color, 1.0f, 1.0f);
    }

    public void draw(Sprite sprite, float posx, float posy, GLColor color, float scaleX, float scaleY) {
        this.draw(sprite, posx, posy, color, scaleX, scaleY, 0.0f);
    }

    public void draw(Sprite sprite, float posx, float posy, GLColor color, float scaleX, float scaleY, float angle) {
        float originX = sprite.getTexture().getImageWidth() / 2;
        float originY = sprite.getTexture().getImageHeight() / 2;
        this.draw(sprite, posx, posy, color, scaleX, scaleY, angle, originX, originY);
    }

    public void draw(Sprite sprite, float posx, float posy, GLColor color, float scaleX, float scaleY, float angle, float originX, float originY) {
        this.draw(sprite.getFrame(0, 0, sprite.getTexture().getImageWidth(), sprite.getTexture().getImageHeight()), posx, posy, color, scaleX, scaleY, angle, originX, originY, 1.0f);
    }


    public void render() {
        if (vertexCount <= 0) {
            return;
        }

        GL gl = this.glInstance;

        FloatBuffer vertexBufferData = FloatBuffer.wrap(this.vertices, 0, this.vertexElementsCount);
        IntBuffer indexBufferData = IntBuffer.wrap(this.indices, 0, this.indicesCount);

        shaderProgram.use(gl);
        shaderProgram.setUniformMatrix(gl, matrix_location, false, MVP);


        gl.getGL2().glDisable(GL2.GL_DEPTH_BUFFER_BIT);
        gl.getGL2().glEnable(GL2.GL_TEXTURE_2D);
        gl.getGL2().glEnable(GL2.GL_BLEND);

        if (this.sfactor == null || this.dfactor == null) {
            gl.getGL2().glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
        } else {
            gl.getGL2().glBlendFunc(this.sfactor, this.dfactor);
        }

        currentTexture.bind(gl);
        gl.glActiveTexture(GL.GL_TEXTURE0);


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


        gl.getGL2().glVertexAttribPointer(in_text_coord_location, 2, GL2.GL_FLOAT, false, this.vboStride, 24);
        gl.getGL2().glEnableVertexAttribArray(in_text_coord_location);
        gl.getGL2().glDrawElements(GL2.GL_TRIANGLES, this.indicesCount, GL2.GL_UNSIGNED_INT, 0L);


        this.vertices = new float[MAX_VERTEX * elementsInVertex];
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
