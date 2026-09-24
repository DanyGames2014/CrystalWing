package net.danygames2014.crystalwing.network;

import net.danygames2014.crystalwing.CrystalWing;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.NetworkHandler;
import net.minecraft.network.packet.Packet;
import net.modificationstation.stationapi.api.entity.player.PlayerHelper;
import net.modificationstation.stationapi.api.network.packet.ManagedPacket;
import net.modificationstation.stationapi.api.network.packet.PacketType;
import net.modificationstation.stationapi.api.util.SideUtil;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class CrystalWingTeleportedS2CPacket extends Packet implements ManagedPacket<CrystalWingTeleportedS2CPacket> {
    public static final PacketType<CrystalWingTeleportedS2CPacket> TYPE = PacketType.builder(true, false, CrystalWingTeleportedS2CPacket::new).build();
    
    @Override
    public void read(DataInputStream stream) {
        
    }

    @Override
    public void write(DataOutputStream stream) {

    }

    @Override
    public void apply(NetworkHandler networkHandler) {
        SideUtil.runClient(() -> {handleClient(networkHandler);});
    }
    
    @Environment(EnvType.CLIENT)
    public void handleClient(NetworkHandler networkHandler) {
        PlayerEntity player = PlayerHelper.getPlayerFromPacketHandler(networkHandler);
        
        if (player == null) {
            return;
        }

        CrystalWing.crystalWing.playEffects(player.world, player);
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public @NotNull PacketType<CrystalWingTeleportedS2CPacket> getType() {
        return TYPE;
    }
}
