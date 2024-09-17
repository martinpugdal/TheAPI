package dk.martinersej.theapi.utils;

import dk.martinersej.theapi.TheAPI ;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class PacketUtils {

    public static void sendTitle(Player player, String title, String subTitle, int fadeInTicks, int durationTicks, int fadeOutTicks) {
        if (!player.isOnline()) return;

        IChatBaseComponent titleComponent = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + title + "\"}");
        IChatBaseComponent subtitleComponent = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + subTitle + "\"}");

        PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, titleComponent, fadeInTicks, durationTicks, fadeOutTicks);
        PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, subtitleComponent, fadeInTicks, durationTicks, fadeOutTicks);

        PlayerConnection playerConnection = ((CraftPlayer) player).getHandle().playerConnection;
        playerConnection.sendPacket(subtitlePacket);
        playerConnection.sendPacket(titlePacket);

        PacketPlayOutTitle length = new PacketPlayOutTitle(fadeInTicks, durationTicks, fadeOutTicks);
        playerConnection.sendPacket(length);
    }

    public static void sendTitle(Player player, String title, String subTitle) {
        sendTitle(player, title, subTitle, 10, 70, 20);
    }

    public static void sendActionBar(Player player, String message) {
        if (!player.isOnline()) return;

        IChatBaseComponent chatComponent = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + message + "\"}");
        PacketPlayOutChat packetPlayOutChat = new PacketPlayOutChat(chatComponent, (byte) 2);

        EntityPlayer nmsPlayer = ((CraftPlayer) player).getHandle();
        nmsPlayer.playerConnection.sendPacket(packetPlayOutChat);
    }

    public static void sendActionBar(final Player player, final String message, int duration) {
        sendActionBar(player, message);

        if (duration >= 0) {
            // Sends empty message at the end of the duration. Allow messages shorter than 3 seconds, ensures precision.
            TaskUtils.runSyncLater(
                TheAPI.getPlugin(),
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        sendActionBar(player, "");
                    }
                },
                duration + 1
            );
        }

        // Re-sends the messages every 3 seconds, so it doesn't go away from the player's screen.
        while (duration > 40) {
            duration -= 40;
            TaskUtils.runSyncLater(
                TheAPI.getPlugin(),
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        sendActionBar(player, message);
                    }
                },
                duration
            );
            new BukkitRunnable() {
                @Override
                public void run() {
                    sendActionBar(player, message);
                }
            }.runTaskLater(JavaPlugin.getProvidingPlugin(PacketUtils.class), duration);
        }
    }

    public static void sendActionBarToAllPlayers(String message) {
        sendActionBarToAllPlayers(message, -1);
    }

    public static void sendActionBarToAllPlayers(String message, int duration) {
        for (Player p : TheAPI.getPlugin().getServer().getOnlinePlayers()) {
            sendActionBar(p, message, duration);
        }
    }

    public static void sendRedstoneParticle(Player player, Location location, Color color) {
        PacketPlayOutWorldParticles particle = new PacketPlayOutWorldParticles(EnumParticle.REDSTONE, true, (float) location.getX(), (float) location.getY(), (float) location.getZ(), (float) color.getRed() / 255, (float) color.getGreen() / 255, (float) color.getBlue() / 255, (float) 1, 0, 1);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(particle);
    }
}