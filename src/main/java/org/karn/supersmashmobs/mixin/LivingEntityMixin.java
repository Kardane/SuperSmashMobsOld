package org.karn.supersmashmobs.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import org.karn.supersmashmobs.api.LivingEntityApi;
import org.karn.supersmashmobs.registry.SSMAttributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin implements LivingEntityApi {
    @Inject(method = "createLivingAttributes", at = @At("RETURN"), require = 1, allow = 1)
    private static void esekai$registerCustomAttributes(CallbackInfoReturnable<DefaultAttributeContainer.Builder> cir) {
        SSMAttributes.ATTRIBUTES.forEach(attribute -> {
            cir.getReturnValue().add(attribute);
        });
    }

}
