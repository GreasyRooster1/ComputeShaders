package main;

import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL45.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class ComputeShader {
    private int ID;

    public ComputeShader(String computePath) {
        String computeSource = Files.loadStringFromFile(computePath);

        int compute = glCreateShader(GL_COMPUTE_SHADER);
        glShaderSource(compute,computeSource);
        glCompileShader(compute);

        ID = glCreateProgram();
        glAttachShader(ID, compute);
        glLinkProgram(ID);
    }

    public void bind(){
        glUseProgram(ID);
    }

    public void execute(int sx,int sy,int sz){
        glDispatchCompute(sx, sy, sz);

// make sure writing to image has finished before read
        glMemoryBarrier(GL_SHADER_IMAGE_ACCESS_BARRIER_BIT);
    }

    public int getUniformLocation(String uniformName){
        return glGetUniformLocation(ID, uniformName);
    }

    protected void bindAttribute(int attribute, String variableName) {
        glBindAttribLocation(ID, attribute, variableName);
    }

    public void setUniform1i(String uniformName, int value) {
        glUniform1i(getUniformLocation(uniformName), value);
    }
    public void setUniform1f(String uniformName, float value) {
        glUniform1f(getUniformLocation(uniformName), value);
    }
}
