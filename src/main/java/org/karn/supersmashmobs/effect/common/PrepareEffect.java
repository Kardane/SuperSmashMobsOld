package org.karn.supersmashmobs.effect.common;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import org.karn.supersmashmobs.effect.AbstractSSMEffect;
import org.karn.supersmashmobs.effect.SSMEffectType;
import org.karn.supersmashmobs.registry.SSMAttributes;

import java.util.List;

public class PrepareEffect extends AbstractSSMEffect {
    public static String name = "게임 준비";
    public static String icon = "\u5001";
    public static List<SSMEffectType> type = List.of(SSMEffectType.SILENCE,SSMEffectType.BIND,SSMEffectType.INVINCIBLE,SSMEffectType.DISARM);
    public PrepareEffect() {
        super(StatusEffectCategory.NEUTRAL, name, icon, type);
        this.addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, "77777777-0000-0000-0000-777777777777", -100D, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(SSMAttributes.ATTACK_DMG, "77777777-0000-0000-0001-777777777777", -100D, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(SSMAttributes.PROTECTION, "77777777-0000-0000-0002-777777777777", 100D, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(SSMAttributes.KNOCKBACK_TAKEN, "77777777-0000-0000-0002-777777777777", 100D, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        super.applyUpdateEffect(entity, amplifier);
        if (!entity.getWorld().isClient) {
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 2,200,false,false));
        }
    }
}
