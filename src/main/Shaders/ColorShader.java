package main.Shaders;

public class ColorShader extends ProjectionShader {
    public ColorShader() {
        super("resources/shaders/universal.vert", "resources/shaders/color/color.frag");
    }

    @Override
    protected void bindAttributes() {
        bindAttribute(0, "position");
    }

}
