package main.Shaders;

public class TextureShader extends ProjectionShader {
    public TextureShader() {
        super("resources/shaders/texture.frag");
    }

    @Override
    protected void bindAttributes() {
        defaultBinds();
    }

}
