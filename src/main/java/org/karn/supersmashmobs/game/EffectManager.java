package org.karn.supersmashmobs.game;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.network.ServerPlayerEntity;
import org.karn.supersmashmobs.effect.AbstractSSMEffect;
import org.karn.supersmashmobs.effect.SSMEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EffectManager {
    public static Map<AbstractSSMEffect, StatusEffectInstance> getSSMEffects(ServerPlayerEntity player){
        Map<StatusEffect, StatusEffectInstance> map = player.getActiveStatusEffects();
        if (map.isEmpty()) return null;

        var ssmMap = new HashMap<AbstractSSMEffect, StatusEffectInstance>();
        for (Map.Entry<StatusEffect, StatusEffectInstance> entry : map.entrySet()) {
            if (entry.getKey() instanceof AbstractSSMEffect) {
                ssmMap.put((AbstractSSMEffect) entry.getKey(), entry.getValue());
            }
        }
        return ssmMap;
    }

    public static boolean hasBind(ServerPlayerEntity player){
        var map = getSSMEffects(player);
        if(map == null) return false;

        boolean hasBind = false;
        for (AbstractSSMEffect entry : map.keySet()) {
            if (entry.type.contains(SSMEffectType.BIND)||
                    entry.type.contains(SSMEffectType.STUN) ||
                    entry.type.contains(SSMEffectType.STOP) ||
                    entry.type.contains(SSMEffectType.KNOCKBACK)) {
                return true;
            }
        }
        return hasBind;
    }

    public static boolean hasSilence(ServerPlayerEntity player){
        var map = getSSMEffects(player);
        if(map == null) return false;

        boolean hasSilence = false;
        for (AbstractSSMEffect entry : map.keySet()) {
            if (entry.type.contains(SSMEffectType.SILENCE)||
                    entry.type.contains(SSMEffectType.STUN) ||
                    entry.type.contains(SSMEffectType.STOP) ||
                    entry.type.contains(SSMEffectType.KNOCKBACK)) {
                return true;
            }
        }
        return hasSilence;
    }

    public static boolean hasKnockbackResistance(ServerPlayerEntity player){
        var map = getSSMEffects(player);
        if(map == null) return false;

        boolean hasKnockbackResist = false;
        for (AbstractSSMEffect entry : map.keySet()) {
            if (entry.type.contains(SSMEffectType.SUPERAMOR)||
                    entry.type.contains(SSMEffectType.INVINCIBLE)) {
                return true;
            }
        }
        return hasKnockbackResist;
    }
}
