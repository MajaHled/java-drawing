package cz.cuni.mff.java.drawing;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ServiceLoader;

/**
 * A class responsible for loading and managing plugins for drawing pens.
 * <p>
 * The {@code PluginLoader} class dynamically loads plugins (represented by {@link Pen} instances) from a
 * specified directory and manages their integration with the drawing application. It maintains a list of
 * buttons that represent these plugins and provides functionality to refresh and update the list of
 * available plugins.
 * </p>
 * <p>
 * The {@code PluginLoader} may be used to load generic {@code Pen} plugins, but may also be used to load
 * plugins for a specific {@code Pen} subclass, for example {@link ShapePen}.
 * </p>
 * <p>
 * The plugins are loaded using a {@link ServiceLoader}. In order for a plugin to be loaded into the application,
 * a {@code jar} file with the implementation must be placed into the specified directory and be ready for loading
 * by a {@code ServiceLoader}. That is, it must have a {@code META-INF/services/cz.cuni.mff.java.drawing.<Service>}
 * entry listing the implementing class(es).
 * </p>
 *x
 * @see ServiceLoader
 * @see PenButton
 * @see Pen
 * @see ShapePen
 */
public class PluginLoader {
    private final List<PenButton> buttonList, permanentList;
    private final Class<? extends Pen> service;
    private final File pluginsDir;
    private final PenSettings penSettings;
    private final DrawPanelSettings panelSettings;

    /**
     * Constructs a {@code PluginLoader} with the specified parameters.
     *
     * @param service the {@link Class} object representing the {@link Pen} class of one of its subclasses,
     * which defines the service interface.
     * @param buttonList the list of {@link PenButton} objects to add the loaded plugins into
     * @param permanentList the list of {@link PenButton} objects that are always available
     * (i.e. permanent pens and not loaded plugins)
     * @param pluginsDir the directory from which to load plugin {@code jar} files
     * @param penSettings the {@link PenSettings} to apply to each loaded pen
     * @param panelSettings the {@link DrawPanelSettings} to associate the buttons with
     */
    public PluginLoader(Class<? extends Pen> service, ArrayList<PenButton> buttonList, ArrayList<PenButton> permanentList, File pluginsDir, PenSettings penSettings, DrawPanelSettings panelSettings) {
        this.buttonList = buttonList;
        this.permanentList = permanentList;
        this.service = service;
        this.pluginsDir = pluginsDir;
        this.penSettings = penSettings;
        this.panelSettings = panelSettings;
    }

    /**
     * Refreshes the list of plugins to the loader's specific service
     * by loading them from the specified plugins directory.
     * <p>
     * This method clears the current list of plugin buttons, adds all permanent buttons back to the list,
     * and then loads new plugins from {@code jar} files in the plugins directory. For each loaded plugin,
     * a corresponding {@link PenButton} is created and added to the list.
     * </p>
     *
     * @throws FileNotFoundException if the plugins directory is not found or is not a directory
     * @throws RuntimeException if there is an error creating the class loader for the plugins
     *
     * @see Pen
     * @see ShapePen
     * @see PenButton
     */
    public void refreshPlugins() throws FileNotFoundException {
        try {
            // Reset buttons to default state
            buttonList.clear();
            buttonList.addAll(permanentList);

            // Prepare class loader
            URLClassLoader loader = getUrlClassLoader();

            // Use the classloader in ServiceLoader to load all appropriate jars
            ServiceLoader<? extends Pen> sl = ServiceLoader.load(service, loader);

            // Initialize all plugin pens and add to list of buttons
            for (Pen plugin : sl) {
                plugin.setSettings(penSettings);
                PenButton b = new PenButton(plugin);

                // Setup button action
                b.addActionListener(_ -> {
                    panelSettings.currentPen.reset();
                    panelSettings.currentPen = b.pen;
                    b.pen.reset();
                });

                buttonList.add(b);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates a {@link URLClassLoader} for loading {@code jar} files from the plugins directory.
     * This classloader is to be used by a {@link ServiceLoader} to load the plugins.
     *
     * @return a {@code URLClassLoader} for the {@code jar} files in the plugins directory
     * @throws FileNotFoundException if the plugins directory does not exist or is not a directory
     * @throws MalformedURLException if a URL could not be created for a file in the directory
     */
    private URLClassLoader getUrlClassLoader() throws FileNotFoundException, MalformedURLException {
        if (pluginsDir.listFiles() == null)
            throw new FileNotFoundException(pluginsDir.getName() + " is not a directory.");

        // Get URLs of all files in pluginsDir
        ArrayList<URL> urls = new ArrayList<>();
        for (File file : Objects.requireNonNull(pluginsDir.listFiles())) {
            urls.add(file.toURI().toURL());
        }

        // Return classloader for all the loaded URLs
        URL[] array = new URL[urls.size()];
        urls.toArray(array);
        return new URLClassLoader(array);
    }
}
