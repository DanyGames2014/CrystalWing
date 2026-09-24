package net.danygames2014.crystalwing.item;

import net.danygames2014.crystalwing.CrystalWing;
import net.danygames2014.crystalwing.config.Config;
import net.danygames2014.crystalwing.network.CrystalWingTeleportedS2CPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.network.packet.PacketHelper;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.SideUtil;

import java.util.Random;

public class CrystalWingItem extends TemplateItem {
    private final Random random = new Random();
    public boolean playNotes;
    private int start;

    public CrystalWingItem(Identifier identifier) {
        super(identifier);
        this.setMaxDamage(Config.CRYSTAL_WING.crystalWingDurability);
        this.setMaxCount(1);
    }

    @Override
    public ItemStack use(ItemStack stack, World world, PlayerEntity player) {
        if (world.isRemote) {
            return super.use(stack, world, player);
        }

        if (player.world.dimension.id != 0) {
            world.playSound(player, "fire.ignite", 1.0F, 1.0F);
            return new ItemStack(CrystalWing.burningWing, stack.count);
        }

        Vec3i spawnPos = player.getSpawnPos();

        // If the player has a spawn pos, find the respawn point
        if (spawnPos != null) {
            spawnPos = PlayerEntity.findRespawnPosition(world, spawnPos);
        }

        // If the player has no spawn pos or the respawn point is invalid, use world spawn pos
        if (spawnPos == null) {
            spawnPos = world.getSpawnPos();
            spawnPos.y = world.getTopY(spawnPos.x, spawnPos.z);
        }

        Vec3d teleportPos = Vec3d.create(spawnPos.x + 0.5D, spawnPos.y + player.standingEyeHeight, spawnPos.z + 0.5D);
        SideUtil.run(() -> {
            player.setPosition(teleportPos.x, teleportPos.y, teleportPos.z);
        }, () -> {
            if (player instanceof ServerPlayerEntity serverPlayer) {
                serverPlayer.networkHandler.teleport(teleportPos.x, teleportPos.y, teleportPos.z, player.yaw, player.pitch);
            }
        });

        player.sendMessage("Magical winds have brought you home");
        PacketHelper.sendTo(player, new CrystalWingTeleportedS2CPacket());

        if (Config.CRYSTAL_WING.crystalWingDurability > 0) {
            stack.damage(1, player);
        }

        return stack;
    }

    @Environment(EnvType.CLIENT)
    public void playEffects(World world, PlayerEntity player) {
        this.playNotes = true;
        this.start = player.age;

        for (int i = 0; i < 20; ++i) {
            double velocityX = this.random.nextGaussian() * 0.02D;
            double velocityY = this.random.nextGaussian() * 0.02D;
            double velocityZ = this.random.nextGaussian() * 0.02D;
            double spread = 10.0D;
            world.addParticle(
                    "explode",
                    player.x + (double) (random.nextFloat() * player.width * 2.0F) - (double) player.width - velocityX * spread,
                    player.y + (double) (this.random.nextFloat() - player.height) - velocityY * spread,
                    player.z + (double) (this.random.nextFloat() * player.width * 2.0F) - (double) player.width - velocityZ * spread,
                    velocityX,
                    velocityY,
                    velocityZ
            );
        }
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!(entity instanceof PlayerEntity player)) {
            return;
        }

        if (this.playNotes) {
            int offset = player.age - this.start;
            switch (offset) {
                case 1:
                    this.playAtPitch(8, world, player);
                    break;
                case 5:
                    this.playAtPitch(15, world, player);
                    break;
                case 7:
                    this.playAtPitch(19, world, player);
                    this.playNotes = false;
                    break;
            }
        }
    }

    @Environment(EnvType.CLIENT)
    public void playAtPitch(int i, World world, PlayerEntity player) {
        float pitch = (float) Math.pow(2.0, (i - 12) / 12.0);
        world.playSound(player, "note.pling", 0.5F, pitch);
    }
}
