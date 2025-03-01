package main.Shaders;

public class ScreenShader extends Shader {

    public ScreenShader() {
        super("resources/shaders/screen/screen.vert", "resources/shaders/screen/screen.frag");
    }

    @Override
    protected void bindAttributes() {

    }
}
