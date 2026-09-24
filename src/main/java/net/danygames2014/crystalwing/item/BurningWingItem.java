package net.danygames2014.crystalwing.item;

import net.danygames2014.crystalwing.CrystalWing;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class BurningWingItem extends TemplateItem {
    public BurningWingItem(Identifier identifier) {
        super(identifier);
        this.setMaxCount(1);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (world.isRemote) {
            return;
        }
        
        if (entity.isSubmergedInWater() && entity instanceof PlayerEntity player) {
            world.playSound(player, "random.fizz", 1.0f, 1.0f);
            extinguishWings(player);
            // TODO: Award achievement
            return;
        }
        
        if (entity.fireTicks <= 0) {
            entity.fireTicks = 20;
        }
    }
    
    private void extinguishWings(PlayerEntity player) {
        PlayerInventory inventory = player.inventory;

        ItemStack[] main = inventory.main;
        for (int i = 0, mainLength = main.length; i < mainLength; i++) {
            ItemStack stack = main[i];
            if (stack != null && stack.getItem() == CrystalWing.burningWing) {
                inventory.setStack(i, new ItemStack(CrystalWing.burnedWing, stack.count));
            }
        }
    }
}
