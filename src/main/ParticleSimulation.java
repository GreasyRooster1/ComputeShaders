package main;

import main.Shaders.ColorShader;
import main.Shaders.ParticleShader;
import main.Shaders.ProjectionShader;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import static org.lwjgl.opengl.GL45.*;

public class ParticleSimulation {
    private ComputeShader computeShader;
    private ProjectionShader particleShader = new ParticleShader();
    private Vector4f[] positions;

    private Vector3f size = new Vector3f();
    private int totalParticles;

    private int mVAO;

    ParticleSimulation(ComputeShader computeShader, Vector3f size) {
        this.computeShader = computeShader;
        this.size = size;
    }

    public void initBuffers(){
        totalParticles = (int) (size.x*size.y*size.z);
        positions = new Vector4f[totalParticles];

        int mPosBuf = glCreateBuffers();
        int bufferSize = positions.length*Float.SIZE*4;

        glBindBufferBase(GL_ARRAY_BUFFER,0,mPosBuf);
        glBufferData(GL_SHADER_STORAGE_BUFFER,getFloatList(),GL_DYNAMIC_DRAW);

        mVAO = glGenVertexArrays();
        glBindVertexArray(mVAO);

        glBindBuffer(GL_ARRAY_BUFFER,mVAO);
        glVertexAttribPointer(0, 4, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(0);

        glBindVertexArray(0);
    }

    void calcPositions() {
        float dx = 2f/size.x;
        float dy = 2f/size.y;
        float dz = 2f/size.z;

        Vector4f p = new Vector4f();
        int particleIndex = 0;
        for (int x = 0; x < size.x; x++) {
            for (int y = 0; y < size.y; y++) {
                for (int z = 0; z < size.z; z++) {
                    p.x = dx*x-1f;
                    p.y = dy*y-1f;
                    p.z = dz*z-1f;
                    p.w = 1f;
                    positions[particleIndex]=p;
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

        glPointSize(2f);

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
    public float[] getFloatList(){
        float[] floats = new float[positions.length*4];
        for(int i=0;i<positions.length;i++){
            int index = i*4;
            positions[i] = new Vector4f();
            floats[index]=positions[i].x;
            floats[index+1]=positions[i].y;
            floats[index+2]=positions[i].z;
            floats[index+3]=positions[i].w;
        }
        return floats;
    }
}
