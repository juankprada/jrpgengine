package com.jprada.core.graphics;

import java.nio.FloatBuffer;
import java.util.Map;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

import com.jogamp.opengl.math.Matrix4;

/**
 * Created by Juan Camilo Prada on 25/06/2014.
 */
public class ShaderProgram {

    public final int program;
    public final int vertex;
    public final int fragment;
    protected String log;

    public int in_position_location;
    public int in_color_location;
    public int in_text_coord_location;

    private static FloatBuffer fbuf16;


    public ShaderProgram(GL gl, String vertexSource, String fragmentSource) {
        this(gl, vertexSource, fragmentSource, null);
    }


    public ShaderProgram( GL gl, String vertexSource, String fragmentSource, Map<Integer, String> attributes) {
        GL2 gl2 = gl.getGL2();
        int errorCheckValue = gl2.glGetError();

        //compile the String source
        vertex = compileShader(gl, vertexSource, GL2.GL_VERTEX_SHADER);
        fragment = compileShader(gl, fragmentSource, GL2.GL_FRAGMENT_SHADER);


        //create the program
        program = gl.getGL2().glCreateProgram();

        //attach the shaders
        gl2.glAttachShader(program, vertex);
        gl2.glAttachShader(program, fragment);

        //bind the attrib locations for GLSL 120
        if (attributes != null) {
            for (Map.Entry<Integer, String> e : attributes.entrySet()) {
                gl2.glBindAttribLocation(program, e.getKey(), e.getValue());
            }
        }

        //link our program
        gl2.glLinkProgram(program);


        gl2.glValidateProgram(program);

        errorCheckValue = gl2.glGetError();
        if (errorCheckValue != gl2.GL_NO_ERROR) {
            System.out.println("ERROR - Could not create the shaders:"
                    + gl.glGetString(errorCheckValue));
            System.exit(-1);

            //detach and delete the shaders which are no longer needed
           disposeShaders(gl);
        }

       int matrix_location = gl2.glGetUniformLocation(program,
                "u_projectionViewMatrix");
       int texture_location = gl2.glGetUniformLocation(program,
                "texture_diffuse");
       in_position_location = gl2.glGetAttribLocation(program,
                "in_Position");
       in_color_location = gl2.glGetAttribLocation(program, "in_Color");
       int in_resolution = gl2.glGetAttribLocation(program, "resolution");
       in_text_coord_location = gl2.glGetAttribLocation(program,
                "in_TextureCoord");



    }

    public void disposeShaders(GL gl) {
        GL2 gl2 = gl.getGL2();
        //detach and delete the shaders which are no longer needed
        gl2.glDetachShader(program, vertex);
        gl2.glDetachShader(program, fragment);
        gl2.glDeleteShader(vertex);
        gl2.glDeleteShader(fragment);
    }



    /** Compile the shader source as the given type and return the shader object ID. */
    protected int compileShader(GL gl, String source, int type) {

        // Get GL object
        GL2 gl2 = gl.getGL2();

        //create a shader object
        int shader = gl2.glCreateShader(type);
        //pass the source string
        gl2.glShaderSource(shader, 1, new String[] {source }, (int[]) null, 0);
        //compile the source
        gl2.glCompileShader(shader);

        return shader;
    }

    protected String getName(int shaderType) {
        if (shaderType == GL2.GL_VERTEX_SHADER)
            return "GL_VERTEX_SHADER";
        if (shaderType == GL2.GL_FRAGMENT_SHADER)
            return "GL_FRAGMENT_SHADER";
        else
            return "shader";
    }


    /**
     * Make this shader the active program.
     */
    public void use(GL gl) {
        gl.getGL2().glUseProgram(program);
    }

    public static void unbind(GL gl) {
        gl.getGL2().glUseProgram(0);
    }

    /**
     * Destroy this shader program.
     */
    public void destroy(GL gl) {
        gl.getGL2().glDeleteProgram(program);
    }


    /**
     * Gets the location of the specified uniform name.
     * @param str the name of the uniform
     * @return the location of the uniform in this program
     */
    public int getUniformLocation(GL gl, String str) {
        return gl.getGL2().glGetUniformLocation(program, str);
    }


    /**
     * Gets the location of the specified attribute name.
     * @param str the name of the uniform
     * @return the location of the uniform in this program
     */
    public int getAttributeLocation(GL gl, String str) {
        return gl.getGL2().glGetAttribLocation(program, str);
    }


    /* ------ UNIFORM SETTERS/GETTERS ------ */

    /**
     * Sets the uniform data at the specified location (the uniform type may be int, bool or sampler2D).
     * @param loc the location of the int/bool/sampler2D uniform
     * @param i the value to set
     */
    public void setUniformi(GL gl, int loc, int i) {
        if (loc==-1) return;
        gl.getGL2().glUniform1i(loc, i);
    }

    /**
     * Sends a 4x4 matrix to the shader program.
     * @param loc the location of the mat4 uniform
     * @param transposed whether the matrix should be transposed
     * @param mat the matrix to send
     */
    public void setUniformMatrix(GL gl, int loc, boolean transposed, Matrix4 mat) {
        GL2 gl2 = gl.getGL2();
        if (loc==-1) return;
//        if (fbuf16 == null)
//            fbuf16 = FloatBuffer.allocate(16);
//        fbuf16.clear();
//        mat.store(fbuf16);
//        fbuf16.flip();
        
        fbuf16 = FloatBuffer.wrap(mat.getMatrix());

        gl2.glUniformMatrix4fv(loc, 1, transposed, fbuf16);
    }


}
