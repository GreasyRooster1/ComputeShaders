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

        glPointSize(2f);
    }

    void calcParticles() {
        totalParticles = (int) (size.x*size.y*size.z);
        positions = new Vector4f[totalParticles];
        velocities = new Vector4f[totalParticles];
        double deltaDeg = 2*PI/(double)size.x;
        double deltaDist = .9/(double)size.y;
        double deltaH = 1/(double)size.z;

        double angleDif = PI/4;

        int particleIndex = 0;
        for (double deg = 0; deg < 2f*PI; deg+=deltaDeg) {
            for (double dist = .1; dist < 1f; dist+=deltaDist) {
                for (double h = 0; h < 1f; h+=deltaH) {
                    if(particleIndex>=totalParticles){
                        return;
                    }
                    Vector4f p = new Vector4f((float) (cos(deg)*dist),0,(float) (sin(deg)*dist),1);
                    Vector4f v = new Vector4f(0,0,0,0);
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
