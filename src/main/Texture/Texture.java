package main.Texture;

public class Texture {
    public  int textureID;

    public Texture(String texturePath) {
        load(texturePath);
    }

    public Texture() {
    }

    public void load(String texturePath){
        textureID = TextureLoader.loadTexture(texturePath);
    }
    public void setTextureID(int textureID) {
        this.textureID = textureID;
    }
}
