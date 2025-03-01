package main.Mesh;

import java.nio.IntBuffer;

public class Mesh {

    private int vao;
    private int vertices;
    private IntBuffer indices;

    public Mesh(int vao, IntBuffer indices,int length) {
        this.vao = vao;
        this.vertices = length;
        this.indices = indices;
    }

    public int getVaoID() {
        return vao;
    }

    public int getVertexCount() {
        return vertices;
    }

    public IntBuffer getIndices() {
        return indices;
    }
}