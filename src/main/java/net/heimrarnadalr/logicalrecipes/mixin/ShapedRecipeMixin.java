package net.heimrarnadalr.logicalrecipes.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.ShapedRecipe;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShapedRecipe.class)
public class ShapedRecipeMixin {
	@Shadow
	private ItemStack output;
	
	@Inject(at = @At("HEAD"), method = "getOutput", cancellable = true)
	private void init(CallbackInfoReturnable<ItemStack> info) {
		Item item = output.getItem();
		if (item instanceof BlockItem) {
			Block block = ((BlockItem)item).getBlock();
			if (block instanceof StairsBlock && output.getCount() == 4) {
				output.setCount(8);			
			} else if (block instanceof TrapdoorBlock) {
				if (block.getMaterial(block.getDefaultState()) == Material.METAL && output.getCount() == 1) {
					output.setCount(4);
				} else if (block.getMaterial(block.getDefaultState()) == Material.WOOD && output.getCount() == 2) {
					output.setCount(6);
				}
			}
		}
	}
}
