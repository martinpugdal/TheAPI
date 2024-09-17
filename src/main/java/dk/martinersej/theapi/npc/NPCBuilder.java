package dk.martinersej.theapi.npc;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.MemoryNPCDataStore;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.trait.LookClose;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

public class NPCBuilder {

    private static final NPCRegistry npcRegistry;

    static {
        if (CitizensAPI.hasImplementation()) {
            npcRegistry = CitizensAPI.createNamedNPCRegistry(JavaPlugin.getProvidingPlugin(NPCBuilder.class) + "-NPC_Registry", new MemoryNPCDataStore());
        } else {
            npcRegistry = null;
        }
    }

    private EntityType entityType = EntityType.PLAYER;
    private Location location = null;
    private String name;
    private String skinTexture = null;
    private String skinSignature = null;
    private boolean lookClose = false;
    private boolean nameVisible = false;

    public NPCBuilder(String name) {
        this.name = name;
    }

    public static NPCRegistry getNpcRegistry() {
        return npcRegistry;
    }

    public Location getLocation() {
        return location;
    }


    public NPCBuilder withLocation(Location location) {
        this.location = location;
        return this;
    }

    public NPCBuilder withEntityType(EntityType entityType) {
        this.entityType = entityType;
        return this;
    }

    public NPCBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public NPCBuilder withSkinTexture(String skinTexture) {
        this.skinTexture = skinTexture;
        return this;
    }

    public NPCBuilder withSkinSignature(String skinSignature) {
        this.skinSignature = skinSignature;
        return this;
    }

    public NPCBuilder withLookClose(boolean lookClose) {
        this.lookClose = lookClose;
        return this;
    }

    public NPCBuilder withNameVisible(boolean nameVisible) {
        this.nameVisible = nameVisible;
        return this;
    }

    public NPC build() {
        NPC npc = npcRegistry.createNPC(entityType, name);
        if (lookClose) npc.getOrAddTrait(LookClose.class).lookClose(lookClose);
        if (nameVisible) npc.data().set(NPC.Metadata.NAMEPLATE_VISIBLE, nameVisible);
        if (skinTexture != null && skinSignature != null && entityType == EntityType.PLAYER) {
            npc.data().set("npcTexture", skinSignature);
            npc.data().set("npcSignature", skinTexture);
        }
        return npc;
    }
}
