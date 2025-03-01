package main.Objects;

import main.Camera;
import main.Mesh.Mesh;
import main.Shaders.ProjectionShader;
import main.Texture.Texture;
import org.joml.Matrix4f;

import static org.lwjgl.opengl.GL30.*;

public class MeshObject {
    public Mesh mesh;
    public ProjectionShader shader;
    public Texture texture;

    public MeshObject(Mesh mesh, ProjectionShader shader) {
        this.mesh = mesh;
        this.shader = shader;
    }

    public void render(Camera camera){
        shader.passCamera(camera);
        shader.passModel(new Matrix4f());
        shader.use();
        glBindVertexArray(mesh.getVaoID());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);
        if(texture!=null) {
            glActiveTexture(GL_TEXTURE0);
            glBindTexture(GL_TEXTURE_2D, texture.textureID);
        }
        glDrawElements(GL_TRIANGLES, mesh.getVertexCount(), GL_UNSIGNED_INT,0);
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
        glBindVertexArray(0);
        //shader.disable();
    }
}
