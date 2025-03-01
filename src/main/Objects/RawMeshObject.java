package main.Objects;

import main.Camera;
import main.Mesh.Mesh;
import main.Shaders.ProjectionShader;
import main.Shaders.Shader;
import main.Texture.Texture;
import org.joml.Matrix4f;

import static org.lwjgl.opengl.GL30.*;

public class RawMeshObject {
    public Mesh mesh;
    public Shader shader;

    public RawMeshObject(Mesh mesh, Shader shader) {
        this.mesh = mesh;
        this.shader = shader;
    }

    public void render(){
        shader.use();
        glBindVertexArray(mesh.getVaoID());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glDrawElements(GL_TRIANGLES, mesh.getVertexCount(), GL_UNSIGNED_INT,0);
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);
        shader.disable();
    }
}
