package main.Shaders;

public class ColorShader extends Shader {
    public ColorShader() {
        super("resources/shaders/color/color.vert", "resources/shaders/color/color.frag");
    }

    @Override
    protected void bindAttributes() {
        bindAttribute(0, "position");
    }

}
