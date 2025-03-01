package main.Texture;

import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL30.*;

public class TextureLoader {
    private static ByteBuffer loadData(String path,IntBuffer width,IntBuffer height){
        ByteBuffer buffer = null;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);

            File file = new File(path);
            String filePath = file.getAbsolutePath();
            STBImage.stbi_set_flip_vertically_on_load(true);
            buffer = STBImage.stbi_load(filePath, w, h, channels, 4);
            if (buffer == null) {
                throw new Exception("Can't load file " + path + " " + STBImage.stbi_failure_reason());
            }
            width.put(w.get());
            height.put(h.get());
        }catch(Exception e){
            e.printStackTrace();
        }
        return buffer;
    }

    public static int loadTexture(String path){
        IntBuffer w = IntBuffer.allocate(1);
        IntBuffer h = IntBuffer.allocate(1);
        ByteBuffer buffer = loadData(path,w,h);

        int id = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, id);
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, w.get(0), h.get(0), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

        glGenerateMipmap(GL_TEXTURE_2D);
        STBImage.stbi_image_free(buffer);
        glBindTexture(GL_TEXTURE_2D,0);

        return id;
    }

    public static int loadCubeMap(String[] faces){
        int c=0;
        int id=glGenTextures();
        glBindTexture(GL_TEXTURE_CUBE_MAP, id);

        for(String path:faces) {
            IntBuffer w = IntBuffer.allocate(1);
            IntBuffer h = IntBuffer.allocate(1);
            ByteBuffer buffer = loadData(path, w, h);
            System.out.println(w.get(0)+","+h.get(0));
            glTexImage2D(GL_TEXTURE_CUBE_MAP_POSITIVE_X + c,
                    0, GL_RGBA, w.get(0), h.get(0), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer
            );
            c++;
        }
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_R, GL_CLAMP_TO_EDGE);

        return id;
    }
}
