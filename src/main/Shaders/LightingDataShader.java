package main.Shaders;

import main.Camera;

public class LightingDataShader extends ProjectionShader {
    public LightingDataShader(String fragPath) {
        super("resources/shaders/lighting/lighting.vert",fragPath);
    }

    @Override
    protected void bindAttributes() {
        defaultBinds();

    }

    @Override
    public void passCamera(Camera camera) {
        super.passCamera(camera);
        setUniform3f("cameraPos",camera.getPosition());
    }
}
