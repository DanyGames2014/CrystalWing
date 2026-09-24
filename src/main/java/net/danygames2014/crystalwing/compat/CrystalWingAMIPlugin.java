package net.danygames2014.crystalwing.compat;

import net.danygames2014.crystalwing.CrystalWing;
import net.glasslauncher.mods.alwaysmoreitems.api.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.modificationstation.stationapi.api.util.Identifier;

public class CrystalWingAMIPlugin implements ModPluginProvider {
    @Override
    public String getName() {
        return "Crystal Wing";
    }

    @Override
    public Identifier getId() {
        return CrystalWing.NAMESPACE.id("ami_plugin");
    }

    @Override
    public void onAMIHelpersAvailable(AMIHelpers amiHelpers) {

    }

    @Override
    public void onItemRegistryAvailable(ItemRegistry itemRegistry) {

    }

    @Override
    public void register(ModRegistry registry) {
        registry.addDescription(new ItemStack(CrystalWing.crystalWing), "description.crystalwing.crystal_wing");
        registry.addDescription(new ItemStack(CrystalWing.burningWing), "description.crystalwing.burning_wing");
        registry.addDescription(new ItemStack(CrystalWing.burnedWing), "description.crystalwing.burned_wing");
    }

    @Override
    public void onRecipeRegistryAvailable(RecipeRegistry recipeRegistry) {

    }

    @Override
    public SyncableRecipe deserializeRecipe(NbtCompound recipe) {
        return null;
    }

    @Override
    public void updateBlacklist(AMIHelpers amiHelpers) {

    }
}
