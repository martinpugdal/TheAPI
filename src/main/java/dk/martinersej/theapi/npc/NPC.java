package dk.martinersej.theapi.npc;

import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class NPC implements Listener {

    private net.citizensnpcs.api.npc.NPC npc;

    public NPC(net.citizensnpcs.api.npc.NPC npc) {
        this.npc = npc;
    }

    public NPC(NPCBuilder npcBuilder) {
        this.npc = npcBuilder.build();
    }

    abstract NPCBuilder getNpcBuilder();

    public void spawn() {
        if (npc == null || getNpcBuilder().getLocation() == null) return;
        npc = getNpcBuilder().build();
        npc.spawn(getNpcBuilder().getLocation());
        Bukkit.getServer().getPluginManager().registerEvents(this, JavaPlugin.getProvidingPlugin(getClass()));
    }

    public void despawn() {
        if (npc == null) return;
        npc.despawn();
        HandlerList.unregisterAll(this);
    }

    public abstract void onRightClick(NPCRightClickEvent event);

    public abstract void onLeftClick(NPCRightClickEvent event);

    @EventHandler
    public void onNpcRightClick(NPCRightClickEvent event) {
        if (!event.getNPC().equals(npc)) return;
        onRightClick(event);
    }

    @EventHandler
    public void onNpcLeftClick(NPCRightClickEvent event) {
        if (!event.getNPC().equals(npc)) return;
        onLeftClick(event);
    }
}
