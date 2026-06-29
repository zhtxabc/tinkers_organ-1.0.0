package com.zhtx.gtmce.event;

import net.minecraft.world.entity.EntityType;
import net.minecraftforge.eventbus.api.Event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChestCavityOrganEvent extends Event {

    private final Map<EntityType<?>, List<OrganSlot>> assignments = new HashMap<>();

    public ChestCavityOrganEvent() {}

    public void assign(String entityId, OrganSlot[] slots) {
        EntityType<?> type = EntityType.byString(entityId).orElse(null);
        if (type != null) {
            assignments.computeIfAbsent(type, k -> new ArrayList<>())
                    .addAll(List.of(slots));
        }
    }

    public void assignAll(String[] entityIds, OrganSlot[] slots) {
        for (String id : entityIds) {
            assign(id, slots);
        }
    }

    public Map<EntityType<?>, List<OrganSlot>> getAssignments() {
        return assignments;
    }

    public static class OrganSlot {
        public final String item;
        public final int position;

        public OrganSlot(String item, int position) {
            this.item = item;
            this.position = position;
        }
    }

    public static class OrganEntry {
        public String item;
        public int position;
    }
}