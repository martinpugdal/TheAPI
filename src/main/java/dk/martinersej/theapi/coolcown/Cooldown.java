package dk.martinersej.theapi.coolcown;

import dk.martinersej.theapi.TheAPI;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class Cooldown {

    private final Map<OfflinePlayer, Long> cooldowns = new HashMap<>();
    private final long cooldownMs;
    private BukkitTask cleanupTask;

    public Cooldown(long ticks, Command... cmds) {
        this.cooldownMs = ticks * 50; // 1 tick = 50 ms
        CooldownUtil.getInstance().skipCommands(cmds);
    }

    public Cooldown(long ticks) {
        this.cooldownMs = ticks * 50; // 1 tick = 50 ms
    }

    public Cooldown(long ms, boolean inMs) {
        if (!inMs) this.cooldownMs = ms * 50; // 1 tick = 50 ms
        else this.cooldownMs = ms;
    }

    public Cooldown(long ms, boolean inMs, Command... cmds) {
        this(ms, inMs);
        CooldownUtil.getInstance().skipCommands(cmds);
    }

    public static long now() {
        return Instant.now().toEpochMilli();
    }

    public long getCooldownMs() {
        return cooldownMs;
    }

    public void addCooldown(OfflinePlayer player) {
        addCooldown(player, cooldownMs);
    }

    public void addCooldown(OfflinePlayer player, long ticks, boolean inTicks) {
        if (inTicks) addCooldown(player, ticks * 50);
        else addCooldown(player, ticks);
    }

    public void addCooldown(OfflinePlayer player, long ms) {
        long finishTime = now() + ms;

        Map.Entry<OfflinePlayer, Long> current = getCooldown(player);
        if (current != null) cooldowns.replace(player, finishTime);
        else cooldowns.put(player, finishTime);

        if (cleanupTask == null || cleanupTask.getTaskId() == -1) cleanupTask = new BukkitRunnable() {
            @Override
            public void run() {
                cleanExpiredCooldowns();
            }
        }.runTaskLater(TheAPI.getPlugin(), (long) (((double) ms / 50) * 1.5));
    }

    public void removeCooldown(OfflinePlayer player) {
        Map.Entry<OfflinePlayer, Long> cooldown = getCooldown(player);
        if (cooldown != null) cooldowns.remove(player);

        if (cooldowns.isEmpty() && cleanupTask != null) {
            cleanupTask.cancel();
            cleanupTask = null;
        }
    }

    public boolean hasCooldown(OfflinePlayer player) {
        Map.Entry<OfflinePlayer, Long> cooldown = getCooldown(player);
        if (cooldown == null) return false;
        return now() < cooldown.getValue();
    }

    public long getTimeLeftCooldown(OfflinePlayer player) {
        Map.Entry<OfflinePlayer, Long> cooldown = getCooldown(player);
        return cooldown == null ? 0 : cooldown.getValue() - now();
    }

    private Map.Entry<OfflinePlayer, Long> getCooldown(OfflinePlayer player) {
        return cooldowns.entrySet().stream().filter(entry -> entry.getKey().equals(player)).findFirst().orElse(null);
    }

    private long getCooldownTime(OfflinePlayer player) {
        Map.Entry<OfflinePlayer, Long> cooldown = getCooldown(player);
        return cooldown == null ? -1 : cooldown.getValue();
    }

    private void cleanExpiredCooldowns() {
        cooldowns.entrySet().removeIf(entry -> now() > entry.getValue());
    }
}
