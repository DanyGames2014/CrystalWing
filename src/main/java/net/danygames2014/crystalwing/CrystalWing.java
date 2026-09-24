package net.danygames2014.crystalwing;

import net.danygames2014.crystalwing.item.BurnedWingItem;
import net.danygames2014.crystalwing.item.BurningWingItem;
import net.danygames2014.crystalwing.item.CrystalWingItem;
import net.danygames2014.crystalwing.network.CrystalWingTeleportedS2CPacket;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.event.network.packet.PacketRegisterEvent;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Namespace;
import org.apache.logging.log4j.Logger;

public class CrystalWing {
    @Entrypoint.Namespace
    public static Namespace NAMESPACE;

    @Entrypoint.Logger
    public static Logger LOGGER;
    
    public static CrystalWingItem crystalWing;
    public static Item burningWing;
    public static Item burnedWing;
    
    @EventListener
    public void registerItems(ItemRegistryEvent event) {
        crystalWing = new CrystalWingItem(NAMESPACE.id("crystal_wing"));
        burningWing = new BurningWingItem(NAMESPACE.id("burning_wing"));
        burnedWing = new BurnedWingItem(NAMESPACE.id("burned_wing"));
    }
    
    @EventListener
    public void registerPackets(PacketRegisterEvent event) {
        event.register(NAMESPACE.id("teleported"), CrystalWingTeleportedS2CPacket.TYPE);
    }
}
