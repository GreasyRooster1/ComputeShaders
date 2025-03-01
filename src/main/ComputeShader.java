package main;

import main.Shaders.Shader;
import static org.lwjgl.opengl.GL45.*;
import static org.lwjgl.opengl.GL45.GL_READ_ONLY;

import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class ComputeShader {
    private int ID;
    private int texture;

    private int texWidth;
    private int texHeight;

    public ComputeShader(String computePath) {
        String computeSource = Files.loadStringFromFile(computePath);

        int compute = glCreateShader(GL_COMPUTE_SHADER);
        glShaderSource(compute,computeSource);
        glCompileShader(compute);

        ID = glCreateProgram();
        glAttachShader(ID, compute);
        glLinkProgram(ID);
    }

    public void setupTexture(int width, int height) {
        texWidth = width;
        texHeight = height;
        texture = glGenTextures();
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, texture);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA32F, texWidth, texHeight, 0, GL_RGBA,
                GL_FLOAT, NULL);

        glBindImageTexture(0, texture, 0, false, 0, GL_READ_ONLY, GL_RGBA32F);
    }

    public void execute(){
        glUseProgram(ID);
        glDispatchCompute(texWidth, texHeight, 1);

// make sure writing to image has finished before read
        glMemoryBarrier(GL_SHADER_IMAGE_ACCESS_BARRIER_BIT);
    }

    public void bindTexture(){
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, texture);
    }
}
