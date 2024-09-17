package dk.martinersej.theapi;

import dk.martinersej.theapi.command.CommandInjector;
import org.bukkit.plugin.java.JavaPlugin;

public final class TheAPI {

    private static JavaPlugin plugin;
    private static CommandInjector commandInjector;

    public static void initializeAPI(JavaPlugin javaPlugin) {
        if (isInitialized()) {
            javaPlugin.getLogger().warning("TheAPI has already been initialized by " + plugin.getName());
            return;
        }

        plugin = javaPlugin;
        plugin.getLogger().info("TheAPI has been initialized!");

        commandInjector = new CommandInjector();
    }

    public static boolean isInitialized() {
        return plugin != null && plugin.isEnabled();
    }

    public static JavaPlugin getPlugin() {
        if (isInitialized()) {
            return plugin;
        }
        return JavaPlugin.getProvidingPlugin(TheAPI.class);
    }

    public static CommandInjector getCommandInjector() {
        if (commandInjector == null) {
            throw new IllegalStateException("TheAPI has not been initialized!");
        }
        return commandInjector;
    }
}
