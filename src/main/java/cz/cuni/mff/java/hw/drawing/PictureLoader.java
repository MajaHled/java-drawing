package cz.cuni.mff.java.hw.drawing;

import javax.imageio.ImageIO;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PictureLoader {
    public static BufferedImage LoadImage(File imgFile) {
        if (imgFile.exists()) {
            try {
                return ImageIO.read(imgFile);
            } catch (IOException e) {
                return null;
            }
        }
        return null;
    }
    public static int SaveImage(BufferedImage img, File imgFile) {
        try {
            ImageIO.write(img, "png", imgFile); // TODO: other formats
            return 0;
        } catch (IOException e) {
            return 1;
        }
    }
    public static BufferedImage NewImage(int width, int height, Color backgroundColor) {
        var img = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        var g = img.createGraphics();
        g.setColor(backgroundColor);
        g.fillRect(0, 0, img.getWidth(), img.getHeight());
        return img;
    }

    public static FileNameExtensionFilter GetFileExtentionFilter() {
        return new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());
    }
}
