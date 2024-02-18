package org.karn.supersmashmobs.effect.common;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import org.karn.supersmashmobs.effect.AbstractSSMEffect;
import org.karn.supersmashmobs.effect.SSMEffectType;

public class Slow extends AbstractSSMEffect {
    public static String name = "테스트 슬로우";
    public static String icon = "\u6000";
    public static SSMEffectType type = SSMEffectType.SLOW;
    public Slow() {
        super(StatusEffectCategory.NEUTRAL, name, icon, type);
        this.addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, "7107DE5E-7CE8-4030-940E-514C1F160890", -0.15D, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
    }

}
