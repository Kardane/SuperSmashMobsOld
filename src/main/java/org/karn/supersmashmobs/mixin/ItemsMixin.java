package org.karn.supersmashmobs.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.karn.supersmashmobs.game.SkillRouter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class ItemsMixin {
    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void SSM$dropItem(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir){
        SkillRouter.routeSkillA((ServerPlayerEntity) user);
        cir.setReturnValue(TypedActionResult.pass(user.getStackInHand(hand)));
    }

}
