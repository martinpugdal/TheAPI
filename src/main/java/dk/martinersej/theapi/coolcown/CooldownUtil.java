package dk.martinersej.theapi.coolcown;

import org.bukkit.command.Command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CooldownUtil {

    public static final CooldownUtil instance = new CooldownUtil();

    public static CooldownUtil getInstance() {
        return instance;
    }

    private final List<Command> handleCmds = new ArrayList<>();

    public void skipCommands(Command... cmds) {
        Collections.addAll(handleCmds, cmds);
    }

    public static boolean skipCooldownCheck(String cmdLabel) {
        return CooldownUtil.getInstance().handleCmds.stream().anyMatch(cmd -> {
            if (cmd.getName().equalsIgnoreCase(cmdLabel)) {
                return true;
            }

            for (String alias : cmd.getAliases()) {
                if (alias.equalsIgnoreCase(cmdLabel)) return true;
            }

            return false;
        });
    }
}
