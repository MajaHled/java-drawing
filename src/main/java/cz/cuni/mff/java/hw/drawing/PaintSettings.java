package cz.cuni.mff.java.hw.drawing;

import java.awt.*;

public class PaintSettings {
    public Color mainColor = Color.BLACK;
    public Color backgroundColor = Color.WHITE;

    public static enum DrawShape { RECTANGLE, OVAL, TRIANGLE, STAR }; //TODO custom
    public DrawShape shape = DrawShape.RECTANGLE;
}
