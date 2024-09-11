package cz.cuni.mff.java.hw.drawing;

import javax.swing.*;
import java.awt.*;

public class PenButton extends JToggleButton {
    public final Pen pen;

    public PenButton(Pen pen) {
        super();
        this.pen = pen;

        Icon icon = pen.getPreferredIcon();
        if (icon != null) {
            this.setIcon(icon);
        } else {
            var img = PictureLoader.NewImage(20, 20, Color.WHITE);

            this.setIcon(new ImageIcon(img));
        }
    }
}
