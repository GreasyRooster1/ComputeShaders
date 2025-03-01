package main.Texture;

public class CubeMap extends Texture{
    public CubeMap(String texturePath) {
        super(texturePath);
    }
    public CubeMap(String[] textures) {
        setTextureID(TextureLoader.loadCubeMap(textures));
    }

    @Override
    public void load(String texturePath) {
        setTextureID(TextureLoader.loadCubeMap(new String[]{
                texturePath,
                texturePath,
                texturePath,
                texturePath,
                texturePath,
                texturePath
        }));
    }
}
