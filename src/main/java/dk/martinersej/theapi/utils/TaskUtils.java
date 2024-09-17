package dk.martinersej.theapi.utils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class TaskUtils {

    public static void runSync(JavaPlugin plugin, Runnable runnable) {
        Bukkit.getScheduler().runTask(plugin, runnable);
    }

    public static void runAsync(JavaPlugin plugin, Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, runnable);
    }

    public static void runSyncLater(JavaPlugin plugin, Runnable runnable, long delay) {
        Bukkit.getScheduler().runTaskLater(plugin, runnable, delay);
    }

    public static void runAsyncLater(JavaPlugin plugin, Runnable runnable, long delay) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, runnable, delay);
    }

    public static void runSyncTimer(JavaPlugin plugin, BukkitRunnable runnable, long delay, long period) {
        Bukkit.getScheduler().runTaskTimer(plugin, runnable, delay, period);
    }

    public static void runAsyncTimer(JavaPlugin plugin, Runnable runnable, long delay, long period) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, runnable, delay, period);
    }
}