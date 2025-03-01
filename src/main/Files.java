package main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Path;

public class Files {
    public static String loadStringFromFile(String filename) {
        String out;
        try {
            out = java.nio.file.Files.readString(Path.of(filename), Charset.defaultCharset());
        }catch (Exception e) {
            throw new RuntimeException("Could not load shader from files");
        }
        return out;
    }

    public static byte[] loadPixelsFromFile(String path,boolean alpha) {
        BufferedImage img;
        try {
            img = ImageIO.read(new File(path));
        }catch (Exception e) {
            throw new RuntimeException("Could not load image from file");
        }
        return ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
    }
}
