package main.Shaders;

import main.Files;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.opengl.GL43C.GL_COMPUTE_SHADER;

public abstract class Shader {
    private int programID;
    private int vertexID;
    private int fragmentID;

    private FloatBuffer matrix = BufferUtils.createFloatBuffer(16);

    public Shader(String vertPath, String fragPath){
        String vertexSource = Files.loadStringFromFile(vertPath);
        String fragmentSource = Files.loadStringFromFile(fragPath);

        vertexID = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexID, vertexSource);
        glCompileShader(vertexID);
        checkCompileStatus(vertexID);

        fragmentID = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentID, fragmentSource);
        glCompileShader(fragmentID);
        checkCompileStatus(fragmentID);

        programID = glCreateProgram();
        glAttachShader(programID, vertexID);
        glAttachShader(programID, fragmentID);
        glLinkProgram(programID);

        glDeleteShader(vertexID);
        glDeleteShader(fragmentID);
    }

    private void checkCompileStatus(int shaderId){
        if(glGetShaderi(shaderId, GL_COMPILE_STATUS)==GL_FALSE) {
            System.out.println(glGetShaderInfoLog(shaderId, 1024));
            System.err.println("Couldn't compile the shader");
            System.exit(-1);
        }
    }

    public void use(){
        glUseProgram(programID);
    }

    public void disable(){
        glUseProgram(0);
    }

    public int getUniformLocation(String uniformName){
        return glGetUniformLocation(programID, uniformName);
    }

    protected void bindAttribute(int attribute, String variableName) {
        glBindAttribLocation(programID, attribute, variableName);
    }

    protected abstract void bindAttributes();

    //protected abstract void getAllUniformLocations();

    public void setUniform1f(String uniformName, float value) {
        glUniform1f(getUniformLocation(uniformName), value);
    }
    public void setUniform3f(String uniformName, Vector3f value) {
        glUniform3f(getUniformLocation(uniformName), value.x,value.y,value.z);
    }
    public void setUniform4f(String uniformName, Vector4f value) {
        glUniform4f(getUniformLocation(uniformName), value.x,value.y,value.z,value.w);
    }

    public void setUniformMatrix4f(String uniformName, Matrix4f matrix) {
        FloatBuffer fb = BufferUtils.createFloatBuffer(16);
        matrix.get(fb);
        glUniformMatrix4fv(getUniformLocation(uniformName),false,fb);
    }
}
