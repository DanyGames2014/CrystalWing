package net.danygames2014.crystalwing.item;

import net.danygames2014.crystalwing.config.Config;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.SideUtil;

import java.util.Random;

public class BurnedWingItem extends TemplateItem {
    private final Random random = new Random();
    
    public BurnedWingItem(Identifier identifier) {
        super(identifier);
    }

    @Override
    public ItemStack use(ItemStack stack, World world, PlayerEntity player) {
        if (world.isRemote) {
            return super.use(stack, world, player);
        }

        world.playSound(player, "fire.ignite", 1.0F, 0.5F);
        int teleportDistance = Config.CRYSTAL_WING.burnedWingTeleportDistance;
        
        int teleportX = ((int) player.x) + (this.random.nextInt(teleportDistance * 2) - teleportDistance);
        int teleportZ = ((int) player.z) + (this.random.nextInt(teleportDistance * 2) - teleportDistance);
        world.getChunkSource().loadChunk(teleportX >> 4, teleportZ >> 4);
        int teleportY = world.getTopY(teleportX, teleportZ);
        
        if (teleportY <= 0) {
            return stack;
        }

        Vec3d teleportPos = Vec3d.create(teleportX + 0.5D, teleportY + player.standingEyeHeight, teleportZ + 0.5D);
        SideUtil.run(() -> {
            player.setPosition(teleportPos.x, teleportPos.y, teleportPos.z);
        }, () -> {
            if (player instanceof ServerPlayerEntity serverPlayer) {
                serverPlayer.networkHandler.teleport(teleportPos.x, teleportPos.y, teleportPos.z, player.yaw, player.pitch);
            }
        });
        
        stack.count--;
        return stack;
    }
}
