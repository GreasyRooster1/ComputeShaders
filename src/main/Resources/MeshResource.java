package main.Resources;

import main.Mesh.Mesh;
import main.Mesh.MeshLoader;

public class MeshResource extends Resource {
    private Mesh mesh;
    public MeshResource(String path) {
        super(path);
    }

    @Override
    public void load() {
        MeshLoader loader = new MeshLoader(path);
        mesh = loader.load();
    }

    @Override
    public Mesh get() {
        return mesh;
    }
}
