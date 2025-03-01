package main.Objects;

import main.Camera;
import main.Mesh.Mesh;
import main.Shaders.SkyBoxShader;
import main.Texture.CubeMap;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import static main.Mesh.MeshBuilder.createMesh;
import static org.lwjgl.opengl.GL30.*;

public class SkyBox extends MeshObject{
    private CubeMap cubeMap;
    private int vao;
    private int vbo;
    public SkyBox(CubeMap cubeMap) {
        super(getMesh(), new SkyBoxShader());
        this.cubeMap = cubeMap;
    }



    public void render(Camera camera){
        shader.passView(new Matrix4f(new Matrix3f(camera.getView())));
        shader.passProjection(camera.getProjection());
        shader.use();
        //glDepthFunc(GL_LEQUAL);
        //glBindVertexArray(mesh.getVaoID());
        //glEnableVertexAttribArray(0);
        //glBindTexture(GL_TEXTURE_CUBE_MAP, cubeMap.textureID);
        //glDrawElements(GL_TRIANGLES, mesh.getVertexCount(), GL_UNSIGNED_INT,0);
        //glDisableVertexAttribArray(0);
        //glDepthFunc(GL_LESS);
        shader.disable();
        //shader.disable();
    }

    private static Mesh getMesh() {
        int[] ind = new int[getMeshVertices().length];
        for (int i = 0; i < getMeshVertices().length; i++) {
            ind[i] =i;
        }
        return createMesh(getMeshVertices(),ind);
    }

    private static float[] getMeshVertices(){
        return new float[]{
                // positions
                -1.0f,  1.0f, -1.0f,
                -1.0f, -1.0f, -1.0f,
                1.0f, -1.0f, -1.0f,
                1.0f, -1.0f, -1.0f,
                1.0f,  1.0f, -1.0f,
                -1.0f,  1.0f, -1.0f,

                -1.0f, -1.0f,  1.0f,
                -1.0f, -1.0f, -1.0f,
                -1.0f,  1.0f, -1.0f,
                -1.0f,  1.0f, -1.0f,
                -1.0f,  1.0f,  1.0f,
                -1.0f, -1.0f,  1.0f,

                1.0f, -1.0f, -1.0f,
                1.0f, -1.0f,  1.0f,
                1.0f,  1.0f,  1.0f,
                1.0f,  1.0f,  1.0f,
                1.0f,  1.0f, -1.0f,
                1.0f, -1.0f, -1.0f,

                -1.0f, -1.0f,  1.0f,
                -1.0f,  1.0f,  1.0f,
                1.0f,  1.0f,  1.0f,
                1.0f,  1.0f,  1.0f,
                1.0f, -1.0f,  1.0f,
                -1.0f, -1.0f,  1.0f,

                -1.0f,  1.0f, -1.0f,
                1.0f,  1.0f, -1.0f,
                1.0f,  1.0f,  1.0f,
                1.0f,  1.0f,  1.0f,
                -1.0f,  1.0f,  1.0f,
                -1.0f,  1.0f, -1.0f,

                -1.0f, -1.0f, -1.0f,
                -1.0f, -1.0f,  1.0f,
                1.0f, -1.0f, -1.0f,
                1.0f, -1.0f, -1.0f,
                -1.0f, -1.0f,  1.0f,
                1.0f, -1.0f,  1.0f
        };
    }
}
