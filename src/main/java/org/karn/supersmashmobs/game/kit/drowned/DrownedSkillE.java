package org.karn.supersmashmobs.game.kit.drowned;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.*;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.server.network.ServerPlayerEntity;
import org.joml.Vector3f;
import org.karn.supersmashmobs.api.HudApi;
import org.karn.supersmashmobs.effect.AbstractSSMEffect;
import org.karn.supersmashmobs.effect.SSMEffectType;
import org.karn.supersmashmobs.game.kit.AbstractSkill;
import org.karn.supersmashmobs.game.kit.SkillType;
import org.karn.supersmashmobs.registry.SSMEffects;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DrownedSkillE extends AbstractSkill {
    public static String id = "drowned_e";
    public static String name = "급류";
    public static String desc = "스킬을 사용할때마다 이동속도가 증가합니다.";
    public static SkillType type = SkillType.MOVEMENT;
    public static Integer cooldown = 100;
    public DrownedSkillE(){
        super(id, name, desc, cooldown, type);
    }
    public static void main(ServerPlayerEntity player){
        HudApi a = (HudApi) player;
        var stack = a.getTempData().get("drowned_riptide");
        if(stack == null || stack.equals(0)) {
            a.getTempData().put("drowned_riptide", 1);
            player.addStatusEffect(new StatusEffectInstance(SSMEffects.DROWNED_RIPTIDE, 100, 0));
        } else if (stack.equals(1)){
            a.getTempData().put("drowned_riptide", 2);
            player.addStatusEffect(new StatusEffectInstance(SSMEffects.DROWNED_RIPTIDE, 100, 1));
        } else if (stack.equals(2)){
            a.getTempData().put("drowned_riptide", 3);
            player.addStatusEffect(new StatusEffectInstance(SSMEffects.DROWNED_RIPTIDE, 100, 2));
        } else if (stack.equals(3)){
            a.getTempData().put("drowned_riptide", 4);
            player.addStatusEffect(new StatusEffectInstance(SSMEffects.DROWNED_RIPTIDE, 100, 3));
        } else {
            a.getTempData().put("drowned_riptide", 5);
            player.addStatusEffect(new StatusEffectInstance(SSMEffects.DROWNED_RIPTIDE, 100, 4));
        }
    }

    public static class RiptideEffect extends AbstractSSMEffect {
        public static String name = "drowned_riptide";
        public static String icon = "\u5002";
        public static List<SSMEffectType> type = List.of(SSMEffectType.SPEED);
        public RiptideEffect() {
            super(StatusEffectCategory.NEUTRAL, name, icon, type);
            this.addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, "77777777-0000-0001-0000-777777777777", 0.1D, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
        }
        public void applyUpdateEffect(LivingEntity entity, int amplifier) {
            super.applyUpdateEffect(entity, amplifier);
            entity.getServer().getOverworld().spawnParticles(DrownedKit.drownedDust, entity.getX(), entity.getY()+0.1, entity.getZ(), 10, 0.2D, 0.0D, 0.2D, 0.0D);
            var riptideEffect = entity.getStatusEffect(SSMEffects.DROWNED_RIPTIDE);
            if(riptideEffect != null && riptideEffect.getDuration() <= 1 && entity instanceof PlayerEntity){
                HudApi a = (HudApi) entity;
                a.getTempData().remove("drowned_riptide");
            }
        }
    }
}
