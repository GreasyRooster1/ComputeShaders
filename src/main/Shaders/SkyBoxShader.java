package main.Shaders;

public class SkyBoxShader extends ProjectionShader {
    public SkyBoxShader() {
        super("resources/shaders/skybox/skybox.vert","resources/shaders/skybox/skybox.frag");
    }

    @Override
    protected void bindAttributes() {
        defaultBinds();
    }

}
