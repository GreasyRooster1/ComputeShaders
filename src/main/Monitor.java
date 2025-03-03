package main;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class Monitor {
    static double deltaTime = 0.0f; // time between current frame and last frame
    static double lastFrame = 0.0f; // time of last frame
    static double frameRate = 0.0f;
    static int fCounter = 0;

    public static void tick() {
        double currentFrame = glfwGetTime();
        deltaTime = currentFrame - lastFrame;
        lastFrame = currentFrame;
        frameRate = 1/deltaTime;
        if(fCounter > frameRate){
            System.out.println("FPS: " + frameRate);
            fCounter = 0;
        } else {
            fCounter++;
        }
    }
}
