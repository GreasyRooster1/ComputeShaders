package main;

import main.Mesh.Mesh;
import main.Objects.MeshObject;
import main.Objects.SkyBox;
import main.Resources.ResourceLoader;
import main.Shaders.ParticleShader;
import main.Texture.CubeMap;
import org.joml.Vector3f;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.opengl.GL45.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Main {

    private long window;

    public static int WIDTH = 800;
    public static int HEIGHT = 600;

    public int currentFrame = 0;

    public void run(){
        init();
        loop();

        Callbacks.glfwFreeCallbacks(window);
        GLFW.glfwDestroyWindow(window);

        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free();
    }

    /*
     *Initialize GLFW and create the window
     */
    public void init(){
        GLFWErrorCallback.createPrint(System.err).set();

        if(!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);
        GLFW.glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
        GLFW.glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 5);

        window = GLFW.glfwCreateWindow(WIDTH, HEIGHT, "Cube", NULL, NULL);
        if(window == NULL) {
            throw new IllegalStateException("Unable to create GLFW Window");
        }

        GLFW.glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {});

        try(MemoryStack stack = stackPush()){
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            GLFW.glfwGetWindowSize(window, pWidth, pHeight);

            GLFWVidMode vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());

            GLFW.glfwSetWindowPos(window,(vidmode.width() - pWidth.get(0)) / 2,(vidmode.height() - pHeight.get(0)) / 2);

            GLFW.glfwMakeContextCurrent(window);
            GLFW.glfwSwapInterval(1);
            GLFW.glfwShowWindow(window);
        }
    }

    public void loop(){
        GL.createCapabilities();

        glEnable(GL_DEPTH_TEST);

        ResourceLoader resources = new ResourceLoader("resources");
        resources.loadAllResources();

        Camera camera = new Camera(new Vector3f(20f,1f,0f),new Vector3f(0f,0f,0f));

        CubeMap cubeMap = new CubeMap("resources/textures/065.png");
        SkyBox skybox = new SkyBox(cubeMap);

        MeshObject monkey = new MeshObject((Mesh) resources.getResource("models/monkey.obj"), new ParticleShader());
        //monkey.texture = (Texture) resources.getResource("textures/monkey.png");

//        RawMeshObject computeQuad = new RawMeshObject((Mesh) resources.getResource("models/quad.obj"), new ScreenShader());
//
//        TextureComputeShader computeShader = new TextureComputeShader("resources/shaders/compute/particleTest.comp");
//        computeShader.setupTexture(1000,1000);

        ComputeShader simComputeShader = new ComputeShader("resources/shaders/compute/particleTest.comp");
        ParticleSimulation simulation = new ParticleSimulation(simComputeShader,new Vector3f(200,200,200));
        simulation.calcParticles();
        simulation.initBuffers();

        glClearColor(0.0f,0.0f,0.0f,1.0f);

        while(!GLFW.glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);

//            camera.setPosition(new Vector3f(
//                    (float)cos(GLFW.glfwGetTime())*4,
//                    (float)sin(GLFW.glfwGetTime()*1.1f)*4,
//                    (float)sin(GLFW.glfwGetTime())*4
//            ));

            simulation.render(camera);
            //monkey.render(camera);

//            computeShader.bind();
//            computeShader.setUniform1f("t",currentFrame);
//            computeShader.execute();
//            computeShader.bindTexture();
//            computeQuad.render();



            GLFW.glfwSwapBuffers(window);
            GLFW.glfwPollEvents();

            Monitor.tick();
            currentFrame++;
        }
    }

    public static void main(String[] args){
        new Main().run();
    }
}