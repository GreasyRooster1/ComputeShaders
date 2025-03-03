package main.Shaders;

import org.joml.Vector3f;

public class ColorShader extends ProjectionShader {
    private Vector3f color;

    public ColorShader() {
        super("resources/shaders/universal.vert", "resources/shaders/color/color.frag");
        setColor(new Vector3f(1f));
    }

    public ColorShader(Vector3f color) {
        super("resources/shaders/universal.vert", "resources/shaders/color/color.frag");
        setColor(color);
    }

    @Override
    protected void bindAttributes() {
        bindAttribute(0, "position");
    }

    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
        System.out.println(color);
        setUniform3f("color", color);
    }
}
