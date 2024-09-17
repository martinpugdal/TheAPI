package dk.martinersej.theapi.utils;

import org.bukkit.ChatColor;

public class MessageUtils {

    static String colorize(String message) {
        if (StringUtils.isBlank(message)) {
            return "";
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String[] colorize(String... messages) {
        for (int i = 0; i < messages.length; i++) {
            messages[i] = colorize(messages[i]);
        }
        return messages;
    }

    public static String uncolorize(String message) {
        return ChatColor.stripColor(message);
    }
}
