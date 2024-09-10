package cz.cuni.mff.java.hw.drawing;

import java.awt.*;

public class PaintSettings {
    public Color mainColor, backgroundColor;
    public DrawShape shape;
    public Pen CurrentPen;

    public enum DrawShape { RECTANGLE, OVAL, TRIANGLE, STAR }; //TODO custom

    public PaintSettings() {
        mainColor = Color.BLACK;
        backgroundColor = Color.WHITE;
        shape = DrawShape.RECTANGLE;
        CurrentPen = new TestPen(); //TODO temp
    }

    public PaintSettings(Color mainColor, Color backgroundColor, DrawShape shape, Pen pen) {
        this.mainColor = mainColor;
        this.backgroundColor = backgroundColor;
        this.shape = shape;
        this.CurrentPen = pen;
    }
}
