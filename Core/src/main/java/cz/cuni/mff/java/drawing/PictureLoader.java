package cz.cuni.mff.java.drawing;

import javax.imageio.ImageIO;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Utility class for loading, saving, and creating image files.
 * <p>
 * This class provides static methods to handle common image operations such as loading an image from a file,
 * saving an image to a file, creating a new image with a specified background color, and retrieving a file
 * filter for image file types.
 * </p>
 * <p>
 * Supported image formats include BMP, GIF, JPEG, JPG, and PNG. The class uses {@link ImageIO} for reading and
 * writing images, and supports basic error handling for I/O operations.
 * </p>
 */
public class PictureLoader {
    private static final String[] VALID_EXTS = new String[]{"bmp", "gif", "jpeg", "jpg", "png"};

    /**
     * Loads an image from the specified file.
     * <p>
     * This method checks if the file exists and has a valid extension before attempting to read the image.
     * If the file does not exist or has an unsupported extension, or if an I/O error occurs, the method returns {@code null}.
     * </p>
     *
     * @param imgFile the file from which the image should be loaded
     * @return the loaded {@link BufferedImage}, or {@code null} if the file does not exist, has an invalid extension,
     *         or an I/O error occurs
     */
    public static BufferedImage LoadImage(File imgFile) {
        if (imgFile.exists()) {
            try {
                String filename = imgFile.getName();
                String extension = filename.substring(filename.lastIndexOf(".")+1);
                if (!Arrays.asList(VALID_EXTS).contains(extension))
                    return null;
                return ImageIO.read(imgFile);
            } catch (IOException e) {
                return null;
            }
        }
        return null;
    }

    /**
     * Saves the specified image to the given file.
     * <p>
     * This method determines the appropriate image format based on the file extension. If the file extension
     * is not valid, the image is saved in PNG format by default. Returns {@code 0} if the image is successfully saved,
     * or {@code 1} if an I/O error occurs.
     * </p>
     *
     * @param img the {@link BufferedImage} to save
     * @param imgFile the file to which the image should be saved
     * @return {@code 0} if the image is saved successfully, or {@code 1} if an I/O error occurs
     */
    public static int SaveImage(BufferedImage img, File imgFile) {
        try {
            String filename = imgFile.getName();
            String extension = filename.substring(filename.lastIndexOf(".")+1);
            if (Arrays.asList(VALID_EXTS).contains(extension))
                ImageIO.write(img, extension, imgFile);
            else
                ImageIO.write(img, "png", imgFile);
            return 0;
        } catch (IOException e) {
            return 1;
        }
    }

    /**
     * Creates a new image with the specified width, height, and background color.
     * <p>
     * The image is created with the type {@link BufferedImage#TYPE_3BYTE_BGR} and filled with the provided
     * background color.
     * </p>
     *
     * @param width the width of the new image
     * @param height the height of the new image
     * @param backgroundColor the background color to fill the image with
     * @return a new {@link BufferedImage} with the specified dimensions and background color
     */
    public static BufferedImage NewImage(int width, int height, Color backgroundColor) {
        var img = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        var g = img.createGraphics();
        g.setColor(backgroundColor);
        g.fillRect(0, 0, img.getWidth(), img.getHeight());
        return img;
    }

    /**
     * Provides a file filter for supported image files.
     * <p>
     * The filter includes all file extensions supported by this class and can
     * be used in file choosers to filter out unsupported files.
     * </p>
     *
     * @return a {@link FileNameExtensionFilter} for supported image files
     */
    public static FileNameExtensionFilter GetFileExtensionFilter() {
        return new FileNameExtensionFilter("Images", VALID_EXTS);
    }
}
