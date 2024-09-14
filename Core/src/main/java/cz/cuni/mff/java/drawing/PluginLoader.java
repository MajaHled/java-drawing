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

public class PluginLoader {
    private final List<PenButton> buttonList, permanentList;
    private final Class<? extends Pen> service;
    private final File pluginsDir;
    private final PenSettings settings;

    public PluginLoader(Class<? extends Pen> service, ArrayList<PenButton> buttonList, ArrayList<PenButton> permanentList, File pluginsDir, PenSettings settings) {
        this.buttonList = buttonList;
        this.permanentList = permanentList;
        this.service = service;
        this.pluginsDir = pluginsDir;
        this.settings = settings;
    }

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
                plugin.setSettings(settings);
                buttonList.add(new PenButton(plugin));
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

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
