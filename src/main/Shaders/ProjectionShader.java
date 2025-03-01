package main.Shaders;

import main.Camera;
import org.joml.Matrix4f;

public abstract class ProjectionShader extends Shader {

    public ProjectionShader(String vertPath, String fragPath) {
        super(vertPath, fragPath);
    }
    public ProjectionShader(String fragPath) {
        super("resources/shaders/universal.vert", fragPath);
    }

    public void defaultBinds(){
        bindAttribute(0, "position");
        bindAttribute(1,"texCoords");
    }

    public void passCamera(Camera camera){
        setUniformMatrix4f("projection",camera.getProjection());
        setUniformMatrix4f("view",camera.getView());
    }

    public void passModel(Matrix4f model){
        setUniformMatrix4f("model",model);
    }

    public void passProjection(Matrix4f projection){
        setUniformMatrix4f("projection",projection);
    }

    public void passView(Matrix4f view){
        setUniformMatrix4f("view",view);
    }
}
