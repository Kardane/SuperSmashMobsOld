package org.karn.supersmashmobs.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import org.karn.supersmashmobs.api.HudApi;
import org.karn.supersmashmobs.api.LivingEntityApi;
import org.karn.supersmashmobs.registry.SSMAttributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin implements LivingEntityApi {
    private final LivingEntity entity = (LivingEntity) (Object) this;
    @Inject(method = "createLivingAttributes", at = @At("RETURN"), require = 1, allow = 1)
    private static void SSM$registerCustomAttributes(CallbackInfoReturnable<DefaultAttributeContainer.Builder> cir) {
        SSMAttributes.ATTRIBUTES.forEach(attribute -> {
            cir.getReturnValue().add(attribute);
        });
    }
}
