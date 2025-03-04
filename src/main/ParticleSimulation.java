package main;

import main.Shaders.ParticleShader;
import main.Shaders.ProjectionShader;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import static java.lang.Math.*;
import static org.lwjgl.opengl.GL45.*;

public class ParticleSimulation {
    private ComputeShader computeShader;
    private ProjectionShader particleShader = new ParticleShader();
    private Vector4f[] positions;
    private Vector4f[] velocities;

    private Vector3f size = new Vector3f();
    private int totalParticles;

    private int mVAO;

    ParticleSimulation(ComputeShader computeShader, Vector3f size) {
        this.computeShader = computeShader;
        this.size = size;
    }

    public void initBuffers(){
        int mPosBuf = glCreateBuffers();
        int mVelBuf = glCreateBuffers();

        glBindBufferBase(GL_SHADER_STORAGE_BUFFER,0,mPosBuf);
        glBufferData(GL_SHADER_STORAGE_BUFFER,getFloatList(positions),GL_DYNAMIC_DRAW);

        glBindBufferBase(GL_SHADER_STORAGE_BUFFER,1,mVelBuf);
        glBufferData(GL_SHADER_STORAGE_BUFFER,getFloatList(velocities),GL_DYNAMIC_DRAW);

        mVAO = glGenVertexArrays();
        glBindVertexArray(mVAO);

        glBindBuffer(GL_ARRAY_BUFFER,mPosBuf);
        glVertexAttribPointer(0, 4, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(0);

        glBindVertexArray(0);

        glPointSize(1f);
    }

    void calcParticles() {
        totalParticles = (int) (size.x*size.y*size.z);
        positions = new Vector4f[totalParticles];
        velocities = new Vector4f[totalParticles];
        float dx = 2f/size.x;
        float dy = 2f/size.y;
        float dz = 2f/size.z;

        int particleIndex = 0;
        for (int x = 0; x < size.x; x++) {
            for (int y = 0; y < size.y; y++) {
                for (int z = 0; z < size.z; z++) {
                    Vector4f p = new Vector4f(dx*x-1f,dy*y-1f,dz*z-1f,1f);
                    Vector4f v = new Vector4f(1,1,2,0);
                    positions[particleIndex]=p;
                    velocities[particleIndex]=v;
                    particleIndex++;
                }
            }
        }
    }

    public void render(Camera camera) {
        executeComputeShader();
        renderParticles(camera);
    }

    private void renderParticles(Camera camera) {
        particleShader.use();
        particleShader.passCamera(camera);
        particleShader.passModel(new Matrix4f());

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        glBindVertexArray(mVAO);
        glDrawArrays(GL_POINTS, 0, totalParticles);
        glBindVertexArray(0);
    }

    private void executeComputeShader() {
        computeShader.bind();
        computeShader.execute(totalParticles,1,1);
    }

    //util
    public float[] getFloatList(Vector4f[] arr){
        float[] floats = new float[arr.length*4];
        for(int i=0;i<arr.length;i++){
            int index = i*4;
            floats[index]=arr[i].x;
            floats[index+1]=arr[i].y;
            floats[index+2]=arr[i].z;
            floats[index+3]=arr[i].w;
        }


        return floats;
    }
}
