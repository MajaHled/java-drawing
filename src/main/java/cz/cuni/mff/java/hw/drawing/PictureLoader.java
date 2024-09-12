package cz.cuni.mff.java.hw.drawing;

import javax.imageio.ImageIO;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PictureLoader {
    private static final ArrayList<String> VALID_EXTS = new ArrayList<>(List.of(new String[]{"bmp", "gif", "jpeg", "jpg", "png"}));
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
            String filename = imgFile.getName();
            String extension = filename.substring(filename.lastIndexOf(".")+1);
            if (VALID_EXTS.contains(extension))
                ImageIO.write(img, extension, imgFile);
            else
                ImageIO.write(img, "png", imgFile);
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

    public static FileNameExtensionFilter GetFileExtensionFilter() {
        return new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());
    }
}
