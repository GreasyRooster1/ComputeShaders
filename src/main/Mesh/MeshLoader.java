package main.Mesh;

import org.joml.Vector2f;
import org.joml.Vector3f;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import static main.Mesh.MeshBuilder.createMesh;

public class MeshLoader {
    private final String path;

    public MeshLoader(String path){
        this.path = path;
    }

    public Mesh load(){
        ArrayList<Integer> vertexIndices = new ArrayList<>();
        ArrayList<Integer> uvIndices  = new ArrayList<>();
        ArrayList<Integer> normalIndices  = new ArrayList<>();

        ArrayList<Vector3f> vertices  = new ArrayList<>();
        ArrayList<Vector2f> uvs  = new ArrayList<>();
        ArrayList<Vector3f> normals  = new ArrayList<>();

        File myObj = new File(path);
        Scanner scanner = null;
        try {
            scanner = new Scanner(myObj);
        }catch (FileNotFoundException e) {
            System.out.println(e);
        }
        while (scanner.hasNextLine()) {
            String currentLine = scanner.nextLine();
            String[] words = currentLine.split(" ");
            switch (words[0]) {
                case "vt":
                    Vector2f uv = new Vector2f();
                    uv.x = Float.parseFloat(words[1]);
                    uv.y = Float.parseFloat(words[2]);
                    uvs.add(uv);
                    break;
                case "vn":
                    Vector3f normal = new Vector3f();
                    normal.x = Float.parseFloat(words[1]);
                    normal.y = Float.parseFloat(words[2]);
                    normal.z = Float.parseFloat(words[3]);
                    normals.add(normal);
                    break;
                case "v":
                    Vector3f vertex = new Vector3f();
                    vertex.x = Float.parseFloat(words[1]);
                    vertex.y = Float.parseFloat(words[2]);
                    vertex.z = Float.parseFloat(words[3]);
                    vertices.add(vertex);
                    break;
                case "f":
                    String[] v1 = words[1].split("/");
                    String[] v2 = words[2].split("/");
                    String[] v3 = words[3].split("/");
                    vertexIndices.add(Integer.parseInt(v1[0])-1);
                    vertexIndices.add(Integer.parseInt(v2[0])-1);
                    vertexIndices.add(Integer.parseInt(v3[0])-1);
                    //vertexIndices.add(Integer.parseInt(v1[0])-1);

                    uvIndices.add(Integer.parseInt(v1[1])-1);
                    uvIndices.add(Integer.parseInt(v2[1])-1);
                    uvIndices.add(Integer.parseInt(v3[1])-1);
                    //uvIndices.add(Integer.parseInt(v1[1])-1);

                    normalIndices.add(Integer.parseInt(v1[2])-1);
                    normalIndices.add(Integer.parseInt(v2[2])-1);
                    normalIndices.add(Integer.parseInt(v3[2])-1);
                    //normalIndices.add(Integer.parseInt(v1[2])-1);
                    break;
            }
        }
        float[] outUv = new float[uvIndices.size()*2];
        int cUv =0;
        for (int ind : uvIndices) {
            Vector2f uv = uvs.get(ind);
            outUv[cUv] = uv.x;
            outUv[cUv+1] = uv.y;
            cUv+=2;
        }

        float[] outVertices = new float[vertexIndices.size()*3];
        int cVert = 0;
        for (int ind : vertexIndices) {
            Vector3f vertex = vertices.get(ind);
            outVertices[cVert]=vertex.x;
            outVertices[cVert+1]=vertex.y;
            outVertices[cVert+2]=vertex.z;

            cVert+=3;
        }

        float[] outNormals = new float[normalIndices.size()*3];
        int cNorm = 0;
        for (int ind : normalIndices) {
            Vector3f normal = normals.get(ind);
            outNormals[cNorm]=normal.x;
            outNormals[cNorm+1]=normal.y;
            outNormals[cNorm+2]=normal.z;
            cNorm+=3;
        }

        int[] outIndices = new int[vertexIndices.size()];
        int cInd = 0;
        for (int ind : vertexIndices) {
            outIndices[cInd]=cInd;
            cInd++;
        }
        return createMesh(outVertices,outUv,outNormals,outIndices);
    }
}
