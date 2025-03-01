package main.Resources;

import main.Texture.Texture;

public class TextureResource extends Resource {
    private Texture texture;
    public TextureResource(String path) {
        super(path);
    }

    @Override
    public void load() {
        texture = new Texture(path);
    }

    @Override
    public Texture get() {
        return texture;
    }
}
