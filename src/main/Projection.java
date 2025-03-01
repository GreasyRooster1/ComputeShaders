package main;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import static main.Main.HEIGHT;
import static main.Main.WIDTH;

public class Projection {
    public Matrix4f projection;
    public Matrix4f view;
    public Matrix4f model;

    private float fov;
    private float nearClip;
    private float farClip;

    public Projection(){
        fov = 45f;
        nearClip = .1f;
        farClip = 100f;
        update();
    }
    private void update(){
        projection = new Matrix4f().perspective((float) Math.toRadians(fov), (float)WIDTH/(float)HEIGHT, nearClip, farClip);
        model = new Matrix4f().rotate((float) Math.toRadians(0f), new Vector3f(1f,0f,0f));
        view = new Matrix4f().translate(new Vector3f(0f,0f,-1f));
    }
}
