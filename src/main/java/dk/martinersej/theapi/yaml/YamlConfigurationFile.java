package dk.martinersej.theapi.yaml;

import dk.martinersej.theapi.TheAPI;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

import static org.bukkit.configuration.file.YamlConfiguration.loadConfiguration;

/**
 * A class to handle YAML configuration files
 */
public class YamlConfigurationFile {

    private String filePath;
    private FileConfiguration config;

    /**
     * Create a new configuration file
     *
     * @param instance the plugin instance
     * @param fileName the file name
     */
    public YamlConfigurationFile(JavaPlugin instance, String fileName) {
        setupConfig(instance, fileName);
    }

    /**
     * Save the configuration file
     */
    public void save() {
        try {
            getConfig().save(getFilePath());
        } catch (IOException e) {
            TheAPI.getPlugin().getLogger().severe("Failed to save " + getFilePath());
        }
    }

    /**
     * Load the configuration file
     */
    public void load() {
        File file = new File(this.getFilePath());
        if (!file.exists()) {
            TheAPI.getPlugin().saveResource(file.getName(), false);
        }
        setConfig(loadConfiguration(file));
    }

    /**
     * Reload the configuration file
     */
    public void reload() {
        save();
        load();
    }

    /**
     * Get the configuration
     *
     * @return the configuration
     */
    public FileConfiguration getConfig() {
        return config;
    }

    /**
     * Set the configuration
     *
     * @param fileConfiguration the configuration
     */
    public void setConfig(FileConfiguration fileConfiguration) {
        this.config = fileConfiguration;
    }

    /**
     * Get the file name without the extension
     *
     * @return the file name without the extension
     */
    public String getFileName() {
        String filePath = getFilePath();
        int dotIndex = filePath.lastIndexOf('.');

        return dotIndex > 0 ? filePath.substring(0, dotIndex) : filePath;
    }

    /**
     * Get the file path
     *
     * @return the file path
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Set the file path
     *
     * @param filePath the file path
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Setup the configuration file
     *
     * @param instance the plugin instance
     * @param fileName the file name
     */
    void setupConfig(JavaPlugin instance, String fileName) {
        File file = new File(instance.getDataFolder(), fileName);
        setFilePath(file.getAbsolutePath());
        if (!file.exists()) {
            instance.saveResource(fileName, false);
        }
        setConfig(loadConfiguration(file));
    }

    public void saveDefaultConfig() {
        if (getConfig().getKeys(true).isEmpty()) {
            getConfig().options().copyDefaults(true);
            save();
        }
    }
}
