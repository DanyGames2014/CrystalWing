package net.danygames2014.crystalwing.config;

import net.glasslauncher.mods.gcapi3.api.ConfigEntry;
import net.glasslauncher.mods.gcapi3.api.ConfigRoot;

public class Config {
    @ConfigRoot(value = "crystalwing", visibleName = "Crystal Wing")
    public static final CrystalWingConfig CRYSTAL_WING = new CrystalWingConfig();
    
    
    public static class CrystalWingConfig {
        @ConfigEntry(name = "Crystal Wing Durability", description = "Set to 0 for infinite durability")
        public Integer crystalWingDurability = 8;
        
        @ConfigEntry(name = "Burned Wing Teleportation Distance Limit")
        public Integer burnedWingTeleportDistance = 500;
    } 
}
