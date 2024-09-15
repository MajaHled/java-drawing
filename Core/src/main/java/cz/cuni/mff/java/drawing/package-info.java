/**
 * This package implements a simple GUI drawing tool.
 * <br><br>
 * The application is launched from the {@link cz.cuni.mff.java.drawing.Main} class.
 * <br><br>
 * The application window consists of a menu panel and {@link cz.cuni.mff.java.drawing.DrawPanel} with an image.
 * The top menu provides basic file operations on the image.
 * <br><br>
 * The drawing is handled by various {@link cz.cuni.mff.java.drawing.Pen} and {@link cz.cuni.mff.java.drawing.ShapePen}
 * implementations, which are parametrized by the settings controlled from the left menu panel.
 * There are some provided default pens, and the app is also extendable via plugins in the {@code /Plugins} directory.
 * <br><br>
 * For information about plugins, refer to the user docs, the {@link cz.cuni.mff.java.drawing.Pen} and
 * {@link cz.cuni.mff.java.drawing.ShapePen} classes as well as the {@link cz.cuni.mff.java.drawing.PluginLoader} class.
 *
 * @author Marie Hledíková
 * @version 1.0
 */

package cz.cuni.mff.java.drawing;