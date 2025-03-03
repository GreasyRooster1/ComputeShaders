package main.Shaders;

import org.joml.Vector3f;

public class ParticleShader extends ProjectionShader {

    public ParticleShader() {
        super("resources/shaders/particle/particle.vert", "resources/shaders/particle/particle.frag");
    }

    @Override
    protected void bindAttributes() {
        bindAttribute(0, "position");
    }
}
